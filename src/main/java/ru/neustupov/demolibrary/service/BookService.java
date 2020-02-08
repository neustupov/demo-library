package ru.neustupov.demolibrary.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.repository.BookRepository;

@Service
public class BookService {

  private BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book getBookById(Long id) {
    return bookRepository.getBookById(id);
  }

  public List<Book> getAllByLevel(Enum level) {
    return bookRepository.getAllByLevel(level);
  }

  public List<Book> getAllByRack(Rack rack) {
    return bookRepository.getAllByRack(rack);
  }

  public List<Book> getAllByRackAndLevel(Rack rack, Enum level) {
    return bookRepository.getAllByRackAndLevel(rack, level);
  }

  public Book save(Book book) {
    return bookRepository.save(book);
  }
}
