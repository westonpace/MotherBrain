package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

import com.ptank.brain.neural.core.RandomWeightSource;
import com.ptank.brain.neural.core.WeightSource;
import com.ptank.brain.world.simpleworld.SimpleWorldBrain;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseEyes.VisualInput;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseMotorControl.MouseMove;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.encog.EncogUtils;
import com.ptank.util.gridworld.Unit;
import com.ptank.util.gridworld.World.Direction;

public class MouseBrain implements SimpleWorldBrain<Unit> {

	private Mouse body;
	private MouseEyes eyes;
	private MouseMotorControl motorControl;
	private BasicNetwork prefrontalCortex;
	private WeightSource weightSource = new RandomWeightSource();
	
	public MouseBrain(Mouse body, MouseEyes eyes, MouseMotorControl motorControl) {
		this.body = body;
		this.eyes = eyes;
		this.motorControl = motorControl;
		initialize();
	}

	private void initializeStructure() {
		prefrontalCortex = new BasicNetwork();
		prefrontalCortex.addLayer(new BasicLayer(null,true,eyes.size()));
		prefrontalCortex.addLayer(new BasicLayer(new ActivationSigmoid(),false,motorControl.size()));
		prefrontalCortex.getStructure().finalizeStructure();
		prefrontalCortex.reset();
	}
	
	private void initializeWeights() {
		int numWeightsNeeded = eyes.size() * motorControl.size();
		double [] weights = weightSource.getWeights(numWeightsNeeded);
		for(int i = 0; i < eyes.size(); i++) {
			for(int j = 0; j < motorControl.size(); j++) {
				prefrontalCortex.setWeight(0, i, j, weights[i*motorControl.size()+j]);
			}
		}
	}
	
	private void initialize() {
		initializeStructure();
		initializeWeights();
	}

	public void setWeightSource(WeightSource weightSource) {
		this.weightSource = weightSource;
		initializeWeights();
	}
	
	public void hardCodeRule(VisualInput input, Direction [] path, MouseMove action) {
		int eyeIndex = eyes.getNeuralIndex(input, path);
		int motorIndex = motorControl.getNeuralIndex(action);
		prefrontalCortex.setWeight(0, eyeIndex, motorIndex, 1.0);
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

	public Mouse getBody() {
		return body;
	}
	
	public String toString() {
		return prefrontalCortex.dumpWeights();
	}
	
}
