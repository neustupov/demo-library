package ru.neustupov.demolibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neustupov.demolibrary.model.Rack;

public interface RackRepository extends JpaRepository<Rack, Long> {
}
