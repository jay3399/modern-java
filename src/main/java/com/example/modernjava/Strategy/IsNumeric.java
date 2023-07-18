package com.example.modernjava.Strategy;


public class IsNumeric implements ValidationStrategy{

  @Override
  public boolean excute(String s) {
    return s.matches("\\d+");
  }

}
