package com.ptank.brain.neural.core;

import java.util.Random;

import com.ptank.util.random.RandomSingleton;

public class RandomWeightSource implements WeightSource {

	private Random random = RandomSingleton.getInstance();
	
	@Override
	public double[] getWeights(int size) {
		double [] result = new double[size];
		for(int i = 0; i < size; i++) {
			double nextValue = random.nextDouble();
			if(random.nextBoolean()) {
				nextValue = nextValue *= -1;
			}
			result[i] = nextValue;
		}
		return result;
	}

}
