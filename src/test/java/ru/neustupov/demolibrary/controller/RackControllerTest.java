package ru.neustupov.demolibrary.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
class RackControllerTest {

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
  void getBookByRackId() throws Exception {

    Rack rack1 = rackService.save(new Rack());
    bookService.save(Book.builder().rack(rack1).level(Level.ONE1).name("AAA")
        .build());

    mvc.perform(get("/api/v1/racks/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is("AAA")));
  }

  @Test
  void createRack() throws Exception{

    Rack rack1 = rackService.save(new Rack());

    mvc.perform(post("/api/v1/racks/")
        .content(om.writeValueAsString(rack1))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)));
  }
}