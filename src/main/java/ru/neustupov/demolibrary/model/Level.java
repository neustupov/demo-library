package ru.neustupov.demolibrary.model;

public enum Level {
  ONE("One"),
  TWO("Two"),
  THREE("Three");

  private String code;

  Level(String code) {
    this.code = code;
  }
}
