package com.example.modernjava;

public class Dish {

  private final String name;

  private final boolean vegetartian;

  private final int calories;

  private final Type type;

  public Dish(String name, boolean vegetartian, int calories, Type type) {
    this.name = name;
    this.vegetartian = vegetartian;
    this.calories = calories;
    this.type = type;
  }

  public CaloricLevel getCaloricLevel() {
    if(this.getCalories() <= 400) return  CaloricLevel.DIET;
    else if (this.getCalories() <= 700) return CaloricLevel.NORMAL;
    else return CaloricLevel.FAT;
  }

  @Override
  public String toString() {
    return "Dish{" +
        "name='" + name + '\'' +
        ", vegetartian=" + vegetartian +
        ", calories=" + calories +
        ", type=" + type +
        '}';
  }

  public String getName() {
    return name;
  }

  public boolean isVegetartian() {
    return vegetartian;
  }

  public int getCalories() {
    return calories;
  }

  public Type getType() {
    return type;
  }

  public enum Type {
    MEAT , FISH , OTHER
  }

}
