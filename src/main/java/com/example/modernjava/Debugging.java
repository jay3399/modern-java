package com.example.modernjava;

import java.util.Arrays;
import java.util.List;



public class Debugging {

  /**
   * 스텍트레이드에 lambda..어쩌고저쩌고가 니온다 이름이 없기때문
   * 메서드 참조를사용해도 달라질건 없다.
   */

//  public static void main(String args[]) {
//    List<Point> pointList = Arrays.asList(new Point(12, 2), null);
//    pointList.stream().map(
//       Point::getX
//    ).forEach(System.out::println);
//  }

  /**
   *  정상적으로 출려이된다.
   */
  public static void main(String args[]) {
    List<Integer> integers = Arrays.asList(1, 2, 3);
    integers.stream().map(Debugging::divideByZero).forEach(System.out::println);

  }

  public static int divideByZero(int n) {
    return n / 0;
  }

}
