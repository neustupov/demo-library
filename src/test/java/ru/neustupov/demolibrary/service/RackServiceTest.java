package ru.neustupov.demolibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neustupov.demolibrary.model.Rack;

@SpringBootTest
class RackServiceTest {

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
  void getRackById() {
    Rack rack = rackService.save(new Rack());
    assertEquals(rackService.getRackById(rack.getId()).get().getId(), rack.getId());
  }

  @Test
  void save() {
    Rack rack = rackService.save(new Rack());
    assertEquals(rackService.getRackById(rack.getId()).get().getId(), rack.getId());
  }

  @Test
  void deleteAll() {
    Rack rack = rackService.save(new Rack());
    rackService.deleteAll();
    assertThrows(NoSuchElementException.class, () -> rackService.getRackById(rack.getId()).get());
  }
}