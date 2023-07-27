package com.example.modernjava.DSL;


import static java.util.Arrays.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import scala.concurrent.impl.FutureConvertersImpl.P;

public class DSL {

  public static void main(String args[]) throws IOException {


    /**
     * 도메인전용언어
     *
     *
     * 언어의 주 목표 -> 메세지를 명확하고 , 안정적인 방식으로 전달
     * 프로그램은 사람들이 이해할수있도록 작성되어야하는것이 중요하며 , 기기가 실행하는 부분은 부차적이며 의도가 명학하게 전달되어야한다
     *
     * 읽기쉽고 , 이해하기쉬운코드.
     * 개발팀과 도메인전문가가 공유하고 이해할수있는 코드는 생산성과 직결되기 떄문.
     *
     * 도메인전문가 , 소프트웨어 개발 프로세스에 참여할수있고 비니지스 관점에서 소프트웨어가 제대로 되었는지 확인할수있다. -> 버그과 오해를 바지
     *
     * DSL 로 비니지스로직을 표현함으로써 이문제를 해결
     * 작은 범용이 아니라 , 특정도메인을 대상으로 만들어진 특수 프로그래밍언어.
     *
     * 특정 비지니스도메인의 문제를 해결하려고 만든 언어.
     * 자바에서는 도메인을 표현할수있는 클래스와 메서드 집합이 필요 , dsl 이란 특정비즈니스 도메인을 인터페이스로 만든 API
     *
     * 범용이아닌 , 특정도메인에 국한 -> 다른문제걱정없이 당장 자신앞에 놓은 문제를 어떻게 해결할지만 집중 , 특정도메인의 복잡성을 더 잘다룰있다
     * 저수준 구현 세부사항 메서드는 클래스의 비공개로 만들어서 저수준 구현 세부내용은 숨길수있다. -> 사용자 친화적 DSL
     *
     *
     * 의사소통 : 코드 의도가 명확하게 전달되어야하며 , 프로그래머가 아닌사람도 이해할수있어야한다
     * 한번 코드르 구현하지만 여러번 읽는다 : 가독성은 유지보수의 핵심. 팀원이 쉽게 이해할수있도록 코드를 구현
     *
     *
     * 장점 :
     * 간결함 ( api는 비지니스로직을 캡슐화 , 반복을 피할수있어 코드를 간결하게 만든다 )
     * 가독성 ( 도메인영억의 용어를 사용하므로 , 비 도메인 전문가또한 코드를 쉽게 이해할수있다 )
     * 유지보수 ( 잘설계뙨 dsl 로 구련한 코드는 쉽게 유지보수하고 바꿀수있다 )
     * 높은수준의 추상화 ( 도메인과 같은 추상화 수준에서 동작하므로 도메인의 문제와 직접적으로 관련되지않은 세부 사항을 숨긴다 . )
     * 집중 ( 비지니스 도메인의 규칙을 표현할 목적으로 설계된 언어이므로 프로그래머가 특정 코드에 집중할수있다 )
     * 관심사분리 ( 어플리케이션 의 인프라구조와 관련된 문제와 독립적을 비지니스관련된 코드에서 집중하기가 용이하다 -> 결과적으로 유지보수가 쉬;운 코드 )
     *
     * 단점 :
     * DSL 설계의 어려움 : 간결하게 제한적인 언어에 도메인 지식을 담는것은 쉬운작업이 아님
     * 개발비용 : DSl을 추가하는 작업은 초기프로젝트에 많은 비용과 시간이 소모되는 작업
     * 추가우회계층 : 추가적인 계층으로 도메인 모델을 감싸며 이댸 계층을 최대한 작게만들어 성능문제를 회피한다 .
     * 새로 배워야 하는 언어
     * 호스팅언어의 한계 : 자바 -> 장황하고 엄격한문법 .. 사용자친화적DSL 만들기 어렵다. 자바8 람다로 해결 !!
     *
     *
     * DSL : 내부 , 외부 나뉜다.
     * 내부 - 순수자바코드같은 기존 호스팅언어를 기반으로 구현
     * 외부 - 독립적인 자체문법
     * JVM - 내부 , 외부의 중간에 DSL이 만들어질 가능성이 생김. 다중 DSL
     */

    List<String> nums = Arrays.asList("one", "two", "three");

    nums.forEach(new Consumer<String>() {
      @Override
      public void accept(String s) {
        System.out.println(s);
      }
    });

    // -> 코드의 잡음 : forEach , accept , println

    nums.forEach(s -> System.out.println(s));
    nums.forEach(System.out::println);

    /**
     * 자바 바이트코드를 사용하는 JVM 기반 프로그래밍언어 , 다중 Dsl
     *
     * jvm 실행되는언어 100개 ++ , 스칼라 루비.. jRuby , jython ..
     * Kotlin , Ceylon 같이 스칼라와 호환성을 유지하면서 , 단순하고 쉽게배울수있는 강점을가진 언어.
     *
     * Dsl은 기반 프로그래밍언어의 영향을 받으므로 간결한 dsl을 만드는데 새로운 언어의 특성 중요
     * 특히 스칼라는 커링 , 임의변환등 dsl 개발에 팔요한 특성을갖췄다.
     */









    /**
     * 최신자바의 작은 API
     *
     * Comparator 인터페이스에 새 메서드가 추가외었다.
     */

    List<Person> persons = Arrays.asList(new Person(20 , "jay"), new Person(30 , "sally"), new Person(10 , "liz"),
        new Person(25 , "sera"));

    // 내부클래스로 인터페이스 구현.

//    Collections.sort(persons, new Comparator<Person>() {
//      @Override
//      public int compare(Person o1, Person o2) {
//        return o1.getAge() - o2.getAge();
//      }
//    });



    // 내부클래스 -> 람다

    Collections.sort(persons, (o1, o2) -> o1.getAge() - o2.getAge());

    /**
     * 정적유틸리티메서드제공 , 정적메서드는 Comparator 인터페이스에 포함돼있다.
     *
     */
    Collections.sort(persons , Comparator.comparingInt(Person::getAge));

    Collections.sort(persons, Comparator.comparingInt(Person::getAge).reversed());

//    Collections.sort(persons,
//        Comparator.comparingInt(Person::getAge).thenComparing(Person::getName));

    persons.sort(Comparator.comparingInt(Person::getAge).thenComparing(Person::getName));



    for (Person person : persons) {
      System.out.println("person = " + person.getAge());
    }





    /**
     * 문제가 분리되지않아 가독성과 유지보수성이 모두 저하되었다
     * 같은의무를 지닌 코드가 여러행에분산
     * FIleReader가 만들어짐
     * file이 종료되었는지 확인하는 while루프의 두번째조건
     * 파일의 다음행을 읽는 while루프의 마지막행
     *
     * 40행수집 코드도 세부분으로 흩어져있다
     * errorCount변수 초기화 코드
     * while루프의 첫번쨰조건
     * Error 로그를 발견하면 카운트증가행
     */



    List<String> error = new ArrayList<>();


    int errorCount = 0 ;

    BufferedReader bufferedReader = new BufferedReader(
        new FileReader("/Users/jay/Downloads/test/test1.txt"));

    String line = bufferedReader.readLine();

    while (errorCount < 40 && line != null) {
      if (line.startsWith("ERROR")) {
        error.add(line);
        errorCount++;
      }
      line = bufferedReader.readLine();
    }

    List<String> error1 = Files.lines(Paths.get("/Users/jay/Downloads/test/test1.txt"))
        .filter(line1 -> line1.startsWith("ERROR"))
        .limit(40)
        .collect(Collectors.toList());

    System.out.println("error1 = " + error1);

//    long uniqueWords = 0;
//
//    try (Stream<String> lines = Files.lines(Paths.get("/Users/jay/Downloads/test/test1.txt"), Charset.defaultCharset()))
//    {
//      uniqueWords =
//          lines.flatMap(
//              line -> stream(line.split(" ")).distinct()
//          ).count();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    System.out.println("uniqueWords = " + uniqueWords);
//

    /**
     *  Collectors -> P334
     */











  }

}
