package com.example.modernjava.Strategy;

public class isAllLowerCase implements ValidationStrategy{

  @Override
  public boolean excute(String s) {
    return s.matches("[a-z]+");
  }

}
