package com.ptank.brain.neural.core;

public class StaticWeightSource implements WeightSource {

	private double[] weights;
	
	public StaticWeightSource(double [] weights) {
		this.weights = weights;
	}

	@Override
	public double[] getWeights(int size) {
		return weights;
	}
	
}
