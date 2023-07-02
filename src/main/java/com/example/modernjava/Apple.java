package com.example.modernjava;

public class Apple {

  private int weight;

  private Enum color;

  private int num;

  public int getWeight() {
    return weight;
  }

  public int getNum() {
    return num;
  }

  public Enum getColor() {
    return color;
  }

  public Apple(int weight, Enum color , int num) {
    this.weight = weight;
    this.color = color;
    this.num = num;
  }

  @Override
  public String toString() {
    return "Apple{" +
        "weight=" + weight +
        ", color=" + color +
        ", num=" + num +
        '}';
  }
}
