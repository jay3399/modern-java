package com.example.modernjava;

public class AppleGreenColorPredicate implements ApplePredicate{

  @Override
  public boolean test(Apple apple) {
    return Color.GREEN == apple.getColor();
  }


}
