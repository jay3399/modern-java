package com.example.modernjava;

import static com.example.modernjava.Color.*;
import static com.example.modernjava.Dish.Type.*;

import com.example.modernjava.Dish.Type;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModernJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModernJavaApplication.class, args);

		List<Apple> apples = Arrays.asList(new Apple(130, RED , 2), new Apple(170, GREEN , 1 ),
				new Apple(190, RED , 5),
				new Apple(110, RED , 6 ), new Apple(130, GREEN , 3 ) , new Apple(130 , BROWN ,7) , new Apple(170 , RED , 8));





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
						j ->( j + i) % 3 == 0
				).map(
						j -> new int[]{i, j}
				)
		).collect(Collectors.toList());

		for (int[] ints : collect19) {
			for (int anInt : ints) {
				System.out.println("anInt = " + anInt);
			}
		}


	}




	// Function<Double , Double >  에비해 결과를 박싱하지않아도된다 . double -> Double 박싱과정
	public static double integrate(DoubleFunction<Double> f, double a, double b) {

		return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;

	}



	public static List<Block> map(List<Integer> list, Function<Integer,Block> f) {

		List<Block> result = new ArrayList<>();

		for (Integer i : list) {
			result.add(f.apply(i));
		}

		return result;


	}



}
