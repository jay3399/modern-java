package com.example.modernjava;

import static com.example.modernjava.Color.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
		 * 함수형인터페이스 -> 추상메서드만을 정의하는 인터페이스
		 * 함수형인터페이스가 기대되는곳 -> 람다쓸수있다
		 * 람다는 함수형인터페이스 추상메서드를 즉석제공 , 람다표현전체가 함수형인터페이스의 인스턴스
		 * 메서드참조기능
		 * 디폴트메서드 조합제공
		 * 오토박싱 메모리효율을 고려한 , 기본형특화 인터페이스제공
		 */


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
