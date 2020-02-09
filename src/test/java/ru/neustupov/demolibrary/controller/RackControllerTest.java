package ru.neustupov.demolibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.service.BookService;
import ru.neustupov.demolibrary.service.RackService;

@AutoConfigureMockMvc
@WebMvcTest

class RackControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private RackService rackService;

  @Autowired
  private BookService bookService;

  @BeforeEach
  void setUp() {
    bookService.save(Book.builder().rack(rackService.save(new Rack())).level(Level.ONE1).name("AAA")
        .build());
    bookService.save(Book.builder().rack(rackService.save(new Rack())).level(Level.TWO2).name("BBB")
        .build());
  }

  @Test
  void getBookByRackId() throws Exception {

    Rack rack1 = rackService.getRackById(1L).orElse(null);
    Rack rack2 = rackService.getRackById(2L).orElse(null);
    Book book1 = Book.builder().rack(rack1).level(Level.ONE1).name("AAA").build();
    Book book2 = Book.builder().rack(rack2).level(Level.TWO2).name("BBB").build();
    List<Book> books = Arrays.asList(book1, book2);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("api/v1/racks/1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    String resultString = result.getResponse().getContentAsString();
    assertNotNull(resultString);
  }

  @Test
  void createRack() throws Exception{
  }
}