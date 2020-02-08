package ru.neustupov.demolibrary.service;

import java.util.List;
import java.util.Optional;
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

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  public Book getBookByName(String name){
    return bookRepository.getBookByName(name);
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

  public void deleteById(Book book){
    bookRepository.delete(book);
  }
}
