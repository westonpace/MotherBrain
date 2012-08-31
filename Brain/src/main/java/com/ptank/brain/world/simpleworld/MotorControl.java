package com.ptank.brain.world.simpleworld;

public interface MotorControl {

	public enum Action {
		ROTATE_LEFT,
		ROTATE_RIGHT,
		MOVE_FORWARD,
		MOVE_BACKWARD;
	}

	public void doAction(Action action);
	
}
