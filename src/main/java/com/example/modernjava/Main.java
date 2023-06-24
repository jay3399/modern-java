package com.example.modernjava;

import static com.example.modernjava.Color.BROWN;
import static com.example.modernjava.Color.GREEN;
import static com.example.modernjava.Color.RED;
import static com.example.modernjava.Dish.Type.FISH;
import static com.example.modernjava.Dish.Type.MEAT;
import static com.example.modernjava.Dish.Type.OTHER;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;

public class Main {

  public static void main(String[] args) {

    List<Apple> apples = Arrays.asList(new Apple(130, RED, 2), new Apple(170, GREEN, 1),
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

    List<Integer> weights = Arrays.asList(3, 2, 10, 7);

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

    List<Apple> collect = apples.stream().filter((redApple)).collect(Collectors.toList());

    List<Apple> collect1 = apples.stream().filter(negate).collect(Collectors.toList());

    List<Apple> collect2 = apples.stream().filter(redAppleAndHeavy)
        .collect(Collectors.toList());

    for (Apple apple : apples) {
      // 일반적으로는 test 메서드를 써야하지만  , 스트림 filter 를쓰면 그럴필요가 없다 . @@@@@@@

      if (redApple.test(apple)) {

      }

    }

    System.out.println("collect = " + collect);

    System.out.println("collect1 = " + collect1);

    System.out.println("collect2 = " + collect2);

    Function<Integer, Integer> f = x -> x + 1;

    Function<Integer, Integer> g = x -> x * 2;

    Function<Integer, Integer> h = f.andThen(g); //  == g(f(x)) 합성함수!!!!

    List<Integer> integers = Arrays.asList(3, 6, 7, 8);

    List<Integer> collect3 = integers.stream().map(g).collect(Collectors.toList());

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
    ).collect(Collectors.toList());

    /**
     * 스트림 병렬처리 @@@@
     * 마치 sql 의 질의 @ 처럼 할수있다.
     *
     * 선언형 : 가독성++
     * 조립가능 : 유연성 ++
     * 병렬화 : 성능 ++
     */

    List<Dish> menu = Arrays.asList(
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
    ).map(Dish::getName).limit(3).collect(Collectors.toList());

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

    List<String> list = Arrays.asList("a", "b", "c");

    Stream<String> stream = list.stream();

    stream.forEach(
        System.out::println
    );

//		stream.forEach(
//				System.out::println
//		); -> 스트림은 단 한번 소비가능

    List<String> name = new ArrayList<>();

    // 컬렉션 -> 반복자사용 ,  사용자가 직접 요소를 반복 .  -> 외부반복
    for (Dish dish : menu) {
      name.add(dish.getName());
    }

    // 스트림 , 반복자 필요x , 내부반복
    List<String> collect6 = menu.stream().map(
        Dish::getName
    ).collect(Collectors.toList());

    /**
     * 장난가정리
     * 장난감있지 ? , 담자 -> 장난감있지 -> 담자 .. 장난감있지 ? - > 담자
     * vs
     * 바닥에있는 모든장난감 담아 & 여러손이용 & 모든장난감 상자근처로 한번에 가져가서 담는다
     *
     * 외부반복은 , 병렬성을 스스로관리
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
        .limit(3).collect(Collectors.toList());

    System.out.println(collect7);

    List<Integer> integers1 = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

    integers1.stream()
        .filter(
            integer -> integer % 2 == 0
        ).distinct().forEach(System.out::println);

    List<Dish> dishes = Arrays.asList(
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
    ).collect(Collectors.toList());

    List<Dish> collect9 = dishes.stream().dropWhile(
        dish -> dish.getCalories() < 300
    ).collect(Collectors.toList());

    System.out.println("collect8 = " + collect8);
    System.out.println("collect9 = " + collect9);

    List<Dish> collect10 = dishes.stream().filter(dish -> dish.getCalories() < 300).limit(2)
        .collect(Collectors.toList());

    System.out.println("collect10 = " + collect10);
    List<Dish> collect11 = dishes.stream().filter(
        dish -> dish.getCalories() < 300
    ).skip(2).collect(Collectors.toList());
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
    ).collect(Collectors.toList());

    List<Integer> collect13 = dishes.stream().map(
        Dish::getName
    ).map(
        String::length

    ).collect(Collectors.toList());

    List<String> list1 = Arrays.asList("hello", "world");

    List<String[]> collect14 = list1.stream().map(
        s -> s.split("")
    ).distinct().collect(Collectors.toList());
    //-> 배열스트림을 반환한다 , 문자열스트림이 필요

    String[] words = {"Goodbye", "World"};

    Stream<String> stream1 = Arrays.stream(words); // 문자열 -> 스트림변환

    List<Stream<String>> collect15 = list1.stream().map(w -> w.split("")).map(Arrays::stream)
        .distinct().collect(Collectors.toList());
    // 각 배열을 @별도의스트림@으로 생성
    // 해결 x

    // 문자열 배열 반환 !!
    List<String> collect16 = list1.stream().map(w -> w.split(""))
        .flatMap(Arrays::stream).distinct().collect(Collectors.toList());
    // 생선된 스트림을 하나의 스트림으로 평면화 @@

    List<Integer> num = Arrays.asList(1, 2, 3, 4, 5);

    List<Integer> collect17 = num.stream().map(
        a -> a * a
    ).collect(Collectors.toList());

    System.out.println("collect17 = " + collect17);

    List<Integer> num1 = Arrays.asList(1, 2, 3);

    List<Integer> num2 = Arrays.asList(3, 4);

    List<int[]> collect18 = num1.stream().flatMap(
        i -> num2.stream().map(
            j -> new int[]{i, j}
        )
    ).collect(Collectors.toList());

    List<int[]> collect19 = num1.stream().flatMap(
        i -> num2.stream().filter(
            j -> (j + i) % 3 == 0
        ).map(
            j -> new int[]{i, j}
        )
    ).collect(Collectors.toList());

    for (int[] ints : collect19) {
      for (int anInt : ints) {
        System.out.println("anInt = " + anInt);
      }
    }

//		char addddsssscccccc = findMostRepeatedCharacter("aaabbbccc");
//		System.out.println("addddsssscccccc = " + addddsssscccccc);
//
//		int[][] targets = {{1, 2}, {3, 1}, {6, 3}, {9, 7}};
//
//		solution(targets);

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

//		String[] input = new String[]{
//				"1622025201 REQUEST 10001 192.168.0.1",
//				"1622025202 REQUEST 10002 192.168.0.2",
//				"1622025203 REQUEST 10003 192.168.0.1",
//				"1622025211 RESPONSE 10003",
//				"1622025212 RESPONSE 10002",
//				"1622025213 RESPONSE 10001",
//				"1622025221 REQUEST 10004 192.168.0.2",
//				"1622025223 REQUEST 10005 192.168.0.2",
//				"1622025230 RESPONSE 10004",
//				"1622025231 REQUEST 10006 192.168.0.3",
//				"1622025236 RESPONSE 10006"
//		};

//		Map<String, Integer> ipRequestCount = new HashMap<>();
//
//		for (String log : input) {
//			String[] parts = log.split(" ");
//			String logType = parts[1];
//
//			if (logType.equals("REQUEST")) {
//				String ipAddress = parts[3];
//				ipRequestCount.put(ipAddress, ipRequestCount.getOrDefault(ipAddress, 0) + 1);
//			}
//		}
//
//		for (Map.Entry<String, Integer> entry : ipRequestCount.entrySet()) {
//			System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
//		}

//		Map<String, Long> ipRequestCount2 = Arrays.stream(input)
//				.filter(log -> log.split(" ")[1].equals("REQUEST"))
//				.map(log -> log.split(" ")[3])
//				.collect(Collectors.groupingBy(ip -> ip, Collectors.counting()));
//
//		ipRequestCount2.forEach((ip, count) -> System.out.println("(" + ip + ", " + count + ")"));

//
//		Map<String, Long> requestTimes = new HashMap<>();
//		Map<String, Long> responseTimes = new HashMap<>();
//
//		for (String log : input) {
//			String[] parts = log.split(" ");
//			long timestamp = Long.parseLong(parts[0]);
//			String logType = parts[1];
//			String requestId = parts[2];
//
//			if (logType.equals("REQUEST")) {
//				requestTimes.put(requestId, timestamp);
//			} else if (logType.equals("RESPONSE")) {
//				responseTimes.put(requestId, timestamp);
//			}
//		}
//
//		for (Map.Entry<String, Long> entry : requestTimes.entrySet()) {
//			String requestId = entry.getKey();
//			long requestTime = entry.getValue();
//			long responseTime = responseTimes.getOrDefault(requestId, -1L);
//
//			if (responseTime != -1) {
//				long elapsedTime = responseTime - requestTime;
//				System.out.println("(" + requestId + ", " + requestTime + ", " + elapsedTime + ")");
//			} else {
//				System.out.println("(" + requestId + ", " + requestTime + ", FAIL)");
//			}
//		}
//	}

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

    IntStream stream2 = Arrays.stream(integers2);

    int sum1 = stream2.sum();

    List<Integer> integers3 = Arrays.asList(4, 5, 3, 9);

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
    ).collect(Collectors.toList());

    System.out.println("collect20 = " + collect20);

    List<Double> collect21 = menu.stream().filter(
        m -> m.getCalories() > 500
    ).map(
        m -> m.getCalories() * 0.3
    ).collect(Collectors.toList());

    System.out.println("collect21 = " + collect21);

    /**
     * ---------------------------------------------------------------------------------------------
     */

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );

    List<Trader> traders = Arrays.asList(
        raoul, mario, alan, brian
    );

    List<Transaction> collect22 = transactions.stream().filter(
            transaction -> transaction.getYear() == 2011
        ).sorted(Comparator.comparing(transaction -> transaction.getValue()))
        .collect(Collectors.toList());

    System.out.println("collect22 = " + collect22);

    List<String> collect23 = traders.stream().map(
        trader -> trader.getLocation()
    ).distinct().collect(Collectors.toList());

    System.out.println("collect23 = " + collect23);

    List<String> collect24 = transactions.stream().map(
        transaction -> transaction.getTrader().getLocation()
    ).distinct().collect(Collectors.toList());

    System.out.println("collect24 = " + collect24);

    List<Transaction> collect25 = transactions.stream().filter(
        transaction -> transaction.getTrader().getLocation() == "Cambridge"
    ).sorted(Comparator.comparing(transaction -> transaction.getTrader().getName())).collect(
        Collectors.toList());

    System.out.println("collect25 = " + collect25);

    List<String> collect26 = transactions.stream().map(
        transaction -> transaction.getTrader().getName()
    ).distinct().sorted().collect(Collectors.toList());

    /**
     * ?
     */

    String collect28 = transactions.stream().map(
        transaction -> transaction.getTrader().getName()
    ).distinct().sorted().collect(Collectors.joining());

    System.out.println("collect26 = " + collect26);
    System.out.println("collect28 = " + collect28);

    boolean b = transactions.stream().anyMatch(
        transaction -> transaction.getTrader().getLocation().equals("Milan")
    );

    System.out.println("b = " + b);

    List<Integer> collect27 = transactions.stream().filter(
        transaction -> transaction.getTrader().getLocation() == "Cambridge"
    ).map(transaction -> transaction.getValue()).collect(Collectors.toList());

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

    List<String> collect29 = stringStream.collect(Collectors.toList());

    System.out.println("collect29 = " + collect29);

    int[] nums = {1, 2, 3, 4, 7, 11};

    IntStream stream5 = Arrays.stream(nums); // intStream 반환

    System.out.println("stream5 = " + stream5.sum());

    /**
     * File IO
     *
     * 스트림의 소스가 IO 자원 -> try catch
     * 메모리 누수를 막으려면 자원을 닫아야 한다 finally
     * but , Stream 인터페이스는 AutoCloseable 인터페이스를 구현해서 try 블록내 자원을 자동으로 관리
     */


    long uniqueWords = 0;

    try (Stream<String> lines = Files.lines(
        Paths.get("/Users/jay/Downloads/test/test1.txt"), Charset.defaultCharset())) {

      uniqueWords =
          lines.flatMap(
              line -> Arrays.stream(line.split(" ")).distinct()).count();


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
    ).limit(20).map(n -> n[0]).collect(Collectors.toList());

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


  }

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
        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));


    char result = characterCounts.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse('\0');

    return result;


  }

  public static int solution(int[][] targets) {
    // 폭격 미사일들을 끝 지점을 기준으로 오름차순으로 정렬합니다.
    Arrays.sort(targets, Comparator.comparingInt(a -> a[1]));

    for (int[] target : targets) {
      for (int i : target) {
        System.out.println("i = " + i);
      }
    }


    // 요격 미사일 수를 계산합니다.
    int interceptCount = (int) Arrays.stream(targets)
        .filter(target -> target[0] > getPreviousMaxX(targets))
        .count();

    return interceptCount;
  }

  private static int getPreviousMaxX(int[][] targets) {
    // 이전에 처리한 폭격 미사일들 중 가장 큰 x 좌표를 구합니다.
    return Arrays.stream(targets)
        .mapToInt(target -> target[1])
        .max()
        .orElse(-1);
  }

}

