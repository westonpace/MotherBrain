package com.ptank.util.encog;

import java.util.ArrayList;
import java.util.List;

import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;

public class EncogUtils {

	/**
	 * Utility method to switch from List<Double> to MLData
	 */
	public static BasicNeuralData neuralDataFromListOfDouble(List<Double> doubleList) {
		BasicNeuralData result = new BasicNeuralData(doubleList.size());
		for(int i = 0; i < doubleList.size(); i++) {
			result.setData(i, doubleList.get(i));
		}
		return result;
	}
	
	/**
	 * Utility method to switch from MLData to List<Double>
	 */
	public static List<Double> doubleListFromNeuralOutput(MLData neuralOutput) {
		List<Double> result = new ArrayList<Double>(neuralOutput.size());
		for(double value : neuralOutput.getData()) {
			result.add(value);
		}
		return result;
	}

	
}
