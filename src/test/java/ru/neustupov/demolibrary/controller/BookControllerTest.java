package ru.neustupov.demolibrary.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Level;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.service.BookService;
import ru.neustupov.demolibrary.service.RackService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private RackService rackService;

  @Autowired
  private BookService bookService;

  private static final ObjectMapper om = new ObjectMapper();

  @BeforeEach
  void setUp() {
    bookService.deleteAll();
    rackService.deleteAll();
  }

  @Test
  void getBookById() throws Exception{

    Rack rack1 = rackService.save(new Rack());
    Book book = bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    String bookId = book.getId().toString();
    mvc.perform(get("/api/v1/books/" + bookId))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("AAA")));
  }

  @Test
  void getBookByName() throws Exception{

    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());

    mvc.perform(get("/api/v1/books/name/AAA"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("AAA")));
  }

  @Test
  void getBookByLevel() throws Exception{

    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());

    mvc.perform(get("/api/v1/books/level/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("AAA")));
  }

  @Test
  void getBookByRackAndLevel() throws Exception{

    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    String rackId = rack1.getId().toString();
    mvc.perform(get("/api/v1/books/rack/" + rackId + "/level/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("AAA")));
  }

  @Test
  void createBookByRackAndLevel() throws Exception{

    rackService.save(new Rack());
    Book book = Book.builder().name("AAA").build();

    mvc.perform(post("/api/v1/books/rack/1/level/1")
        .content(om.writeValueAsString(book))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("AAA")));
  }

  @Test
  void updateBook() throws Exception{
    Rack rack1 = rackService.save(new Rack());
    Book book = bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());
    book.setName("BBB");
    mvc.perform(put("/api/v1/books/1")
        .content(om.writeValueAsString(book))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("BBB")));
  }

  @Test
  void deleteBook() {
  }
}