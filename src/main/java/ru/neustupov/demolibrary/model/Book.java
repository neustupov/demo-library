package ru.neustupov.demolibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends AbstractEntity {

  @JsonDeserialize(as = Level.class)
  private Enum level;

  @ManyToOne
  @JsonIgnoreProperties("books")
  private Rack rack;

  private String name;
}
