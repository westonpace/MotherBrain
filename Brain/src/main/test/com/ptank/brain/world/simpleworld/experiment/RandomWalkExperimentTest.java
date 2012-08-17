package com.ptank.brain.world.simpleworld.experiment;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.test.DataBuilder;
import com.ptank.util.gridworld.World;

public class RandomWalkExperimentTest {

	@Before
	public void hookupVisualVM() throws IOException {
		System.in.read();
	}
	
	@Test
	public void test() {
		
		DataBuilder db = new DataBuilder();
		
		MouseBrain worstEver = null;
		
		for(int i = 0; i < 50; i++) {
			MouseBrain mouseBrain = db.buildRandomMouseBrain();
			World world = new World(100,100);
			FixedLengthRandomWalkExperiment test = new FixedLengthRandomWalkExperiment(mouseBrain,world);
			test.runExperiment();
			if(worstEver == null || mouseBrain.getBody().getScore() < worstEver.getBody().getScore()) {
				worstEver = mouseBrain;
			}
			System.out.println("Test: " + i + " -- " + mouseBrain.getBody().getScore());
		}
		
		System.out.println("Worst Ever Brain: " + worstEver);
	}
	
}
