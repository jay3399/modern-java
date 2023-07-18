package com.example.modernjava.Strategy;

public class SpellCheckerProcessing extends ProcessingObject<String>{



  @Override
  protected String handWork(String input) {
    System.out.println("checkSpell");
    return input.replaceAll("labda", "lambda");

  }


}
