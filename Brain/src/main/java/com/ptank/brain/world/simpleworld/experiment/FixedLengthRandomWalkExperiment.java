package com.ptank.brain.world.simpleworld.experiment;

import java.util.Random;

import com.ptank.brain.core.Experiment;
import com.ptank.brain.world.simpleworld.SimpleWorldBrain;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;
import com.ptank.util.random.RandomSingleton;

/**
 * In this experiment we place the mouse somewhere at random in the world
 * and have him think for 1000 turns.
 */
public class FixedLengthRandomWalkExperiment implements Experiment {

	private SimpleWorldBrain<?> brain;
	private int numTurns = 1000;
	private World world;
	private Random random = RandomSingleton.getInstance();
	
	public FixedLengthRandomWalkExperiment(SimpleWorldBrain<?> brain, World world) {
		this.brain = brain;
		this.world = world;
	}
	
	private Tile getRandomStartingLocation() {
		int x = random.nextInt(world.getWidth()-2)+1;
		int y = random.nextInt(world.getHeight()-2)+1;
		return world.getTile(x, y);
	}
	
	private void placeMouse() {
		Tile startingLocation = getRandomStartingLocation();
		brain.getBody().place(startingLocation);
	}
	
	@Override
	public void runExperiment() {
		placeMouse();
		for(int i = 0; i < numTurns; i++) {
			brain.think();
		}
	}
	
}
