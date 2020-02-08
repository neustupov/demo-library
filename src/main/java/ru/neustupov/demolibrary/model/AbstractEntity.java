package ru.neustupov.demolibrary.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
abstract class AbstractEntity {

  @Id
  @Setter
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
