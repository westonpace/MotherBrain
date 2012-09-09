package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ptank.util.random.RandomSingleton;

public class NeuralNoise implements NeuralInput {

	private NeuralInput source;
	private double minMultiplier;
	private double range;
	private Random random = RandomSingleton.getInstance();
	
	public NeuralNoise(NeuralInput source, double minMultiplier, double maxMultiplier) {
		this.source = source;
		this.minMultiplier = minMultiplier;
		this.range = maxMultiplier - minMultiplier;
	}

	@Override
	public List<Double> getNextInput() {
		ArrayList<Double> result = new ArrayList<Double>();
		for(Double value : source.getNextInput()) {
			double mult = (random.nextDouble() * range) + minMultiplier;
			value *= mult;
			result.add(value);
		}
		return result;
	}

	@Override
	public int size() {
		return source.size();
	}
	
}
