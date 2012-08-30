package com.ptank.brain.world.simpleworld.experiment;

import org.junit.Test;

import com.ptank.brain.world.simpleworld.SimpleWorld;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.test.BaseTest;
import com.ptank.brain.world.simpleworld.test.BaseTest.ExternalDebug;
import com.ptank.brain.world.simpleworld.test.DataBuilder;

public class RandomWalkExperimentTest extends BaseTest {
	
	@Test
	public void test() {
		
		DataBuilder db = new DataBuilder();
		
		MouseBrain worstEver = null;
		
		int NUM_EXPERIMENTS = 1000;
		
		SimpleWorld world = new SimpleWorld(100,100);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < NUM_EXPERIMENTS; i++) {
			MouseBrain mouseBrain = db.buildRandomMouseBrain();
			world.setMouse(mouseBrain);
			world.start();
			FixedLengthRandomWalkExperiment test = new FixedLengthRandomWalkExperiment(world);
			test.runExperiment();
			if(worstEver == null || mouseBrain.getBody().getScore() < worstEver.getBody().getScore()) {
				worstEver = mouseBrain;
			}
			System.out.println("Test: " + i + " -- " + mouseBrain.getBody().getScore());
			world.clear();
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		double timePerExperiment = totalTime / NUM_EXPERIMENTS;
		System.out.println(timePerExperiment + " ms per experiment");
		
		System.out.println("Worst Ever Brain: " + worstEver);
	}
	
}
