package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import org.apache.log4j.Logger;

import com.ptank.brain.world.simpleworld.MotorControl;
import com.ptank.brain.world.simpleworld.MotorControl.Action;
import com.ptank.brain.world.simpleworld.physical.Mouse;

/**
 * Translates a set of neurons into mouse movement.
 */
public class MouseCerebellum implements NeuralOutput {

	private static Logger logger = Logger.getLogger(MouseCerebellum.class);
	
	private MotorControl motorControl;
	
	public MouseCerebellum(Mouse mouse) {
		this.motorControl = new MouseMotorControl(mouse);
	}
	
	public MouseCerebellum(MotorControl motorControl) {
		this.motorControl = motorControl;
	}
	
	public void setMotorControl(MotorControl motorControl) {
		this.motorControl = motorControl;
	}
	
	public MotorControl getMotorControl() {
		return motorControl;
	}
	
	public String getNameOfIndex(int index) {
		return Action.values()[index].toString();
	}
	
	@Override
	public void setOutput(List<Double> output) {
		logger.debug("Neural input arrived at motor control: " + output);
		int maxIndex = -1;
		double maxValue = Double.NaN;
		
		for(int i = 0; i < output.size(); i++) {
			double value = output.get(i);
			if(Double.isNaN(maxValue) || value > maxValue) {
				maxValue = value;
				maxIndex = i;
			}
		}
		
		//If all the values are negative then don't move
		if(maxValue > 0) {
			Action move = Action.values()[maxIndex];
			motorControl.doAction(move);
		} else {
			logger.debug("Standing Still");
		}
	}
		
	public int getNeuralIndex(Action move) {
		return move.ordinal();
	}
	
	public int size() {
		return Action.values().length;
	}

}
