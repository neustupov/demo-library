package ru.neustupov.demolibrary.model;

import java.util.HashMap;
import java.util.Map;

public enum Level {

  ONE(1),
  TWO(2),
  THREE(3);

  private int value;
  private static Map map = new HashMap<>();

  Level(int value) {
    this.value = value;
  }

  static {
    for (Level level : Level.values()) {
      map.put(level.value, level);
    }
  }

  public static Level valueOf(int level) {
    return (Level) map.get(level);
  }

  public int getValue() {
    return value;
  }
}
