package ru.neustupov.demolibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;

@SpringBootTest
class BookServiceTest {

  @Autowired
  private RackService rackService;

  @Autowired
  private BookService bookService;

  @BeforeEach
  void setUp() {
    Rack rack1 = rackService.save(new Rack());
    Rack rack2 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("BBB")
        .build());
    bookService.save(Book.builder().rack(rack2).level(Level.TWO2).name("CCC")
        .build());
  }

  @AfterEach
  void tearDown() {
    bookService.deleteAll();
    rackService.deleteAll();
  }

  @Test
  void getBookById() {
    assertEquals(bookService.getBookById(1L).get().getId(), 1L);
  }

  @Test
  void getBookByName() {
    assertEquals(bookService.getBookByName("AAA").getName(), "AAA");
  }

  @Test
  void getAllByLevel() {
    List bookList = bookService.getAllByLevel(Level.ONE1);
    assertEquals(bookList.size(), 2);
  }

  @Test
  void getAllByRack() {
    Rack rack = rackService.getRackById(1L).get();
    assertEquals(bookService.getAllByRack(rack).size(), 2);
  }

  @Test
  void getAllByRackAndLevel() {
    Rack rack = rackService.getRackById(1L).get();
    assertEquals(bookService.getAllByRackAndLevel(rack, Level.ONE1).size(), 2L);
  }

  @Test
  void save() {
    Rack rack = rackService.getRackById(1L).get();
    Book book = Book.builder().name("ZZZ").rack(rack).level(Level.THREE3).build();
    bookService.save(book);
    assertEquals(bookService.getBookByName("ZZZ").getName(), "ZZZ");
  }

  @Test
  void deleteById() {
    Book book = bookService.getBookById(1L).get();
    bookService.deleteById(book);
    assertEquals(bookService.getAllByLevel(Level.ONE1).size(), 1);
  }
}