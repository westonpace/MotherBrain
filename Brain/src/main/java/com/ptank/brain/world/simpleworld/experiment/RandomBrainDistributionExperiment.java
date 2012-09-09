package com.ptank.brain.world.simpleworld.experiment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;
import com.ptank.brain.world.simpleworld.measurement.PropensityForActionMeasurement;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.test.DataBuilder;

public class RandomBrainDistributionExperiment {

	private static final boolean useBias = false;
	private static final int numBrains = 100000;
	private static final double noise = 0.4;
	
	public static void main(String [] args) {
		DataBuilder db = new DataBuilder();
		
		PropensityForActionMeasurement measurement = new PropensityForActionMeasurement();
		
		HashMap<Double,Integer> histogram = new HashMap<Double,Integer>();
		int total = 0;
		
		for(int i = 0; i < numBrains; i++) {
			MouseBrain mouseBrain = db.buildRandomMouseBrain(useBias,noise);
			double result = measurement.calculate(mouseBrain);
			if(result == 1) {
			}
			if(!histogram.containsKey(result)) {
				histogram.put(result, 0);
			}
			total++;
			histogram.put(result,histogram.get(result)+1);
		}
		
		List<Double> sortedKeys = new ArrayList<Double>(histogram.keySet());
		Collections.sort(sortedKeys);
		
		double arr[] = new double[sortedKeys.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = histogram.get(sortedKeys.get(i)) / ((double)total);
		}

		double min = Double.NEGATIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for(double item : arr) {
			if(min == Double.NEGATIVE_INFINITY || item < min) {
				min = item;
			}
			if(max == Double.NEGATIVE_INFINITY || item > max) {
				max = item;
			}
		}
		
		min = min * 0.9;
		max = max * 1.1;
		
		ArrayList<String> labels = new ArrayList<String>();
		DecimalFormat format = new DecimalFormat(".00");
		for(Double value : sortedKeys) {
			labels.add(format.format(value));
		}
		
		Data plotData = DataUtil.scaleWithinRange(min,max,arr);
		BarChartPlot plot = Plots.newBarChartPlot(plotData);
		plot.setColor(Color.newColor("009999"));
		BarChart graph = GCharts.newBarChart(plot);
		graph.setBarWidth(15);
		graph.setBackgroundFill(Fills.newSolidFill(Color.newColor("FFB273")));
		graph.setMargins(40, 0, 0, 0);
		graph.addXAxisLabels(AxisLabelsFactory.newAxisLabels(labels));
		graph.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(min, max,(max-min)/10.0));
		graph.setSize(300, 300);
		if(useBias) {
			graph.setTitle("PFA Distribution (with bias)");
		} else {
			graph.setTitle("PFA Distribution (no bias)");
		}
		System.out.println(graph.toURLString());
	}	
}
