package com.rocky.learn_java.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.print.attribute.IntegerSyntax;

public class StreamApis {
	public static void main(String[] args) {

		List<String> l = new ArrayList<>();
		l.add("10");
		l.add("1000");
		// map的示例
		// l.stream().map(n -> Integer.valueOf(n)).reduce(Integer::sum);

		// Integer stream
		List<Integer> il = Arrays.asList(1, 2, 3, 15, 8, 7, 21, 9, 30);
		il.stream().sorted().forEach(System.out::println);
		System.out.println("*************");
		il.stream().filter(a -> a>10).forEach(System.out::println);
		System.out.println("*************");
		System.out.println(il.stream().filter(a -> a>10).reduce(Integer::sum));

		IntStream.of(1,2,3,4,5,6,7,8).min();
		
		
		// 数组stream
		Stream.of("1", "2", "3");
		int[] A = { 1, 2 };
		Arrays.stream(A);

	}

}
