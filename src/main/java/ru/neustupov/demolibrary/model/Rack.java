package ru.neustupov.demolibrary.model;

import java.util.Set;
import javax.persistence.CascadeType;
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

  @OneToMany(cascade = CascadeType.ALL)
  private Set<Book> books;
}
