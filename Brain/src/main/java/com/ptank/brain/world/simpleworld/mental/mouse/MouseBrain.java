package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;

import com.ptank.util.encog.EncogUtils;

public class MouseBrain {

	private NeuralInput eyes;
	private NeuralOutput motorControl;
	private BasicNetwork prefrontalCortex;

	public MouseBrain() {

	}

	public void setEyes(NeuralInput eyes) {
		this.eyes = eyes;
	}

	public void setMotorControl(NeuralOutput motorControl) {
		this.motorControl = motorControl;
	}

	public void setPrefrontalCortex(BasicNetwork prefrontalCortex) {
		this.prefrontalCortex = prefrontalCortex;
	}

	/**
	 * Runs through one tick of the brain. Takes input from neural inputs, uses
	 * that input to compute a new output. Puts that output into neural outputs.
	 */
	public void think() {
		List<Double> rawNeuralInput = eyes.getNextInput();
		BasicNeuralData neuralInput = EncogUtils.neuralDataFromListOfDouble(rawNeuralInput);
		MLData neuralOutput = prefrontalCortex.compute(neuralInput);
		List<Double> rawNeuralOutput = EncogUtils.doubleListFromNeuralOutput(neuralOutput);
		motorControl.setOutput(rawNeuralOutput);
	}

}
