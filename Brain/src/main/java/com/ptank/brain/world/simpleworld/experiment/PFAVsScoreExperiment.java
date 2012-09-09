package com.ptank.brain.world.simpleworld.experiment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.Markers;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;
import com.ptank.brain.world.simpleworld.SimpleWorld;
import com.ptank.brain.world.simpleworld.measurement.PropensityForActionMeasurement;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.test.DataBuilder;

public class PFAVsScoreExperiment {

	private static int worldWidth = 20;
	private static int worldHeight = 20;
	private static int turnsPerTrial = 25;
	private static int trialsPerBrain = 25;
	private static int numBrains = 1000;
	private static boolean useBias = true;
	private static double noise = 0.4;
	
	private static class ExperimentRecord {
		private double propensityForAction;
		private boolean wasSuicidal;
		
		public ExperimentRecord(double propensityForAction, boolean wasSuicidal) {
			this.propensityForAction = propensityForAction;
			this.wasSuicidal = wasSuicidal;
		}
	}
	
	
	public static ExperimentRecord testBrain(MouseBrain brain, SimpleWorld world) {
		world.setMouse(brain);
		double pfa = new PropensityForActionMeasurement().calculate(brain);
		for(int i = 0; i < trialsPerBrain; i++) {
			FixedLengthRandomWalkExperiment experiment = new FixedLengthRandomWalkExperiment(world,turnsPerTrial);
			experiment.runExperiment();
		}
		boolean wasSuicidal = (brain.getBody().getScore() < 0);
		return new ExperimentRecord(pfa, wasSuicidal);
	}
	
	public static ExperimentRecord buildAndTestRandomBrain() {
		MouseBrain brain = new DataBuilder().buildRandomMouseBrain(useBias,noise);
		SimpleWorld world = new SimpleWorld(worldWidth, worldHeight);
		return testBrain(brain,world);
	}
	
	private static class RecordAnalysis {
		private int numSuccesses = 0;
		private int numFailures = 0;
		
		private void record(boolean result) {
			if(result) {
				numSuccesses++;
			} else {
				numFailures++;
			}
		}
		
		private double score() {
			return ((double)numSuccesses) / (numSuccesses + numFailures);
		}
	}
	
	public static void analyzeResult(List<ExperimentRecord> results) {
		HashMap<Double,RecordAnalysis> analysisMap = new HashMap<Double,RecordAnalysis>();
		for(ExperimentRecord record : results) {
			if(!analysisMap.containsKey(record.propensityForAction)) {
				analysisMap.put(record.propensityForAction, new RecordAnalysis());
			}
			analysisMap.get(record.propensityForAction).record(!record.wasSuicidal);
		}
		ArrayList<Double> sortedPfas = new ArrayList<Double>(analysisMap.keySet());
		Collections.sort(sortedPfas);
		
		double [] data = new double[sortedPfas.size()];
		double [] xVals = new double[sortedPfas.size()];
		for(int i = 0; i < data.length; i++) {
			xVals[i] = sortedPfas.get(i);
			data[i] = analysisMap.get(sortedPfas.get(i)).score();
		}
		plot(xVals,data);
	}
	
	public static void plot(double [] xValues, double [] yValues) {
		Data data = DataUtil.scaleWithinRange(0, 1, yValues);
		Line line = Plots.newLine(data);
		line.setColor(Color.newColor("009999"));
		line.setLineStyle(LineStyle.THICK_LINE);
		line.addMarkers(Markers.newShapeMarker(Shape.DIAMOND, Color.newColor("009999"), 15));
		LineChart chart = GCharts.newLineChart(line);
		chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("FFB273")));
		chart.setSize(300, 300);
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0.0, 1.0, 0.1));
		chart.addXAxisLabels(AxisLabelsFactory.newNumericAxisLabels(xValues));
		chart.setMargins(20, 20, 20, 20);
		chart.setTitle("% Survivors Vs. PFA");
		System.out.println(chart.toURLString());
	}
	
	public static void main(String [] args) {
		List<ExperimentRecord> records = new ArrayList<ExperimentRecord>();
		HashMap<Double,Integer> histogram = new HashMap<Double,Integer>();
		for(int i = 0; i < numBrains; i++) {
			ExperimentRecord experimentRecord = buildAndTestRandomBrain();
			records.add(experimentRecord);
			if(!experimentRecord.wasSuicidal) {
				if(!histogram.containsKey(experimentRecord.propensityForAction)) {
					histogram.put(experimentRecord.propensityForAction,0);
				}
				int newVal = histogram.get(experimentRecord.propensityForAction)+1;
				histogram.put(experimentRecord.propensityForAction, newVal);
			}
		}
				
		analyzeResult(records);
		analyzeHistogram(histogram);
	}
	
	private static void analyzeHistogram(Map<Double,Integer> histogram) {
		List<Double> sortedKeys = new ArrayList<Double>(histogram.keySet());
		Collections.sort(sortedKeys);
		List<Double> arr = new ArrayList<Double>(sortedKeys.size());
		for(Double key : sortedKeys) {
			arr.add(Double.valueOf(histogram.get(key)));
		}
		
		plotHistogram(sortedKeys,arr);
	}
	
	private static void plotHistogram(List<Double> xValues, List<Double> yValues) {
		
		double total = 0;
		for(Double yValue : yValues) {
			total += yValue;
		}
		
		for(int i = 0; i < yValues.size(); i++) {
			yValues.set(i, yValues.get(i)/total);
		}
		
		Double min = Collections.min(yValues);
		Double max = Collections.max(yValues);
		min = min * 0.9;
		max = max * 1.1;
		
		List<String> xLabels = new ArrayList<String>();
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		for(Double xValue : xValues) {
			xLabels.add(decimalFormat.format(xValue));
		}
		
		Data data = DataUtil.scale(yValues);
		BarChartPlot plot = Plots.newBarChartPlot(data);
		plot.setColor(Color.newColor("009999"));
		BarChart chart = GCharts.newBarChart(plot);
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(xLabels));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(min, max));
		chart.setBarWidth(15);
		chart.setSize(300, 300);
		chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("FFB273")));
		chart.setMargins(40, 0, 0, 0);
		chart.setTitle("PFA Distribution (Successes only)");
		System.out.println(chart.toURLString());

	}
	
}
