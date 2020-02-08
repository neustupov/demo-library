package ru.neustupov.demolibrary.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  Logger logger = LoggerFactory.getLogger(BookController.class);

  @Autowired
  private BookService bookService;

  @Autowired
  private RackService rackService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public Book getBookById(@PathVariable("id") Long id){
    return bookService.getBookById(id);
  }

  @GetMapping(value = "/level/{level}", produces = "application/json")
  public List<Book> getBookByLevel(@PathVariable("level") String level){
    Enum levelEnum = null;
    try{
      levelEnum = Level.valueOf(level);
    } catch (Exception e){
      logger.error("Not found level: " + level);
    }
    return bookService.getAllByLevel(levelEnum);
  }

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    Book newBook = new Book();
    newBook.setName(book.getName());
    newBook.setLevel(book.getLevel());
    Rack bookRack = book.getRack();
    if (bookRack != null) {
      Rack rack = null;
      Long rackId = null;
      try {
        rackId = book.getRack().getId();
        rack = rackService.getRackById(rackId);
      } catch (NullPointerException e){
        logger.error("Not found Rack with id: " + rackId);
      }
      newBook.setRack(rack);
    }
    return ResponseEntity.ok(bookService.save(newBook));
  }
}
