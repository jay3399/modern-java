package com.example.modernjava;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

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

  @Test
  void testMoveAllPointsRightBy() throws Exception{
    List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));

    List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));

    List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);

    assertEquals(expectedPoints, newPoints);
  }

}