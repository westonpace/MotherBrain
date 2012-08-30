package com.ptank.brain.world.simpleworld.mental.mouse;

import com.ptank.brain.world.simpleworld.physical.Mouse;

public class MouseFactory {

	public MouseBrain buildMouseBrain() {
		Mouse mouse = new Mouse();
		MouseVisualCortex mouseEyes = new MouseVisualCortex(mouse);
		MouseCerebellum motorControl = new MouseCerebellum(mouse);
		MouseBrain brain = new MouseBrain(mouse,mouseEyes,motorControl);
		return brain;
	}
	
}
