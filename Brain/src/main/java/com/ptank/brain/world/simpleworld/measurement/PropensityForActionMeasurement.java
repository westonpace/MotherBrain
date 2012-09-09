package com.ptank.brain.world.simpleworld.measurement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.ptank.brain.world.simpleworld.Eyes;
import com.ptank.brain.world.simpleworld.MotorControl;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;

public class PropensityForActionMeasurement {
	
	private VideoRecorder videoRecorder;
	private ActionRecorder actionRecorder = new ActionRecorder();
	
	public PropensityForActionMeasurement() {
		loadVideoRecorder();
	}
	
	private void loadVideoRecorder() {
		try {
			InputStream videoFile = PropensityForActionMeasurement.class.getResource("PropensityForActionVideo.vid").openStream();
			videoRecorder = new FileBasedVideoRecorder(new InputStreamReader(videoFile));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public double calculate(MouseBrain brain) {
		Eyes oldEyes = brain.getEyes();
		MotorControl oldMotorControl = brain.getMotorControl();
		brain.setEyes(videoRecorder);
		brain.setMotorControl(actionRecorder);
		int numTrials = 0;
		while(!videoRecorder.isDone()) {
			brain.think();
			numTrials++;
		}
		brain.setEyes(oldEyes);
		brain.setMotorControl(oldMotorControl);
		double result = actionRecorder.getNumActionsMade() / (double) numTrials;
		videoRecorder.reset();
		actionRecorder.reset();
		return result;
	}

	private static class ActionRecorder implements MotorControl {

		int numActionsMade = 0;
		
		@Override
		public void doAction(Action action) {
			if(action == Action.MOVE_FORWARD || action == Action.MOVE_BACKWARD) {
				numActionsMade++;
			}
		}
		
		public int getNumActionsMade() {
			return numActionsMade;
		}
		
		public void reset() {
			numActionsMade = 0;
		}
		
	}
	
}
