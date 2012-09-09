package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

import com.ptank.brain.neural.core.RandomWeightSource;
import com.ptank.brain.neural.core.WeightSource;
import com.ptank.brain.world.simpleworld.Eyes;
import com.ptank.brain.world.simpleworld.Eyes.VisualInput;
import com.ptank.brain.world.simpleworld.MotorControl;
import com.ptank.brain.world.simpleworld.MotorControl.Action;
import com.ptank.brain.world.simpleworld.SimpleWorldBrain;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.encog.EncogUtils;
import com.ptank.util.gridworld.Unit;
import com.ptank.util.gridworld.World.Direction;

public class MouseBrain implements SimpleWorldBrain<Unit> {

	private boolean useBias;
	private Mouse body;
	private MouseVisualCortex visualCortex;
	private NeuralInput noisyEyes;
	private MouseCerebellum cerebellum;
	private BasicNetwork prefrontalCortex;
	private WeightSource weightSource = new RandomWeightSource();
	
	public MouseBrain(Mouse body, MouseVisualCortex visualCortex, MouseCerebellum cerebellum) {
		this(body,visualCortex,cerebellum,true);
	}

	public MouseBrain(Mouse body, MouseVisualCortex visualCortex, MouseCerebellum cerebellum, boolean useBias) { 
		this(body,visualCortex,cerebellum,useBias,0.0);
	}

	public MouseBrain(Mouse body, MouseVisualCortex visualCortex, MouseCerebellum cerebellum, boolean useBias, double noiseMagnitude) {
		this.body = body;
		this.visualCortex = visualCortex;
		this.cerebellum = cerebellum;
		this.useBias = useBias;
		if(noiseMagnitude == 0) {
			noisyEyes = visualCortex;
		} else {
			noisyEyes = new NeuralNoise(visualCortex, 1-noiseMagnitude, 1+noiseMagnitude);
		}
		initialize();
	}
	
	private void initializeStructure() {
		prefrontalCortex = new BasicNetwork();
		prefrontalCortex.addLayer(new BasicLayer(null,true,visualCortex.size()));
		prefrontalCortex.addLayer(new BasicLayer(new ActivationSigmoid(),false,cerebellum.size()));
		prefrontalCortex.getStructure().finalizeStructure();
		prefrontalCortex.reset();
	}
	
	private int numBiasWeightsNeeded() {
		return useBias ? 1 : 0;
	}
	
	private void initializeWeights() {
		int numWeightsNeeded = visualCortex.size() * cerebellum.size() + numBiasWeightsNeeded();
		double [] weights = weightSource.getWeights(numWeightsNeeded);
		for(int i = 0; i < visualCortex.size(); i++) {
			for(int j = 0; j < cerebellum.size(); j++) {
				prefrontalCortex.setWeight(0, i, j, weights[i*cerebellum.size()+j]);
			}
		}
		if(useBias) {
			prefrontalCortex.setBiasActivation(weights[weights.length-1]);
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
	
	public void hardCodeRule(VisualInput input, Direction [] path, Action action) {
		int eyeIndex = visualCortex.getNeuralIndex(input, path);
		int motorIndex = cerebellum.getNeuralIndex(action);
		prefrontalCortex.setWeight(0, eyeIndex, motorIndex, 1.0);
	}
	
	/**
	 * Runs through one tick of the brain. Takes input from neural inputs, uses
	 * that input to compute a new output. Puts that output into neural outputs.
	 */
	public void think() {
		List<Double> rawNeuralInput = noisyEyes.getNextInput();
		BasicNeuralData neuralInput = EncogUtils.neuralDataFromListOfDouble(rawNeuralInput);
		MLData neuralOutput = prefrontalCortex.compute(neuralInput);
		List<Double> rawNeuralOutput = EncogUtils.doubleListFromNeuralOutput(neuralOutput);
		cerebellum.setOutput(rawNeuralOutput);
	}

	public Mouse getBody() {
		return body;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(int outputIndex = 0; outputIndex < prefrontalCortex.getOutputCount(); outputIndex++) {
			result.append(cerebellum.getNameOfIndex(outputIndex));
			result.append(":\n");
			for(int inputIndex = 0; inputIndex < prefrontalCortex.getInputCount(); inputIndex++) {
				result.append("\t");
				result.append(prefrontalCortex.getWeight(0, inputIndex, outputIndex));
				result.append(" - ");
				result.append(visualCortex.getNameOfIndex(inputIndex));
				result.append("\n");
			}
		}
		return result.toString();
	}

	public void face(Direction direction) {
		body.face(direction);
	}
	
	@Override
	public Eyes getEyes() {
		return visualCortex.getEyes();
	}

	@Override
	public void setEyes(Eyes eyes) {
		visualCortex.setEyes(eyes);
	}

	@Override
	public MotorControl getMotorControl() {
		return cerebellum.getMotorControl();
	}

	@Override
	public void setMotorControl(MotorControl motorControl) {
		cerebellum.setMotorControl(motorControl);
	}
	
}
