package com.rocky.learn_java.basic.thread;


public class ThreadLearn {
	private int a = 0;
	private Integer counter = 3;
	//修饰方法
	public synchronized void synchronizedMethod() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void normalMethod() {
		//Can not call wait outside synchronized block
//		try {
//			counter.wait(100);
//			System.out.println("waited 100 million seconds on counter");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		synchronized(counter) {
			try {
				//this.wait(); // will throw IllegalMonitorStateException as not holding lock of this
				counter.wait(2000);
				System.out.println("waited 2 seconds on counter");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadLearn tl = new ThreadLearn();
		tl.normalMethod();
	}
	
}
