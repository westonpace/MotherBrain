package com.ptank.brain.neural.core;

import java.util.Arrays;

public class FixedWeightSource implements WeightSource {

	private double value;
	
	public FixedWeightSource(double value) {
		this.value = value;
	}

	@Override
	public double[] getWeights(int size) {
		double [] result = new double[size];
		Arrays.fill(result, value);
		return result;
	}
	
}
