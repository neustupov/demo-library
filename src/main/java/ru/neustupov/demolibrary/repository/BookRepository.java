package ru.neustupov.demolibrary.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.neustupov.demolibrary.model.Book;
import ru.neustupov.demolibrary.model.Rack;

public interface BookRepository extends JpaRepository<Book, Long> {

  Book getBookByName(String name);

  List<Book> getAllByLevel(Enum level);

  List<Book> getAllByRack(Rack rack);

  List<Book> getAllByRackAndLevel(Rack rack, Enum level);
}
