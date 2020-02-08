package ru.neustupov.demolibrary.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neustupov.demolibrary.exception.ResourceNotFoundException;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.service.BookService;
import ru.neustupov.demolibrary.service.RackService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

  private Logger logger = LoggerFactory.getLogger(BookController.class);

  private BookService bookService;

  private RackService rackService;

  @Autowired
  public BookController(BookService bookService, RackService rackService) {
    this.bookService = bookService;
    this.rackService = rackService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> getBookById(@PathVariable("id") Long id)
      throws ResourceNotFoundException {
    Book book = bookService.getBookById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id : " + id));
    return ResponseEntity.ok().body(book);
  }

  @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> getBookByName(@PathVariable("name") String name)
      throws ResourceNotFoundException {
    Book book = bookService.getBookByName(name);
    if (book == null) {
      throw new ResourceNotFoundException("Book not found for this name : " + name);
    }
    return ResponseEntity.ok().body(book);
  }

  @GetMapping(value = "/level/{level}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Book> getBookByLevel(@PathVariable("level") Integer level) {
    Enum levelEnum = null;
    try {
      levelEnum = Level.valueOf(level);
    } catch (Exception e) {
      logger.error("Not found level: " + level);
    }
    return bookService.getAllByLevel(levelEnum);
  }

  @GetMapping(value = "/rack/{rack}/level/{level}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Book> getBookByRackAndLevel(@PathVariable("rack") String rack,
      @PathVariable("level") Integer level) throws ResourceNotFoundException{
    Enum levelEnum = getLevelByCode(level);
    Rack rc = getRackById(Long.parseLong(rack));
    List<Book> books = new ArrayList<>();
    if (levelEnum != null && rc != null) {
      books = bookService.getAllByRackAndLevel(rc, levelEnum);
    }
    return books;
  }

  @PostMapping(value = "/rack/{rack}/level/{level}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> createBookByRackAndLevel(@PathVariable("rack") String rack,
      @PathVariable("level") Integer level,
      @RequestBody Book book) throws ResourceNotFoundException {
    Rack rc = getRackById(Long.parseLong(rack));
    Level lv = getLevelByCode(level);

    if (rc == null || lv == null) {
      throw new ResourceNotFoundException(
          "Rack or Level not found for : " + rack + " and " + level);
    }
    Book newBook = createBook(rc, lv, book);

    return ResponseEntity.ok(bookService.save(newBook));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable Long id) {

    return bookService.getBookById(id)
        .map(bc -> {
          bc.setName(book.getName());
          bc.setRack(book.getRack());
          bc.setLevel(book.getLevel());
          return ResponseEntity.ok(bookService.save(bc));
        })
        .orElseGet(() -> {
          book.setId(id);
          return ResponseEntity.ok(bookService.save(book));
        });
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Long> deleteBook(@PathVariable Long id) throws ResourceNotFoundException {

    Book book = bookService.getBookById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + id));

    bookService.deleteById(book);

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  private Book createBook(Rack rack, Level level, Book book) {

    Book newBook = new Book();
    newBook.setName(book.getName());
    newBook.setLevel(level);
    newBook.setRack(rack);

    return newBook;
  }

  private Rack getRackById(Long id) throws ResourceNotFoundException {
    return rackService.getRackById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Rack not found for this id :: " + id));
  }

  private Level getLevelByCode(Integer level) throws ResourceNotFoundException{
    Level levelEnum = Level.valueOf(level);
    if (levelEnum == null){
      throw new ResourceNotFoundException("Rack not found for this id :: " + level);
    }
    return levelEnum;
  }
}
