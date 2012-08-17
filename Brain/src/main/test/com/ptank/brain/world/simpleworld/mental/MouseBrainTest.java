package com.ptank.brain.world.simpleworld.mental;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Test;

import com.ptank.brain.neural.core.WeightSource;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseEyes;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseMotorControl;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;

public class MouseBrainTest {

	@Test
	public void testMouseBrain() {
		
		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		
		World world = new World(10,10);
		Mouse mouse = new Mouse();
		Tile mouseTile = world.getTile(2,2);
		
		MouseEyes mouseEyes = new MouseEyes(mouse);
		MouseMotorControl motorControl = new MouseMotorControl(mouse);
		MouseBrain mouseBrain = new MouseBrain(mouse, mouseEyes, motorControl);
		mouse.place(mouseTile);

		for(int i = 0; i < 10; i++) {
			mouseBrain.think();
			System.out.println(world);
		}
	}
	
}
