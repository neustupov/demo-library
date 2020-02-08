package ru.neustupov.demolibrary.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    return bookService.getBookById(id);
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
  public List<Book> getBookByLevel(@PathVariable("rack") String rack,
      @PathVariable("level") String level) {
    Enum levelEnum = null;
    try {
      levelEnum = Level.valueOf(level);
    } catch (Exception e) {
      logger.error("Not found level: " + level);
    }
    Rack rc = getRackById(Long.parseLong(rack));
    List<Book> books = new ArrayList<>();
    if (levelEnum != null && rc != null) {
      books = bookService.getAllByRackAndLevel(rc, levelEnum);
    }
    return books;
  }

  /*@PostMapping(value = "/rack/{rack}/level/{level}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> createBookByRackAndLevel(@RequestBody Book book) {

  }*/

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> createBook(@RequestBody Book book) {

    ResponseEntity<Book> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    Book newBook = new Book();
    newBook.setName(book.getName());
    newBook.setLevel(book.getLevel());

    Rack bookRack = book.getRack();

    if (bookRack != null) {
      Long rackId = bookRack.getId();
      Rack rack;
      if (rackId != null) {
        rack = getRackById(rackId);
        newBook.setRack(rack);
        responseEntity = ResponseEntity.ok(bookService.save(newBook));
      }
    } else {
      responseEntity = new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }

    return responseEntity;
  }

  private Rack getRackById(Long id) {
    Rack rack = null;
    try {
      rack = rackService.getRackById(id);
    } catch (Exception e) {
      logger.error("Not found Rack with id: " + id);
    }
    return rack;
  }
}
