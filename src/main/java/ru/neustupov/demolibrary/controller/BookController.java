package ru.neustupov.demolibrary.controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
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
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.service.BookService;
import ru.neustupov.demolibrary.service.RackService;

@RestController
@RequestMapping("books")
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
  public Book getBookById(@PathVariable("id") Long id) {
    return bookService.getBookById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
  }

  @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Book getBookByName(@PathVariable("name") String name) {
    return bookService.getBookByName(name);
  }

  @GetMapping(value = "/level/{level}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Book> getBookByLevel(@PathVariable("level") String level) {
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
      @PathVariable("level") String level) {
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
                                                       @PathVariable("level") String level,
                                                       @RequestBody Book book) {
    Rack rc = getRackById(Long.parseLong(rack));
    Level lv = getLevelByCode(level);

    ResponseEntity<Book> responseEntity;
    if (rc != null && lv != null){
      Book newBook = createBook(rc, lv, book);
      responseEntity = ResponseEntity.ok(bookService.save(newBook));
    } else {
      responseEntity = new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }

    return responseEntity;
  }

  @PutMapping("/{id}")
  public Book updateBook(@RequestBody Book book, @PathVariable Long id) {

    return bookService.getBookById(id)
        .map(bc -> {
          bc.setName(book.getName());
          bc.setRack(book.getRack());
          bc.setLevel(book.getLevel());
          return bookService.save(bc);
        })
        .orElseGet(() -> {
          book.setId(id);
          return bookService.save(book);
        });
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Long> deleteBook(@PathVariable Long id) {

    ResponseEntity responseEntity;
    Book book = bookService.getBookById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

    if (book != null) {
      bookService.deleteById(book);
      responseEntity = new ResponseEntity<>(id, HttpStatus.OK);
    }else {
      responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return responseEntity;
  }

  private Book createBook(Rack rack, Level level, Book book) {

    Book newBook = new Book();
    newBook.setName(book.getName());
    newBook.setLevel(level);
    newBook.setRack(rack);

    return newBook;
  }

  private Rack getRackById(Long id) {
    return rackService.getRackById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
  }

  private Level getLevelByCode(String level){
    Level levelEnum = null;
    try {
      levelEnum = Level.valueOf(level);
    } catch (Exception e) {
      logger.error("Not found level: " + level);
    }
    return levelEnum;
  }
}
