package com.example.modernjava;

import static com.example.modernjava.CaloricLevel.*;
import static com.example.modernjava.Color.BROWN;
import static com.example.modernjava.Color.GREEN;
import static com.example.modernjava.Color.RED;
import static com.example.modernjava.Dish.Type.FISH;
import static com.example.modernjava.Dish.Type.MEAT;
import static com.example.modernjava.Dish.Type.OTHER;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

import com.example.modernjava.Dish.Type;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
public class Main {

  public static void main(String[] args) {

    List<Apple> apples = asList(new Apple(130, RED, 2), new Apple(170, GREEN, 1),
        new Apple(190, RED, 5),
        new Apple(110, RED, 6), new Apple(130, GREEN, 3), new Apple(130, BROWN, 7),
        new Apple(170, RED, 8));

    Thread hello = new Thread(
        new Runnable() {
          @Override
          public void run() {
            System.out.println("hello");
          }
        }
    );

    AppleFormatter appleFormatter = new AppleFancyFormatter();

    ApplePredicate applePredicate = new AppleGreenColorPredicate();

    PrettyApple prettyApple = new PrettyApple(apples, appleFormatter);

    prettyApple.prettyPrintApple(apples, appleFormatter);

    // 동작파라미터화
    List<Apple> apples1 = prettyApple.filterApple(apples, applePredicate);

    // 동작파라미터 + 람다
    List<Apple> apples2 = prettyApple.filterApple(apples, apple -> GREEN == apple.getColor());

    // 함수형인터페이스 + 람다
    List<Apple> apples3 = prettyApple.filterAppleV2(apples, apple -> RED == apple.getColor());

    for (Apple apple : apples1) {
      System.out.println("apple = " + apple.getWeight());
    }

    for (Apple apple : apples2) {
      System.out.println("apple = " + apple.getWeight());

    }

    prettyApple.prettyPrintAppleV2(apples, apple -> System.out.println(apple.getWeight()));

//    생성자 참조 만든뒤 , 사용한다

//    디폴트 생성자참조
//		Supplier<Block> blockSupplier = Block::new;
//
//		Block block = blockSupplier.get();

//    무게포함 생성자

    IntFunction<Block> blockIntFunction = Block::new;

    // int -> Integer 오토박싱 , 메모리 소모 .
    Function<Integer, Block> blockFunction = Block::new;

    Block apply = blockIntFunction.apply(30);

    Block apply1 = blockFunction.apply(20);

    System.out.println("apply.getWeight() = " + apply.getWeight());

    System.out.println("apply1 = " + apply1.getWeight());

    List<Integer> weights = asList(3, 2, 10, 7);

    List<Block> map = map(weights, Block::new);

    for (Block block : map) {

      System.out.println("block = " + block.getWeight());

    }

//		BiFunction<Integer, Enum, Apple> aNew = Apple::new;
//
//		Apple apply2 = aNew.apply(20, GREEN);
//
//		System.out.println("apply2 = " + apply2.getWeight());
//		System.out.println("apply2.getColor( = " + apply2.getColor());

    // 생성자 , 파라미터 3개일시에는 , 직접 인터페이스 만든다 .
    TriFunction<Integer, Integer, Integer, Tri> function = Tri::new;

    //디폴트는 오름처순 , then comparing 안해도 항목같으면 디폴트 오름차순비교  , but 다음은 내림차순하고싶으면 then 메서드

    apples.sort(Comparator.comparing(Apple::getWeight).thenComparing(Apple::getNum).reversed());

    for (Apple apple : apples) {
      System.out.println("apple= num: " + apple.getNum() + " weight=" + apple.getWeight() + "color="
          + apple.getColor());
    }

    Predicate<Apple> redApple = apple -> apple.getColor() == RED;

    Predicate<Apple> negate = redApple.negate();

    Predicate<Apple> redAppleAndHeavy = redApple.and(apple -> apple.getWeight() > 150);



    List<Apple> collect = apples.stream().filter((redApple)).collect(toList());

    List<Apple> collect1 = apples.stream().filter(negate).collect(toList());

    List<Apple> collect2 = apples.stream().filter(redAppleAndHeavy)
        .collect(toList());

    for (Apple apple : apples) {

      if (redApple.test(apple)) {

      }

    }

    System.out.println("collect = " + collect);

    System.out.println("collect1 = " + collect1);

    System.out.println("collect2 = " + collect2);

    Function<Integer, Integer> f = x -> x + 1;

    Function<Integer, Integer> g = x -> x * 2;

    Function<Integer, Integer> h = f.andThen(g); //  == g(f(x)) 합성함수!!!!

    List<Integer> integers = asList(3, 6, 7, 8);

    List<Integer> collect3 = integers.stream().map(g).collect(toList());

    System.out.println(collect3);

    List<Integer> result = new ArrayList<>();

    for (Integer integer : integers) {

      result.add(g.apply(integer));

    }

    System.out.println(result);

    double integrate = integrate(x -> x + 10, 3, 7);

    System.out.println("integrate = " + integrate);

    /**
     * -------------------------------------------------------------------------------------------------------------
     * 함수형인터페이스 -> 추상메서드만을 정의하는 인터페이스
     * 함수형인터페이스가 기대되는곳 -> 람다쓸수있다
     * 람다는 함수형인터페이스 추상메서드를 즉석제공 , 람다표현전체가 함수형인터페이스의 인스턴스
     * 메서드참조기능
     * 디폴트메서드 조합제공
     * 오토박싱 메모리효율을 고려한 , 기본형특화 인터페이스제공
     * -------------------------------------------------------------------------------------------------------------
     */

    List<Apple> appleList = new ArrayList<>();

    for (Apple apple : apples) {
      if (apple.getWeight() < 150) {
        appleList.add(apple);
      }
    }

    appleList.sort(
        Comparator.comparing(Apple::getWeight)
    );
//		Collections.sort(
//				appleList,
//				new Comparator<Apple>() {
//					@Override
//					public int compare(Apple o1, Apple o2) {
//						return Integer.compare(
//								o1.getWeight(), o2.getWeight(
//								));
//					}
//				}
//		);

    List<Integer> lowName = new ArrayList<>();

    for (Apple apple : appleList) {
      lowName.add(apple.getNum());
    }

    List<Integer> collect4 = apples.parallelStream().filter(
        apple -> apple.getWeight() < 150
    ).sorted(Comparator.comparing(Apple::getWeight)).map(
        apple -> apple.getNum()
    ).collect(toList());

    /**
     * 스트림 병렬처리 @@@@
     * 마치 sql 의 질의 @ 처럼 할수있다.
     *
     * 선언형 : 가독성++
     * 조립가능 : 유연성 ++
     * 병렬화 : 성능 ++
     */

    List<Dish> menu = asList(
        new Dish("pork", false, 800, MEAT),
        new Dish("beef", false, 700, MEAT),
        new Dish("chicken", false, 400, MEAT),
        new Dish("french fries", true, 530, OTHER),
        new Dish("rice", true, 350, OTHER),
        new Dish("season fruit", true, 120, OTHER),
        new Dish("pizza", true, 550, OTHER),
        new Dish("prawns", false, 300, FISH),
        new Dish("salmon", false, 450, FISH)
    );

    /**
     * 데이터소스 , 연속된요소 , 데이터처리연산 , 파이프라인
     */
    List<String> collect5 = menu.stream().filter(
        dish -> dish.getCalories() > 300
    ).map(Dish::getName).limit(3).collect(toList());

    System.out.println("collect5 = " + collect5);

    /**
     * 컬렉션 vs 스트림 -> 데이터를 "언제" 계산하느냐 .
     * 컬렉션의 모든요소는 컬렉션에 추가하기전에 계산되어야한다 , 컬렉션은 현재자료구조가 포함하는 모든값을 메모리에 저장하는 자료구조.
     * 스트림은 , 요청할때만 요소를계산하는 고정된 자료구조
     *
     * 컬렉션은 , 요소를 추가하거나 삭제가능-> 컬렉션의모든 요소를 메모리에 저장해야하며 , 컬렉션에 추가하려는 요소는 미리계산
     * vs
     * 스트림에 요소를 추가하거나 스트림에서 요소 제거 불가 -> 사용자가 요청하는 값 만 스트림에서 추출하는것이 핵심  생산자와 소비자관계
     * 게으르게->lazy 만들어지는 컬렉션  , 사용자가 데이터를 요청할때만 값을계산  , 사용자중심
     * 컬렉션은 적극적생성 , 생산자중심 , 팔기도전에 창고가득
     *
     * DVD  vs  스트리밍 , 적극적생성 / 게으른생성
     *
     */

    List<String> list = asList("a", "b", "c");

    Stream<String> stream = list.stream();

    stream.forEach(
        System.out::println
    );

//		stream.forEach(
//				System.out::println
//		); -> 스트림은 단 한번 소비가능

    List<String> name = new ArrayList<>();

    // 컬렉션 -> 반복자사용 ,  사용자가 직접 요소를 반복 for  -> 외부반복
    for (Dish dish : menu) {
      name.add(dish.getName());
    }

    // 스트림 , 반복자 필요x , 내부반복
    List<String> collect6 = menu.stream().map(
        Dish::getName
    ).collect(toList());

    /**
     * 장난가정리
     * 장난감있지 ? , 담자 -> 장난감있지 -> 담자 .. 장난감있지 ? - > 담자
     * vs
     * 바닥에있는 모든장난감 담아 & 여러손이용 & 모든장난감 상자근처로 한번에 가져가서 담는다
     *
     * 내부반복 , 병렬성을 스스로관리
     */

    /**
     * 중간연산 -> 최종연산
     * 중간연산은 , 단멸연산을 스트림파이프라인에 실행하기전까지는 아무연산도 수행하지않는다 , lazy !
     * 중간연산을 합친 다음에 , 합쳐진 중간연산을 최종연산으로 한번에처리
     */

    List<String> collect7 = menu.stream().filter(
            dish -> {
              System.out.println("filter:" + dish.getName()); // 필터링한 요리명 출력
              return dish.getCalories() > 300;
            }
        ).map(dish -> {
          System.out.println("map" + dish.getName()); // 추출한 요리명 출력
          return dish.getName();
        })
        .limit(3).collect(toList());

    System.out.println(collect7);

    List<Integer> integers1 = asList(1, 2, 1, 3, 3, 2, 4);

    integers1.stream()
        .filter(
            integer -> integer % 2 == 0
        ).distinct().forEach(System.out::println);

    List<Dish> dishes = asList(
        new Dish("chicken", false, 120, MEAT),
        new Dish("adasd", false, 150, OTHER),
        new Dish("zxcc", false, 170, OTHER),
        new Dish("qwqwe", false, 300, OTHER),
        new Dish("sad czc", false, 320, OTHER),
        new Dish("dazczx", false, 360, OTHER)
    );

    /**
     * 300칼로리 아래 음식 선택 ?
     * -> 기존방법 , 스트림필터사용
     * 전체스트림을 반복하며 , 각요소에 프리디케이트 적용
     *
     * 하지만 위는 이미 칼로리별로 정렬이 돼있는상태 -> 굳이 전체를 반복하며 적용할 필요가 없다 !!
     * -> Takewhile , Dropwhile 사용 전체를 안돌고 해당지점에 스탑
     * 만약 , 데이터소스가 엄청 크다면 성능적 이점이있다 .
     */

    List<Dish> collect8 = dishes.stream().takeWhile(
        dish -> dish.getCalories() < 300
    ).collect(toList());

    List<Dish> collect9 = dishes.stream().dropWhile(
        dish -> dish.getCalories() < 300
    ).collect(toList());

    System.out.println("collect8 = " + collect8);
    System.out.println("collect9 = " + collect9);

    List<Dish> collect10 = dishes.stream().filter(dish -> dish.getCalories() < 300).limit(2)
        .collect(toList());

    System.out.println("collect10 = " + collect10);
    List<Dish> collect11 = dishes.stream().filter(
        dish -> dish.getCalories() < 300
    ).skip(2).collect(toList());
    System.out.println("collect11 = " + collect11);

    menu.stream().filter(
        dish -> dish.getType() == MEAT
    ).limit(2).forEach(System.out::println);

    /**
     * Map , 함수를 적용한 결과가 새로운요소로 매핑된다
     * 기존의값을 고친다보단 , '새로운 버전을 만든다' -> 변환에 가까운 매핑
     */

    List<String> collect12 = dishes.stream().map(
        Dish::getName
    ).collect(toList());

    List<Integer> collect13 = dishes.stream().map(
        Dish::getName
    ).map(
        String::length
    ).collect(toList());

    List<String> list1 = asList("hello", "world");

    List<String[]> collect14 = list1.stream().map(
        s -> s.split("")
    ).distinct().collect(toList());
    //-> 배열스트림을 반환한다 , 문자열스트림이 필요

    String[] words = {"Goodbye", "World"};

    Stream<String> stream1 = stream(words); // 문자열 -> 스트림변환

    List<Stream<String>> collect15 = list1.stream().map(w -> w.split("")).map(Arrays::stream)
        .distinct().collect(toList());
    // 각 배열을 @별도의스트림@으로 생성
    // 해결 x

    // 문자열 배열 반환 !!
    List<String> collect16 = list1.stream().map(w -> w.split(""))
        .flatMap(Arrays::stream).distinct().collect(toList());
    // 생선된 스트림을 하나의 스트림으로 평면화 @@

    List<Integer> num = asList(1, 2, 3, 4, 5);

    List<Integer> collect17 = num.stream().map(
        a -> a * a
    ).collect(toList());

    System.out.println("collect17 = " + collect17);

    List<Integer> num1 = asList(1, 2, 3);

    List<Integer> num2 = asList(3, 4);

    List<int[]> collect18 = num1.stream().flatMap(
        i -> num2.stream().map(
            j -> new int[]{i, j}
        )
    ).collect(toList());

    List<int[]> collect19 = num1.stream().flatMap(
        i -> num2.stream().filter(
            j -> (j + i) % 3 == 0
        ).map(
            j -> new int[]{i, j}
        )
    ).collect(toList());

    for (int[] ints : collect19) {
      for (int anInt : ints) {
        System.out.println("anInt = " + anInt);
      }
    }


    /**
     * 쇼트서킷 기법
     * and 연선중 , 단하나라도 false가나오면 이후 결과와 상관없이 전체가 false
     * 아래 스트림역시 이와같이 작동 limit도 동일
     */

    if (menu.stream().anyMatch(Dish::isVegetartian)) {
      System.out.println("존재");
    }

    if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
      System.out.println("건강");
    }

    if (menu.stream().noneMatch(dish -> dish.getCalories() > 1000)) {
      System.out.println("건강");
    }

    // 다른 메서드와 조합가능
    Optional<Dish> any = menu.stream().filter(
        Dish::isVegetartian
    ).findAny();

    System.out.println(any.get());

    menu.stream().filter(
        Dish::isVegetartian
    ).findAny().ifPresent(dish -> System.out.println(dish.getName()));



    /**
     * findFirst vs findAny  , 요소의 반환순서가 상관없으면 findAny
     */

    /**
     * 리듀싱
     * 스트림 모든요소를 처리해서 값으로 도출
     * 리듀싱연산 -> 마치종이(스트림)를 작은조각이 될떄까지 반복해서 접는것 , 폴드
     */

    int[] integers2 = {1, 2, 3, 4, 5};

    int sum = 0;

    for (int integer : integers2) {
      sum += integer;
    }
    System.out.println(sum);

    IntStream stream2 = stream(integers2);

    int sum1 = stream2.sum();

    List<Integer> integers3 = asList(4, 5, 3, 9);

    Integer reduce = integers3.stream().reduce(
        0, (a, b) -> a + b
    );

    System.out.println("sum1 = " + sum1);
    System.out.println("reduce = " + reduce);

    Integer reduce1 = integers3.stream().reduce(
        1, (a, b) -> a * b
    );

    Integer reduce2 = integers3.stream().reduce(
        0, Integer::sum
    );

    System.out.println("reduce1 = " + reduce1);
    System.out.println("reduce2 = " + reduce2);

    Optional<Integer> reduce3 = integers3.stream().reduce(
        Integer::sum
    );
    System.out.println("reduce3 = " + reduce3);

    Optional<Integer> reduce4 = integers3.stream().reduce(
        Integer::max
    );
    System.out.println("reduce4 = " + reduce4);

    /**
     * map-reduce 패턴 , 쉽게 병렬화하는 특징
     *
     * reduce 이용 , 내부반복이 추상화되면서 내부구현에서 병렬로 reduce 실행
     * 반복적인 합계에서는 sum 변수를 공유 하기떄문에 , 병렬화자체가 힘들다
     * 강제적동기회 -> 병렬화로 얻어야할 이득 << 스레드간의 소모적 경쟁때문에 상쇄
     *
     *
     *
     */

    Integer reduce5 = menu.stream().map(
        a -> 1
    ).reduce(
        0, (a, b) -> a + b
    );

    long count = menu.stream().count();

    System.out.println("reduce5 = " + reduce5);
    System.out.println("count = " + count);

    /**
     * 대가지불해야한다 , 리듀스에 넘겨준 랑다의상태가 바뀌지말아야하고 , 연산이 어떤순서로 실행돼도 바뀌지 말아야한다
     */
    Integer reduce6 = integers3.parallelStream().reduce(
        0, Integer::sum
    );

    /**
     * reduce , sum , max -> 결과를 누적할 내부상태필요 , 예제는 작은값 int 또는 double 사용 , 내부상태크기는 한장되어있다
     *
     * sorted distinct 같은연산은 , 다른 스트림을 출력하는것같지만 정렬 , 중복제거를하려면 과거이력을 알고있어야한다
     * 모든요소가 버퍼에 추가되어 있어야한다@@
     * 연산을 수행하는데 필요한 저장소크기는 정해져있지않고 , 무한이면 문제가 생길수있다
     *
     */

    List<Integer> collect20 = integers3.stream().filter(
        integer -> integer % 3 == 0
    ).map(
        integer -> integer * 3
    ).collect(toList());

    System.out.println("collect20 = " + collect20);

    /**
     * 필터로모두 걸러지고 Map 으로 가는게 아닌 ,
     * 순차적으로 진행된다 ,
     * filter ->true -> map , filter -> false -> filter -> false -> filter -> true -> filter -> true ....
     */


    List<Double> collect21 = menu.stream().filter(
        m ->{
          System.out.println("check= " + m);
          return m.getCalories() > 500;
        }
    ).map(
        m ->{
          System.out.println("filter= " + m);
          return m.getCalories() * 0.3;
        }
    ).collect(toList());

    System.out.println("collect21 = " + collect21);

    /**
     * ---------------------------------------------------------------------------------------------
     */

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );

    List<Trader> traders = asList(
        raoul, mario, alan, brian
    );

    List<Transaction> collect22 = transactions.stream().filter(
            transaction -> {
              System.out.println("transaction = " + transaction.getYear());
              return transaction.getYear() == 2011;
            }
        ).sorted(Comparator.comparing(transaction ->
            transaction.getValue()))
        .collect(toList());



    System.out.println("collect22 = " + collect22);

    List<String> collect23 = traders.stream().map(
        trader -> trader.getLocation()
    ).distinct().collect(toList());

    System.out.println("collect23 = " + collect23);

    List<String> collect24 = transactions.stream().map(
        transaction -> transaction.getTrader().getLocation()
    ).distinct().collect(toList());

    System.out.println("collect24 = " + collect24);

    List<Transaction> collect25 = transactions.stream().filter(
        transaction -> transaction.getTrader().getLocation() == "Cambridge"
    ).sorted(Comparator.comparing(transaction -> transaction.getTrader().getName())).collect(
        toList());

    System.out.println("collect25 = " + collect25);

    List<String> collect26 = transactions.stream().map(
        transaction -> transaction.getTrader().getName()
    ).distinct().sorted().collect(toList());

    /**
     * ?
     */

    String collect28 = transactions.stream().map(
        transaction -> transaction.getTrader().getName()
    ).distinct().sorted().collect(joining());

    System.out.println("collect26 = " + collect26);
    System.out.println("collect28 = " + collect28);

    boolean b = transactions.stream().anyMatch(
        transaction -> transaction.getTrader().getLocation().equals("Milan")
    );

    System.out.println("b = " + b);

    List<Integer> collect27 = transactions.stream().filter(
        transaction -> transaction.getTrader().getLocation() == "Cambridge"
    ).map(transaction -> transaction.getValue()).collect(toList());

    System.out.println("collect27 = " + collect27);

    Optional<Integer> reduce7 = transactions.stream().map(
        Transaction::getValue
    ).reduce(
        Integer::max
    );

    /**
     * 2
     */

    Optional<Integer> reduce8 = transactions.stream().map(
        Transaction::getValue
    ).reduce(
        Integer::min
    );

    Optional<Transaction> reduce9 = transactions.stream().reduce(
        (t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2
    );

    Optional<Transaction> max = transactions.stream()
        .max(Comparator.comparing(Transaction::getValue));


    System.out.println("reduce7 = " + reduce7);
    System.out.println("max = " + max);

    System.out.println("reduce8 = " + reduce8);
    System.out.println("reduce9 = " + reduce9);

    /**
     * 아래는 , 중간에 언박싱과정이 들어간다 Integer -> int
     *
     * 역시 기본형 특화 스트림 제공된다
     * IntStream.
     */

    Integer reduce10 = transactions.stream().map(
        transaction -> transaction.getValue()    // Integer 반환
    ).reduce(
        0, Integer::sum                   // Integer -> int -> 연산 -> 다시 Integer
    );

    int reduce11 = transactions.stream().mapToInt(
        Transaction::getValue                    // int 반환
    ).reduce(
        0, Integer::sum                   // 연산 -> int
    );

    // IntStream 기본제공 메서드 사용가능 @@@

    int sum2 = transactions.stream().mapToInt(
        Transaction::getValue
    ).sum();


    System.out.println("reduce10 = " + reduce10);
    System.out.println("reduce11 = " + reduce11);
    System.out.println("sum2 = " + sum2);

    IntStream intStream = transactions.stream().mapToInt(
        Transaction::getValue
    );

    Stream<Integer> boxed = intStream.boxed();  // 객체스트림으로 변환

    transactions.stream().mapToInt(
        Transaction::getValue
    );

    /**
     * 정적 메서드 제공
     * closed 는 값포함
     * range 는 값 포함x
     */
    IntStream.rangeClosed(200, 400).filter(
        i -> i % 3 == 0
    ).forEach(
        s -> System.out.println("s = " + s)
    );

    /**
     * intStream 의 map 은 스트림의 각 요소를 int 배열로만 반환
     * 다른것을 원하면
     * box를 사용해서 integer로 변환후 시행
     */

    Stream<int[]> stream3 = IntStream.rangeClosed(1, 100).boxed().flatMap(
        x -> IntStream.rangeClosed(x, 100)
            .filter(y -> Math.sqrt(x * x + y * y) % 1 == 0)
            .mapToObj(y -> new int[]{x, y, (int) Math.sqrt(x * x + y * y)})
    );

    stream3.limit(5).forEach(
        s -> System.out.println(s[0] + "," + s[1] + "," + s[2])
    );

    Stream<String> hello1 = Stream.of(
        "hello", "world"
    );

    hello1.map(
        String::toUpperCase
    ).forEach(
        System.out::println
    );

    /**
     * null 이 될수있는 객체를 스트림으로 만들어야할떄 ,예를들어 빈스트림
     */

    Stream<Object> empty = Stream.empty();

    /**
     * 제공된 키에 대응하는 속성이없으면 null을 반환
     */
    String home = System.getProperty("home");

    System.out.println("home = " + home);

    Stream<String> stream4 = home == null ? Stream.empty() : Stream.of(home);

    Stream<String> home1 = Stream.ofNullable(System.getProperty("home"));

    Stream<String> stringStream = Stream.of(
        "config", "home", "user"
    ).flatMap(
        key -> Stream.ofNullable(System.getProperty("home"))
    );

    List<String> collect29 = stringStream.collect(toList());

    System.out.println("collect29 = " + collect29);

    int[] nums = {1, 2, 3, 4, 7, 11};

    IntStream stream5 = stream(nums); // intStream 반환

    System.out.println("stream5 = " + stream5.sum());

    /**
     * File IO
     *
     * 스트림의 소스가 IO 자원 -> try catch
     * 메모리 누수를 막으려면 자원을 닫아야 한다 finally
     * but , Stream 인터페이스는 AutoCloseable 인터페이스를 구현해서 try 블록내 자원을 자동으로 관리
     */


    long uniqueWords = 0;

    try (Stream<String> lines = Files.lines(Paths.get("/Users/jay/Downloads/test/test1.txt"), Charset.defaultCharset()))
    {
      uniqueWords =
          lines.flatMap(
              line -> stream(line.split(" ")).distinct()
          ).count();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("uniqueWords = " + uniqueWords);


    /**
     * 무한스트림
     * 기존의 고정된컬렉션에서 고정된 크기로 스트림을 만들었던것과 달리 , 크기가 고정되지 않은 무한 스트림
     * 요청할때마다 값을 생산하는 무한 , 언바운드 스트림
     */

    Stream.iterate(0, n -> n + 2)
        .limit(10).forEach(System.out::println);

    Stream.iterate(
        new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]}
    ).limit(20).forEach(
        s -> System.out.println("(" +s[0] + "," + s[1] +")")
    );

    List<Integer> collect30 = Stream.iterate(
        new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]}
    ).limit(20).map(n -> n[0]).collect(toList());

    System.out.println("collect30 = " + collect30);

    /**
     *  프리디케이트 지원
     */
    IntStream.iterate(
        0, n -> n < 100, n -> n + 4
    ).forEach(System.out::println);

    /**
     * filter 는 언제 멈춰야할지모르기때문에 종료되지않는다 ,  -  쇼트서킷! 을 지원하는 takeWhile 을 이용한다
     */

//    IntStream.iterate(
//        0, n -> n + 4
//    ).filter(
//        n -> n < 100
//    ).forEach(System.out::println);

    IntStream.iterate(
        0, n -> n + 4
    ).takeWhile(n -> n < 100).forEach(System.out::println);

    /**
     *  generate 메서드는 , iterate 와달리 generate 는 생산된 각 값을 연속적으로 계산하지않는다
     *  Supplier 를 인수로받아 새로운 값을 생산
     */

    Stream.generate(
        Math::random
    ).limit(5).forEach(System.out::println);

    /**
     * generate 어디다 사용할까 ?
     *
     * intStream 의 generate 메서드는 IntSupplier 을 인수로받는다
     * 람다로 인수를 전달하거나 vs 익명클래스를 사용하거나
     * 익명클래스는 메서드의 연산을 커스터마이즈 할수있는 상태필드를 정의할수있는것이 람다와 다르다 -> 부작용이 생길수있다
     * 가변상태 vs 불변상태(람다)
     * 스트림 병렬처리는 불변상태를ㄹ 유지해여한다
     */

    IntSupplier intSupplier = new IntSupplier() {
      private int previous = 0;
      private int current = 1;

      public int getAsInt() {
        int oldPrevious = this.previous;
        int nextValue = this.previous + this.current;
        this.previous = this.current;
        this.current = nextValue;
        return oldPrevious;
      }

    };

    IntStream.generate(intSupplier).limit(30).forEach(
        System.out::println
    );





    /**
     *  리듀싱 , 요약
     */

    Long collect31 = menu.stream().collect(counting());
    long count1 = menu.stream().count();

    System.out.println("collect31 = " + collect31);
    System.out.println("count1 = " + count1);

    Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

    Optional<Dish> collect32 = menu.stream().collect(maxBy(dishCaloriesComparator));
    Optional<Dish> collect40 = menu.stream().collect(minBy(dishCaloriesComparator));


    System.out.println("collect32 = " + collect32);
    System.out.println("collect40 = " + collect40);

    /**
     * 요약 연산
     * 스트림에 있는 객체의 숫자필드의 합계나 평균등을 반환하는 연산에도 리듀싱기능이 자주 사용된다 -> 요약
     *
     * summingInt 는 객체를 int 로 매핑하는 함수!를 인수로 받는다
     * summingInt 의 인수로 전달된 함수는 객체를 int 로 매핑한 컬렉터!를 반환한다
     * 마지막으로 summingInt 가 collect 메서드로 전달되면 요약작업 시작.
     *
     */

    /**
     * 각 요소에 , int 로 매핑하는 함수를 실행하고 , 그 요소를 모두 더하며 reduce 를  실행
     */

    Integer collect33 = menu.stream().collect(summingInt(Dish::getCalories));
    System.out.println("collect33 = " + collect33);

    Double collect34 = menu.stream().collect(averagingInt(Dish::getCalories));
    System.out.println("collect34 = " + collect34);

    // 모든정보 수집
    IntSummaryStatistics collect35 = menu.stream().collect(summarizingInt(Dish::getCalories));
    System.out.println("collect35 = " + collect35);

    //내부적으로 StringBuilder 를 이용해서 문자열을 하나로 만든다
    String collect36 = menu.stream().map(Dish::getName).collect(joining());
    System.out.println("collect36 = " + collect36);

    String collect37 = menu.stream().map(Dish::getName).collect(joining(", "));
    System.out.println("collect37 = " + collect37);

    Optional<String> reduce12 = menu.stream().map(Dish::getName).reduce(
        (c, d) -> c + "," + d
    );
    System.out.println("reduce12 = " + reduce12.get());

    Integer collect38 = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
    System.out.println("collect38 = " + collect38);

    Optional<Integer> reduce13 = menu.stream().map(Dish::getCalories).reduce(Integer::sum);

    System.out.println("reduce13 = " + reduce13);

    int sum3 = menu.stream().mapToInt(Dish::getCalories).sum();

    System.out.println("sum3 = " + sum3);

    /**
     *  아래처럼 여러 방법이있지만 , 결국 Joining을 사용하는것이 가독성 + 성능까지 더 좋다 !!!!
     */
    String s = menu.stream().map(Dish::getName).collect(reducing((a, c) -> a + c)).get();
    String s1 = menu.stream().map(Dish::getName).reduce((z, d) -> z + d).get();
    String collect39 = menu.stream().collect(reducing("", Dish::getName, (s2, s3) -> s2 + s3));
    System.out.println("s = " + s);
    System.out.println("s1 = " + s1);
    System.out.println("collect39 = " + collect39);

    /**
     * 그룹화
     *
     * groupingBy 메서드로 , Type 과 일치하는 요리를 추출할 함수!를 전달 -> '분류함수'
     */

    Map<Type, List<Dish>> collect41 = menu.stream().collect(groupingBy(Dish::getType));

    System.out.println("collect41 = " + collect41);

    /**
     * 복잡한 분류기준은 , 메서드참조가 사용불가 Dish 에 따로 메서드가 없다
     * 람다를 이용한다
     */

    Map<CaloricLevel, List<Dish>> collect42 = menu.stream().collect(
        groupingBy(
            dish -> {
              if (dish.getCalories() < 400)
                return DIET;
              else if (dish.getCalories() <= 700)
                return NORMAL;
              else
                return FAT;

            }
        )
    );

    System.out.println("collect42 = " + collect42);

    /**
     * 위는 요리종류 or 칼로리 기준으로 그룹화
     *
     * 요리종류 and 칼로리 두가지조건 기준으로 그룹화
     */

    // 아래는 Fish 키 자체가 사라진다 , 앞에서 filter 로 인해 .

    Map<Type, List<Dish>> collect43 = menu.stream().filter(dish -> dish.getCalories() > 500)
        .collect(groupingBy(Dish::getType));

    System.out.println("collect43 = " + collect43);

    /**
     * Collector 형식의 두번째 인수를 갖도록 , grouping 메서드를 오버로드해서 해결.
     */

    Map<Type, List<Dish>> collect44 = menu.stream()
        .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    System.out.println("collect44 = " + collect44);


    // Dish 리스트를 반환한것과 다르게 , mapping 을 이용해서 문자열 리스트 name 을 반환

    Map<Type, List<String>> collect45 = menu.stream().collect(groupingBy(
        Dish::getType, mapping(Dish::getName, toList())
    ));

    System.out.println("collect45 = " + collect45);

    Map<String, List<String>> dishTags = new HashMap<>();

    dishTags.put("pork", asList("greasy", "salty"));
    dishTags.put("beef", asList("salty", "roasted"));
    dishTags.put("chicken", asList("fried", "crisp"));
    dishTags.put("french fries", asList("greasy", "fried"));
    dishTags.put("rice", asList("light", "natural"));
    dishTags.put("season fruit", asList("fresh", "natural"));
    dishTags.put("pizza", asList("tasty", "salty"));
    dishTags.put("prawns", asList("tasty", "roasted"));
    dishTags.put("salmon", asList("delicious", "fresh"));

    /**
     * 두수준의 리스트를 한수준으로 평면화하려면 flatMap 을 수행
     * flatMapping 으로 중복을 없애기위해 리스트가아닌 집합으로 그룹화
     */

    Map<Type, Set<String>> collect46 = menu.stream().collect(groupingBy(Dish::getType,
        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

    Map<Type, Set<Stream<String>>> collect47 = menu.stream().collect(groupingBy(Dish::getType,
        mapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

    System.out.println("collect46 = " + collect46);
    System.out.println("collect47 = " + collect47);


    /**
     * 다수준 그룹화
     * 첫번째수준 GetType & 두번째수준 람다
     *
     * 버킷개념
     * -> 첫번째 groupingBy는 는 각 키의 버킷을만든다.
     * 준비된 각각의 버킷을 서브스트림 컬렉터로 채워가기를 반복하면서 n수준의 그룹화 달성
     */

    Map<Type, Map<CaloricLevel, List<Dish>>> collect48 = menu.stream().collect(
        groupingBy(Dish::getType,
            groupingBy(dish -> {
              if (dish.getCalories() <= 400)
                return DIET;
              else if (dish.getCalories() < 700)
                return NORMAL;
              else
                return FAT;
            }
        ))
    );

    System.out.println("collect48 = " + collect48);

    /**
     * 서브그룹으로 데이터수집
     * 분류함수 한개의 인수를 groupingBy(f) -> groupingBy(f, toList()) 의 축약형 , 그러므로 couting() 가능
     */

    Map<Type, Long> collect49 = menu.stream().collect(groupingBy(Dish::getType, counting()));

    System.out.println("collect49 = " + collect49);

    Map<Type, Optional<Dish>> collect50 = menu.stream().collect(
        groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories)))
    );

    System.out.println("collect50 = " + collect50);

    // 위는 , Optional 로 감쌀 필요가 없다.  andThen -> finisher

    /**
     * 218
     * 중첩연산 과정 , groupingBy -> CollectingAndThen -> maxBy (reducing Collector) -> Optional::get
     */
    Map<Type, Dish> collect51 = menu.stream().collect(
        groupingBy(Dish::getType,
            collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get))
    );
    System.out.println("collect51 = " + collect51);

    /**
     * 같은그룹으로 분류된 모든요소에 리듀싱작업수행
     * groupingBy에 두번째 인수로 전달할 컬렉터를 사용
     */

    Map<Type, Integer> collect52 = menu.stream()
        .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));

    System.out.println("collect52 = " + collect52);



    /**
     * mapping 은 입력요소를 누적전 매핑함수를 적용해서 다양한 형식의 객체를 주어진 형식의 컬렉터에 맞게 변환하는 역할.
     * 스트림의 인수를 변환하는 함수와 , 변환함수의 격과 객체를 누적하는 컬렉터를 인수로 받는다
     */

    Map<Type, Set<CaloricLevel>> collect53 = menu.stream().collect(groupingBy(
        Dish::getType, mapping(
            dish -> {
              if (dish.getCalories() <= 400)
                return DIET;
              else if (dish.getCalories() < 700)
                return NORMAL;
              else
                return FAT;
            }, toSet()
        )
    ));

    Map<Type, Map<CaloricLevel, List<Dish>>> collect55 = menu.stream()
        .collect(groupingBy(Dish::getType, groupingBy(dish -> {
          if (dish.getCalories() <= 400)
            return DIET;
          else if (dish.getCalories() < 700)
            return NORMAL;
          else
            return FAT;
        })));


    System.out.println("collect53 = " + collect53);
    System.out.println("collect55 = " + collect55);



    // + Set 형식을 정해줄수있다

    Map<Type, HashSet<CaloricLevel>> collect54 = menu.stream().collect(
        groupingBy(Dish::getType, mapping(
            dish -> {
              if (dish.getCalories() < 400)
                return DIET;
              else if (dish.getCalories() <= 700)
                return NORMAL;
              else
                return FAT;
            }, toCollection(HashSet::new)
        ))
    );


    System.out.println("collect54 = " + collect54);



    /**
     * 분할
     * 분할함수라 불리는 프리디케이트를 분류함수로 사용하는 특수한 그룹화기능
     * 분할함수는 불리언을 반환 , 맵의 키형식은 Boolean , 결과적으로 그룹회맵은 최대 두개의 그룹으로 분류
     */

    Map<Boolean, List<Dish>> collect56 = menu.stream().collect(groupingBy(Dish::isVegetartian));
    System.out.println("collect56 = " + collect56);

    Map<Boolean, List<Dish>> collect57 = menu.stream().collect(partitioningBy(Dish::isVegetartian));
    System.out.println("collect57 = " + collect57);

    List<Dish> dishes1 = collect56.get(true);
    System.out.println("dishes1 = " + dishes1);


    // 분할함수 + 두번째컬렉터

    Map<Boolean, Map<Type, List<Dish>>> collect58 = menu.stream()
        .collect(partitioningBy(Dish::isVegetartian, groupingBy(Dish::getType)));

    System.out.println("collect58 = " + collect58);

    Map<Boolean, Dish> collect59 = menu.stream().collect(partitioningBy(Dish::isVegetartian,
        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

    System.out.println("collect59 = " + collect59);


    // 1분할 -> isVegetartian ?
    // 2분할 -> 내부에서 , 다시 true false

    Map<Boolean, Map<Boolean, List<Dish>>> collect60 = menu.stream().collect(
        partitioningBy(Dish::isVegetartian, partitioningBy(dish -> dish.getCalories() > 500)));

    System.out.println("collect60 = " + collect60);

    Map<Boolean, Long> collect61 = menu.stream().collect(
        partitioningBy(Dish::isVegetartian, counting())
    );

    System.out.println("collect61 = " + collect61);





    /**
     * about Collector
     * <T,A,R>
     *   T: 수집될항목의 제네릭형식
     *   A: 누적자 , 중간결과를 누적하는 객체형식
     *   R: 수집 연산결과의 객체의형식 ( 대게는 컬렉션 )
     */

    /**
     * 새로운 결과 컨테이터 만들기
     * 1. Supplier<A> supplier()
     * 빈결과로 이루어진  supplier 를 반환
     * 수집과정에서 빈 누적자 인스턴스를 만드는 파라미터가 없는 함수
     */

    /**
     * 결과 컨테이너에 요소 추가하기
     * 2. accumulator
     * 리듀싱연산을 수행하는 함수를 반환 , n번째 요소를 탐색할때 두인수 즉 누적자!( 스트림의 n-1번 항목을 수집한상태와 )n번재 요소를 함수에 적용
     * 반환값은 void , 요소를 탐색하면서 적용하는 함수에의해 누적자 내부상태가 바뀌므로 어떤값인지 단정할수없다
     */
//    BiFunction<List<T> , T> accumulator(){ return (list, item) -> list.add(item) ;}

    /**
     * 최종 변환값을 결과 컨테이너로 적용  ex> and then finisher
     * 3. finisher
     * 스트림 탐색을 끝내고 누적자 객체를 최종 결과로 변환하면서 누적과정을 끝낼때 호추랄 함수를 반환해야한다
     */

    /**
     * 두 결과 컨테이너 병합
     * 4. combiner
     * 스트림의 서로다른 서브파트를 병렬로 처리할때 누적자가 이결과를 어떻게 처리할지 정의
     * 스트림의 리듀싱을 병렬로 수헹
     */

//    BinaryOperator<List<T>> combiner(){
//      return (list1 , list2) -> { list1.addAll(list2)};
//      return list1;
//    }

    List<Dish> collect62 = menu.stream().collect(new CustomToListCollector<Dish>());

    // == 아래 기존의것과 차이는 , new 로 인스턴스화 한다는점.

    List<Dish> collect63 = menu.stream().collect(toList());

    System.out.println("collect62 = " + collect62);
    System.out.println("collect63 = " + collect63);




    /**
     * 병렬화
     * 자바7이전  , 데이터 컬렉션 병렬처리가 힘들다 -> 자바 7이후 포크/조인 프레임워크 기능제공
     * 스트림 -> 쉽게 병렬화  , 멀티코어활용 파이프라인 연산
     *
     * Parallel stream -> 스트림요소를 여러 청크로 분할해 각각 스레드에 할당
     *
     * parallel -> filter -> sequential -> map -> parallel  -> reduce 연산마다 병렬 , 순차 바꾸기가능
     * 최총 호출된 메서드가 전체파이프라인에 영향을 미친다
     *
     * 병렬실행시 스레드는 어디서생성 , 몇개나생성 ? -> 병렬스트림은 내부적으로 조인/포크 프레임워크 사용
     *
     */

    long l = parallelSum(100000L);
    System.out.println("l = " + l);

    List<String> collect64 = dishes.stream().map(dish -> {
      System.out.println("adsd :" + dish.getName());
      return dish.getName();
    }).filter(s6 -> {
      System.out.println("filter :"+ s6);
      return s6.length() < 5;
    }).collect(toList());

    System.out.println("collect64 = " + collect64);

    /**----------------------------------------------------------------------------------------------------------------
     * 무조건 병렬스트림 ?
     * 언제나 병렬이 순차보다 빠른것 아니고 , 과정자체가 투명하지 않을수있다 . -> 직접 벤치 측정해본다.
     * 박싱 <-> 언박싱과정에서 생각보다 비용이 많이 소모된다 -> 일반적으로 기본형특화스트림이 좋다.
     * 병렬이 더 성능이떨어지는연산 -> limit, findFirst 처럼 요소의 순서에 의존하는 연산은 비싼 비용이든다.
     * but , findAny 는 요소의 순서에 상관없이 연산하므로 findFirst 보다 낫다.
     * unordered 로 비정렬 스트림을 얻고 , 요소의순서가 상과넚다면 , 비정렬스트림에 limit 를 호출하는것이 효과적.
     * 전체 파이프라인 연산비용을 고려해라 , 요소수가 N , 하나처리하는데  비용 Q -> 전체처리비용 -> N * Q , if Q++ -> 전체비용 ++ -> 병렬사용
     * 소량데이터는 당연히 병렬스트림이 도움되지않는다.
     *
     * 자료구조의 적절성
     * ex> LinkedList 는 분할하려면 모든요소를 탐색 , but ArrayList 는 요소탐색하지않고 분할가능 , ArrayList 가 더 효율적이다 , or Range 팩터리메서드로만든 기본형스트림도 쉽게 분해가능
     *
     * 스트림특성 , 파이프라인의 죽안연산이 스트림의 특성을 어떻게 바꾸냐에 따라 분해과정 성능이 달라진다
     * ex) sized 스트림은 "정확히 같은크기의" 두스트림으로 분할 할 수 있으므로 , 효과적인 병렬처리가 가능하지만
     * 필터연산이 있으면 , 스트림 길이 예측자체가 불가능하므로 효과적으로 스트림병렬 처리가 안된다. ( + iterate vs IntStream.range )
     *
     * 분해성 -
     * ArrayList , IntStream.range > HashSet , TreeSet  >>>> LinkedList , Iterate
     *----------------------------------------------------------------------------------------------------------------
     */



    /**
     * Fork Join
     *
     * 기대성능 안나온다,
     * join 호출시  , 테스크가 생산하는 결과를 준비될떄까지 호출자를 블록한다.
     * 서브태스크가 모두 시작된 다음에 , join 을 호출해야한다.
     * 각각의 서브태스크가 , 다른태스크가 끝나길 기다리는일이 발생하며 일반 순차알고리즘보다 더 복잡한 프로그래밈이 되어버린다.
     * RecursiveTask 내에사는 invoke 메서드를 사용하지말고 ,대신 compute 나 fork 메서드를 직접 호출할수있다.
     * 순차코드에서 병렬계산을 시작할때만 invoke 를 사용한다
     * 서브태스크에서 fork 메서드를 호출해서 ForkJoinPool 의 일정을 조절할수있다 , 오버헤드를 피할수있다 , P260
     * 포크조인프레임워크 이용하는 병렬계산은 디버깅이 어려움.
     *
     */

    long l1 = forkJoinSum(10_000_000L);
    System.out.println("l1 = " + l1);





    /**
     * 작업 훔치기
     * 서브태스크를 더 분할할것인지 결정할 기준을 어떻게 정할까 ?
     *
     * 코어개수와 관계없이 , " 적절한크기로 분할된 많은 태스크를 포킹하는게 바람직하다 "
     * ForkJoin 의 작업훔치기 기법은 , 모든스레드를 공정하게 분배  -> P262
     * but ? 앞에서는 , 분할로직 개발않고 , 병렬 스트림이용이 가능하다
     * 자동 스트림분할기법 -> Spliterator
    */





    /**
     * Spliterator
     * 분할할수있는 반복자.
     */








  }

  /**
   * 문제점 , 박싱 -> 언박싱 -> 박싱 과정에서 손실이 발생
   * 반복작업은 병렬로 수행할수있는 독립단위로 나누기가 어려움 -> iterate 는 이전 연산결과에따라 다음함수의 입력값이 달라지기때문에 청크 분할이 어렵다
   * 리듀싱을 하는시점에 , 전체 숫자리스트가 준비되지 않아 스트림을 병렬로 처리할수있도록 청크 분할이 불가
   */
  public static long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
  }



  /**
   *  기본형 long 을 그대로 사용하기떄문에 박싱과정 오버헤드 x
   *  rangedClosed 는 쉽겝 청크분할할수있는 숫자범위를 생산  , 1-20 범위를 1 -5  , 6 - 10  , 11-16, 16-20분할 -> 병렬 가능
   */


  /**
   * 병렬화를 사용하지 않고 기본형특화스트림을사용해, 박싱 언박싱과정을 없애는것만으로도 엄청난 성능향상이 일어난다
   * 적절한 자료구조를 선택하는것만으로도 좋은 성능을 내는것이 가능하다. !
   */
  public static long rangedSum() {
    return LongStream.rangeClosed(1, 10_000_000L).reduce(0L, Long::sum);
  }



  /**
   * 완벽한 멀티코어 사용
   *
   * 하지만 병렬화가 완전한 공짜는 아니고 , 병렬화를 이용하려면 스트림을 재귀적으로 분할하고 , 각 서브스트림을 각각다른 스레드의 리듀싱연산으로 할당 , 그리고이것을 하나의 값으로 합친다
   * 멀티코어간에 데이터 이동은 생각보다 비싸다
   * -> 결론은 코어간 데이터전송시간보다 훨씬 오래 걸리는 작업만 병렬로 다른코어에서 실행하도록 하는것이 맞다
   */
  public static long rangedSumWithParallel() {
    return LongStream.rangeClosed(1, 10_000_000L).parallel().reduce(0L, Long::sum);
  }


  /**
   *  본질적으로 순차실행이다
   *  특히@ 병렬 사용시  total 에 접근할떄 다수에 스레드에서 동시에 접근하는 레이스컨디션! 문제가 나타난다
   *  결과를 실행시 제대로된 결과가 나오지도 않는다
   *  결국 스레드에서 공유하는 객체의 상태를 바꾸는! forEach 블록 내부에서 add 메서드를 호출하면서 문제가 발생한다
   *
   *  결론 : 병렬계산에서는 공유된 가변상태!를 피해야한다 !!!!
   */
  public long sideEffectSum() {
    Accumlator accumlator = new Accumlator();
    LongStream.rangeClosed(1, 10_000_000L).parallel().forEach(accumlator::add);
    return accumlator.total;
  }






  /**--------------------------------------------------------------------------------------------------------------------------------------------
   * 만약 n이 커진다면 , 병렬로 처리하는것이 best
   * 결과 변수는 어떻게 동기화?
   * 몇개의 스레드를 사용하고 ?
   * 숫자는 어떻게생성하고  ,생성된 숫자는 어떻게 더할까 ?
   * ->
   * 병렬스트림으로 한방에 해결이 가능하다 .
   */

  public static long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
  }

  public static long iterativeSum(long n
  ) {
    long result = 0 ;
    for (long i = 1L; i <= n; i++) {
      result += i;
    }
    return result;
  }


  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();

    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);

    return new ForkJoinPool().invoke(task);
  }

  /**
   * --------------------------------------------------------------------------------------------------------------------------------------------
   */

  // Function<Double , Double >  에비해 결과를 박싱하지않아도된다 . double -> Double 박싱과정
  public static double integrate(DoubleFunction<Double> f, double a, double b) {

    return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;

  }


  public static List<Block> map(List<Integer> list, Function<Integer, Block> f) {

    List<Block> result = new ArrayList<>();

    for (Integer i : list) {
      result.add(f.apply(i));
    }

    return result;


  }


  public static char findMostRepeatedCharacter(String input) {
    Map<Character, Long> characterCounts = input.chars()
        .mapToObj(c -> (char) c)
        .collect(groupingBy(c -> c, counting()));


    char result = characterCounts.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse('\0');

    return result;


  }

  public static int solution(int[][] targets) {
    // 폭격 미사일들을 끝 지점을 기준으로 오름차순으로 정렬합니다.
    sort(targets, Comparator.comparingInt(a -> a[1]));

    for (int[] target : targets) {
      for (int i : target) {
        System.out.println("i = " + i);
      }
    }


    // 요격 미사일 수를 계산합니다.
    int interceptCount = (int) stream(targets)
        .filter(target -> target[0] > getPreviousMaxX(targets))
        .count();

    return interceptCount;
  }

  private static int getPreviousMaxX(int[][] targets) {
    // 이전에 처리한 폭격 미사일들 중 가장 큰 x 좌표를 구합니다.
    return stream(targets)
        .mapToInt(target -> target[1])
        .max()
        .orElse(-1);
  }

}

