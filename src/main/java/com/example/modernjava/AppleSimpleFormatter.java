package com.example.modernjava;

public class AppleSimpleFormatter implements AppleFormatter{

  @Override
  public String accept(Apple apple) {
    return "apple : " + apple.getWeight() + "g";
  }

}
