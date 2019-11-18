package com.rocky.learn_java.basic;

import static java.lang.System.out;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringApis {
	public static void main(String[] args) {
		out.println((int)'b');
		out.println("abcd".toCharArray());
		out.println("abcd".substring(1, 2));
		
		String s1 = "a";
		String s2 = s1 + "b";
		String s3 = "a" + "b";
		System.out.println(s2 == "ab");
		System.out.println(s3 == "ab");
		
		String y = "beijing";
		change(y);
		System.out.println(y);
		
		String[] s = "I am a developer".split(" ");
		System.out.println(Stream.of(s).reduce("", (a, b)->a + " " + b));
		System.out.println(Stream.of(s).collect(Collectors.joining(",")));
		
	}
	
	public static void change(String x) {
		x = "Vancouver";
	}
}
