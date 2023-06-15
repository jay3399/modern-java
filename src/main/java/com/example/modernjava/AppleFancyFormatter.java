package com.example.modernjava;

public class AppleFancyFormatter implements AppleFormatter{

  @Override
  public String accept(Apple apple) {

    String s = apple.getWeight() > 150 ? "heavy" : "light";

    return s + " " + apple.getColor() + "apple";

  }
}
