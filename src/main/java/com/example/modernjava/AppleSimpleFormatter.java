package com.example.modernjava;

import org.springframework.stereotype.Component;

public class AppleSimpleFormatter implements AppleFormatter{

  @Override
  public String accept(Apple apple) {
    return "apple : " + apple.getWeight() + "g";
  }

}
