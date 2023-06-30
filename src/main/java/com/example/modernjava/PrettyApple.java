package com.example.modernjava;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class PrettyApple {

  private final List<Apple> appleList;

  private final AppleFormatter appleFormatter;

  public PrettyApple(List<Apple> appleList, AppleFormatter appleFormatter) {
    this.appleFormatter = appleFormatter;
    this.appleList = appleList;
  }

  public void prettyPrintApple(List<Apple> appleList, AppleFormatter appleFormatter) {

    for (Apple apple : appleList) {
      String accept = appleFormatter.accept(apple);
      System.out.println("accept = " + accept);

    }
  }

  public List<Apple> filterApple(List<Apple> appleList, ApplePredicate applePredicate) {

    List<Apple> result = new ArrayList<>();

    for (Apple apple : appleList) {

      if (applePredicate.test(apple)) {
        result.add(apple);
      }

    }
    return result;
  }

  // 제네릭 파라미터는 오직 참조형만 가능 , 오토박싱 메모리이슈  -> 여러 인터페이스 제공
  public List<Apple> filterAppleV2(List<Apple> appleList, Predicate<Apple> predicate) {

    List<Apple> result = new ArrayList<>();

    for (Apple apple : appleList) {

      if (predicate.test(apple)) {
        result.add(apple);
      }

    }
    return result;
  }


  public void prettyPrintAppleV2(List<Apple> appleList, Consumer<Apple> consumer) {

    for (Apple apple : appleList) {

      consumer.accept(apple);


    }

  }


  public void getWeight(List<Apple> appleList, ToIntFunction<Apple> function) {

    for (Apple apple : appleList) {

      System.out.println("function = " + function.applyAsInt(apple));


    }

  }



}