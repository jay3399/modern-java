package com.example.modernjava;

import static com.example.modernjava.Color.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModernJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModernJavaApplication.class, args);

		List<Apple> apples = Arrays.asList(new Apple(130, RED), new Apple(170, GREEN),
				new Apple(190, RED),
				new Apple(110, RED));





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

		BiFunction<Integer, Enum, Apple> aNew = Apple::new;

		Apple apply2 = aNew.apply(20, GREEN);

		System.out.println("apply2 = " + apply2.getWeight());
		System.out.println("apply2.getColor( = " + apply2.getColor());

	}


	public static List<Block> map(List<Integer> list, Function<Integer,Block> f) {

		List<Block> result = new ArrayList<>();

		for (Integer i : list) {
			result.add(f.apply(i));
		}

		return result;


	}



}
