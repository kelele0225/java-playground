package com.rocky.learn_java.basic;
import static java.lang.System.out;
public class Numberic {
	public static void main(String[] args) {
		for (int i = 0; i < 1<<4; i++) {
			out.println(Integer.valueOf(i>>1) + " " + Integer.valueOf(i^i>>1));
		}
	}
}
