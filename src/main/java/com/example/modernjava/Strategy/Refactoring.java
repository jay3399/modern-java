package com.example.modernjava.Strategy;

import static com.example.modernjava.Dish.Type.FISH;
import static com.example.modernjava.Dish.Type.MEAT;
import static com.example.modernjava.Dish.Type.OTHER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import com.example.modernjava.CaloricLevel;
import com.example.modernjava.Dish;
import com.example.modernjava.Dish.Type;
import com.example.modernjava.Strategy.HeaderTextProcessing;
import com.example.modernjava.Strategy.IsNumeric;
import com.example.modernjava.Strategy.Loan;
import com.example.modernjava.Strategy.ProcessingObject;
import com.example.modernjava.Strategy.Product;
import com.example.modernjava.Strategy.ProductFactory;
import com.example.modernjava.Strategy.SpellCheckerProcessing;
import com.example.modernjava.Strategy.Validator;
import com.example.modernjava.Strategy.isAllLowerCase;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Refactoring {

  private final Validator validator;

  public Refactoring(Validator validator) {
    this.validator = validator;
  }

  public static void main(String[] args) {

    /**
     * 익명 vs 람다
     * 익명의 this 는 , 익명크래스 자신을 가르킨다
     * 람다의 this 는 , 람다를 감싸는 클래스를 가리킨다
     *
     * 익명클래스는 , 감싸고있는 클래스의 변수를 가릴수있다 ( 섀도변수 )
     * 람다는 , 변수를 가릴수없다
     */

    int a = 10;

    Runnable r1 = () -> {
//      int a = 2;    컴파일오류 !!!
      System.out.println("a = " + a);
    };

    Runnable r2 = new Runnable() {
      @Override
      public void run() {
        int a = 2;
        System.out.println(a);
      }

    };

    /**
     * 메서드참조
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

    Map<CaloricLevel, List<Dish>> collect = menu.stream()
        .collect(groupingBy(Dish::getCaloricLevel));

    /**
     * 정적 헬퍼 메서드 , comparing ... maxBy ..
     */

    menu.sort(Comparator.comparing(Dish::getCalories));

    /**
     * 리듀싱연산 , sum .. maximum
     * 메서드참조와 함께 사용할수있도록 ,내장 헬퍼메서드를 제공.
     */

    // 저수준 reducing 연산

    Integer reduce = menu.stream().map(Dish::getCalories).reduce(0, (s1, s2) -> s1 + s2);
    System.out.println("reduce = " + reduce);

    // VS

    // Collectors API

    Integer collect1 = menu.stream().collect(summingInt(Dish::getCalories));

    System.out.println("collect1 = " + collect1);

    /**
     * 유연성  -> 동작파라미터화
     */

    /**
     * 함수형인터페이스
     * 조건부 연기실행 , 실행어라운드 패턴  P300
     */

    /**
     * 디자인패턴
     * 다양한 패턴을 유형별로디자인 , 공통적인 소프트웨어문제를 설계할떄 재사용할수있는 검증된 청사진 제공.
     *
     * 람다사용시 , 디자인패턴으로 해결하던 문제를 간결하게해결가능
     * 람다표현식으로 기존의 만흥ㄴ 객체지향디자인패턴을 제거하거나 간결하게 재구현가능
     *
     * 전략 Strategy
     * 템플릿 메서드 Template method
     * 옵저버 Observer
     * 의무체인 chain of responsibility
     * 팩토리 factory
     *
     */

    /**
     * 전략
     * 한유형의 알고리즘을 보유한상태에서 , 런타임!에 적절한 알고리즘을 선택하는 기법
     *
     * 알고리즘을 나타내는 인터페이스 , 다양한 알고리즘을 나타내는 한개이상의 인터페이스 구현
     * 전략객체를 사용하는 한개 이상의 클라이언트
     */

    //+ 스프링 컴포넌트쓰면 더 깔끔하게도 가능할듯
    // 이부분역시 , 인터페이스가 기대되는곳 -> 람다 사용하며 클래스 없이구현가능하다 .

    Validator numericvalidator = new Validator(new IsNumeric());

    boolean aaaa = numericvalidator.validate("aaaa");

    System.out.println("aaaa = " + aaaa);

    Validator lowerCaseValidator = new Validator(new isAllLowerCase());

    boolean aaaa1 = lowerCaseValidator.validate("aaaa");

    System.out.println("aaaa1 = " + aaaa1);

    /**
     * 템플릿 메서드
     * 알고리즘의 개요를 제시한다음에 , 알고리즘의 일부를 고칠수있는 유연함을 제공해야할떄 .
     * OnlineBanking
     */

    /**
     * 의무체인
     * 한객체가 어떤 작업을 처리 -> 다음에 다른객체로 결과전달 -> 다른객체 작업처리 -> 다른객체로 전달..
     * <->
     * 람다 andThen 조합으로 가독성++
     */

    ProcessingObject p1 = new HeaderTextProcessing();
    ProcessingObject p2 = new SpellCheckerProcessing();

    p1.setSuccesor(p2);

    Object handle = p1.handle("Aren't labdas really sexy?!");
    System.out.println("handle = " + handle);

    UnaryOperator<String> headerProcessing = (String text) -> text;
    UnaryOperator<String> spellcheckerProcessing = (String text) -> text.replaceAll("labda",
        "lambda");

    Function<String, String> stringStringFunction = spellcheckerProcessing.andThen(
        headerProcessing);

    String apply = stringStringFunction.apply("Aren't labdas really sexy?!");

    System.out.println("apply = " + apply);

    /**
     * 팩토리
     * 인스타스화 로직을 클라이언트에 노출하지않고 , 객체를 만들떄 팩토리 디자인패턴을 사용
     *
     * 생성자와 , 설정을 외부로 노출하지않고 단순하게 상품을 생산.
     */

    Product loan = ProductFactory.createProduct("loan");

    System.out.println("loan = " + loan.getClass());

    Product loan1 = ProductFactory.createProductWithLambdas("loan");

    System.out.println("loan1.getClass() = " + loan1.getClass());

    /**
     * 디버깅 : 스틱 트레이스 & 로깅
     *
     * ex) 예외발생으로 프로그램실행이 중단되면 , 먼저 어디에서 멈췄고 어떻게 멈추게 되었는지 살펴봐야한다
     * 스택프레임 -> 호출위치 , 인수값 , 미서드의 지역변수등을 포함한 호출정보가 생성되며 , 이 정보는 스택프레임에 저장된다
     *
     * 프로그램이 멈추면 , 어떻게 멈추게되었는지 프레임별로 보여주는 스텍트레이스!를 얻을수가 있다 .
     * 즉 문제가 발생한 지점에 이르게된 메서드 호출리스트를 얻을수있고 , 이것을통해 문제가 어떻게 발생했는지 이해할수있다 .
     *
     * but , 람다 표현식은 이름이 없기떄문에 복잡한 스택트레이스가 생성된다. -> Debugging Class
     *
     */

    /**
     * 정보로깅
     */

    List<Integer> nums = asList(2, 3, 4, 5);

    nums.stream().map(x -> x + 17).filter(x -> x % 2 == 0).limit(2).forEach(System.out::println);

    // forEach는 호출되는 순간 전체스트림!이 소비된다 , 각각의연산이 어떤 결과를 호출하는지 알고싶다 ->

    /**
     * Peek
     * peek 은 스트림의 각 요소를 소비한것 처럼! 동작을 실행하지만 , forEach처럼 실제로 소비되지는 않는다
     * 자신이 확인한 요소를 파이프라인의 다음연산으로 전달한다
     */

    List<Integer> collect2 = nums.stream().peek(x -> System.out.println("from stream " + x))
        .map(x -> x + 17)
        .peek(x -> System.out.println("after map: " + x))
        .filter(x -> x % 2 == 0)
        .peek(x -> System.out.println("after filer: "+ x))
        .limit(3)
        .peek(x -> System.out.println("after limit: " + x))
        .collect(Collectors.toList());
  }
}
