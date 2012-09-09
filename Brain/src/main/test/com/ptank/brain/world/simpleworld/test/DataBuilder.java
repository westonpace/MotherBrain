package com.ptank.brain.world.simpleworld.test;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseVisualCortex;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseCerebellum;
import com.ptank.brain.world.simpleworld.mental.mouse.NeuralNoise;
import com.ptank.brain.world.simpleworld.physical.Mouse;

public class DataBuilder {

	public Mouse buildMouse() {
		return new Mouse();
	}
	
	public MouseVisualCortex buildMouseEyes(Mouse mouse) {
		return new MouseVisualCortex(buildMouse());
	}
	
	public MouseCerebellum buildMouseMotorControl() {
		return new MouseCerebellum(buildMouse());
	}
	
	public MouseBrain buildRandomMouseBrain(boolean useBias, double noiseMagnitude) {
		Mouse mouse = buildMouse();
		MouseVisualCortex eyes = new MouseVisualCortex(mouse);
		MouseCerebellum motorControl = new MouseCerebellum(mouse);
		MouseBrain result = new MouseBrain(mouse,eyes,motorControl,useBias,noiseMagnitude);
		return result;
	}
		
	public MouseBrain buildRunForwardsAlwaysMouseBrain() {
		return null;
	}
	
	public MouseBrain buildDontHitWallsAlwaysTurnRightBrain() {
		return null;
	}
	
}
