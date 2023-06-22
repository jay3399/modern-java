package com.example.modernjava;

public class Trader {

  private String name;
  private String location;

  public Trader(String name, String location) {
    this.name = name;
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return "Trader{" +
        "name='" + name + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
