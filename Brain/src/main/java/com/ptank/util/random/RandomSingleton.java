package com.ptank.util.random;

import java.util.Random;

public class RandomSingleton {

	private Random random = new Random();

	public static Random getInstance() {
		return SingletonHolder.instance.random;
	}

	private RandomSingleton() {
	}

	private static final class SingletonHolder {
		static final RandomSingleton instance = new RandomSingleton();
	}

}
