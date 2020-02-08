package ru.neustupov.demolibrary.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.neustupov.demolibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Book getBookById(Long id);

  List<Book> getAllByLevel(Enum level);
}
