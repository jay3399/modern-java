package com.example.modernjava.Strategy;

import org.springframework.web.bind.annotation.GetMapping;


public class Validator {

  public final ValidationStrategy strategy;

  public Validator(ValidationStrategy v) {
    this.strategy = v;
  }

  public boolean validate(String s) {
    return strategy.excute(s);
  }
}
