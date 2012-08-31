package com.ptank.brain.world.simpleworld;

import com.ptank.brain.core.Brain;
import com.ptank.util.gridworld.Unit;

public interface SimpleWorldBrain<T extends Unit> extends Brain {

	public T getBody();
	
	public Eyes getEyes();
	
	public void setEyes(Eyes eyes);
	
	public MotorControl getMotorControl();
	
	public void setMotorControl(MotorControl motorControl);
	
}
