package ru.neustupov.demolibrary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.service.BookService;
import ru.neustupov.demolibrary.service.RackService;

@RestController
@RequestMapping("racks")
public class RackController {

  private Logger logger = LoggerFactory.getLogger(RackController.class);

  private BookService bookService;

  private RackService rackService;

  @Autowired
  public RackController(BookService bookService, RackService rackService) {
    this.bookService = bookService;
    this.rackService = rackService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Book> getBookByRackId(@PathVariable("id") Long id) {
    //Long rackId = Long.parseLong(id);
    Rack rack = getRackById(id);
    List<Book> rackBooks = new ArrayList<>();
    if (rack != null) {
      rackBooks = bookService.getAllByRack(rack);
    }
    return rackBooks != null ? rackBooks : Collections.emptyList();
  }

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rack> createRack(@RequestBody Rack rack) {
    Rack newRack = new Rack();
    Set<Book> books = rack.getBooks();
    if (books != null) {
      newRack.setBooks(books);
    } else {
      newRack.setBooks(new HashSet<>());
    }
    return ResponseEntity.ok(rackService.save(rack));
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
