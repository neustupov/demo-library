package ru.neustupov.demolibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;

@SpringBootTest
@Transactional
class BookServiceTest {

  @Autowired
  private RackService rackService;

  @Autowired
  private BookService bookService;

  @BeforeEach
  void setUp() {
    bookService.deleteAll();
    rackService.deleteAll();
  }

  @Test
  void getBookById() {
    Rack rack1 = rackService.save(new Rack());
    Book book = bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    assertEquals(bookService.getBookById(book.getId()).get().getId(), book.getId());
  }

  @Test
  void getBookByName() {
    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    assertEquals(bookService.getBookByName("AAA").getName(), "AAA");
  }

  @Test
  void getAllByLevel() {
    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    List bookList = bookService.getAllByLevel(Level.ONE1);
    assertEquals(bookList.size(), 1);
  }

  @Test
  void getAllByRack() {
    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    Rack rack = rackService.getRackById(1L).get();
    assertEquals(bookService.getAllByRack(rack).size(), 1);
  }

  @Test
  void getAllByRackAndLevel() {
    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    assertEquals(bookService.getAllByRackAndLevel(rack1, Level.ONE1).size(), 1);
  }

  @Test
  void save() {
    Rack rack = rackService.save(new Rack());
    Book book = Book.builder().name("ZZZ").rack(rack).level(Level.THREE3).build();
    bookService.save(book);
    assertEquals(bookService.getBookByName("ZZZ").getName(), "ZZZ");
  }

  @Test
  void deleteById() {
    Rack rack1 = rackService.save(new Rack());
    Book book = bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("BBB")
        .build());
    bookService.deleteById(book);
    assertEquals(bookService.getAllByLevel(Level.ONE1).size(), 1);
  }

  @Test
  void deleteAll() {
    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    bookService.deleteAll();
    assertEquals(bookService.getAllByLevel(Level.ONE1).size(), 0);
  }
}