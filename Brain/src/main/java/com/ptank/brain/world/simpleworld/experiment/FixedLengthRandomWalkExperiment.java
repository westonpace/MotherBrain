package com.ptank.brain.world.simpleworld.experiment;

import java.util.Random;

import com.ptank.brain.core.Experiment;
import com.ptank.brain.world.simpleworld.SimpleWorld;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;
import com.ptank.util.random.RandomSingleton;

/**
 * In this experiment we place the mouse somewhere at random in the world
 * and have him think for 1000 turns.
 */
public class FixedLengthRandomWalkExperiment implements Experiment {

	private SimpleWorld world;
	private int numTurns = 1000;
	private Random random = RandomSingleton.getInstance();
	
	public FixedLengthRandomWalkExperiment(SimpleWorld world) {
		this.world = world;
	}
	
	public FixedLengthRandomWalkExperiment(SimpleWorld world, int numTurns) {
		this.world = world;
		this.numTurns = numTurns;
	}
		
	@Override
	public void runExperiment() {
		world.start();
		for(int i = 0; i < numTurns; i++) {
			world.tick();
		}
		world.clear();
	}
	
}
