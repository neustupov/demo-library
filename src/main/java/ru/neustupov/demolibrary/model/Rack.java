package ru.neustupov.demolibrary.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Rack extends AbstractEntity {

  @OneToMany
  private Set<Book> books;
}
