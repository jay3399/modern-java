package com.example.modernjava;

import static com.example.modernjava.Color.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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



	}

}
