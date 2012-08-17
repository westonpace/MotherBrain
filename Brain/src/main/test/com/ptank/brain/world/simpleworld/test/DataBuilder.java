package com.ptank.brain.world.simpleworld.test;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseEyes;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseMotorControl;
import com.ptank.brain.world.simpleworld.physical.Mouse;

public class DataBuilder {

	public Mouse buildMouse() {
		return new Mouse();
	}
	
	public MouseEyes buildMouseEyes(Mouse mouse) {
		return new MouseEyes(buildMouse());
	}
	
	public MouseMotorControl buildMouseMotorControl() {
		return new MouseMotorControl(buildMouse());
	}
	
	public MouseBrain buildRandomMouseBrain() {
		Mouse mouse = buildMouse();
		MouseEyes eyes = new MouseEyes(mouse);
		MouseMotorControl motorControl = new MouseMotorControl(mouse);
		MouseBrain result = new MouseBrain(mouse,eyes,motorControl);
		return result;
	}
		
	public MouseBrain buildRunForwardsAlwaysMouseBrain() {
		return null;
	}
	
	public MouseBrain buildDontHitWallsAlwaysTurnRightBrain() {
		return null;
	}
	
}
