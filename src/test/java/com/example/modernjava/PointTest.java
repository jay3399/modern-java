package com.example.modernjava;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.xmlunit.util.Predicate;

class PointTest {

  /**
   * 람다테스트
   * moveRightBy 는 public이기 때문에 문제없이 작동 , 하지만 람다는 익명!이기떄문에 테스트코드 이름을 호출할수없다
   * 따라서 필요하다면 람다를 필드에 저장해서 재사용하며 람다의 로직을 테스트할수있다.
   * @throws Exception
   */

  @Test
  void testComparing() throws Exception{

    Point p = new Point(10 , 15
    );

    Point p2 = new Point(10, 20);

    int compare = Point.compareXY.compare(p, p2);

    assertTrue(compare < 0);


  }

  /**
   * 람다를 사용하는 메서드의 동작에 집중하라
   *
   * 람다의 목표 :
   * 정해진 동작을 다른메서드에서 사용할수있도록 하나의조각으로 캡슐화하는것이다.
   * 세부구현을 포함하는 람다표현식을 공개하지 말아야한다.
   * 람다표현식을 사용하는 메서드의 동작을 테스트함으로써 ㄷ람다를 공개하지 않으면서도 람다표힌슥을 검증한다
   *
   */
  @Test
  void testMoveAllPointsRightBy() throws Exception{
    List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));

    List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));

    List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);

    assertEquals(expectedPoints, newPoints);
  }


  /**
   * 고차원함수 테스트
   * 메서드가 람다를 인수로 받는다면 다른람다로 메서드의 동작을 테스트할수있다.
   */
  @Test
  void testFilter() throws Exception{

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

    Num num = new Num();

    List<Integer> integers = num.filterNum(i -> i % 2 == 0, numbers);

    List<Integer> integers1 = num.filterNum(i -> i < 3, numbers);

    assertEquals(Arrays.asList(2, 4), integers);
    assertEquals(Arrays.asList(1, 2), integers1);


  }

}