package com.ptank.brain.world.simpleworld.mental.mouse;

import com.ptank.brain.world.simpleworld.physical.Mouse;

public class MouseFactory {

	public MouseBrain buildMouseBrain() {
		Mouse mouse = new Mouse();
		MouseEyes mouseEyes = new MouseEyes(mouse);
		MouseMotorControl motorControl = new MouseMotorControl(mouse);
		MouseBrain brain = new MouseBrain(mouse,mouseEyes,motorControl);
		return brain;
	}
	
}
