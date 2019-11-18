package com.rocky.learn_java.basic.inner_class;

public class OuterInner {
	private Object o = null;
	private static Object staticOuterAttribute;
	public void method() {
		Object oo = new Object();
		class MethodInner{
			public Object o1 = o;

			public Object o2 = oo;
		}
		MethodInner mi = new MethodInner();
		
	}
	
	class Inner{
		Object innerO = o; // can access outer attribute
		
		String s = staticOuterAttribute.toString(); // can access outer static attribute
		//public static String staticS = null; // cannot define static attribute
	}
	
	static class StaticInner{
		//Object innerO = o; // cannot access outer attribute
		String s = staticOuterAttribute.toString(); // can access outer static attribute
		static String staticS = null; // can define static attribute
	}
	
	public static void main(String[] args) {
		
	}
}
