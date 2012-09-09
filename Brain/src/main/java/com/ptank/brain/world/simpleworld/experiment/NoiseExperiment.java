package com.ptank.brain.world.simpleworld.experiment;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LegendPosition;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.Markers;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;
import com.ptank.brain.world.simpleworld.SimpleWorld;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.brain.world.simpleworld.test.DataBuilder;

public class NoiseExperiment {

	private static int worldWidth = 20;
	private static int worldHeight = 20;
	private static int turnsPerTrial = 25;
	private static int trialsPerBrain = 25;
	private static int numBrains = 1000;
	private static boolean useBias = false;
	private static double minNoiseMagnitude = 0;
	private static double maxNoiseMagnitude = 1.0;
	private static double step = 0.1;
	
	public static boolean testBrain(MouseBrain brain, SimpleWorld world) {
		world.setMouse(brain);
		for(int i = 0; i < trialsPerBrain; i++) {
			FixedLengthRandomWalkExperiment experiment = new FixedLengthRandomWalkExperiment(world,turnsPerTrial);
			experiment.runExperiment();
		}
		boolean wasSuicidal = (brain.getBody().getScore() < 0);
		return !wasSuicidal;
	}
	
	public static boolean buildAndTestRandomBrain(double noise) {
		MouseBrain brain = new DataBuilder().buildRandomMouseBrain(useBias,noise);
		SimpleWorld world = new SimpleWorld(worldWidth, worldHeight);
		return testBrain(brain,world);
	}
		
	public static void plot(List<Double> xValues, List<Double> yValues, List<Double> yValuesBias) {
		Data data = DataUtil.scaleWithinRange(0, 1, yValues);
		Line line = Plots.newLine(data);
		line.setLegend("No Bias");
		line.setColor(Color.newColor("009999"));
		line.setLineStyle(LineStyle.THICK_LINE);
		line.addMarkers(Markers.newShapeMarker(Shape.DIAMOND, Color.newColor("009999"), 15));
		
		Data dataWithBias = DataUtil.scaleWithinRange(0,1,yValuesBias);
		Line lineWithBias = Plots.newLine(dataWithBias);
		lineWithBias.setLegend("With Bias");
		lineWithBias.setColor(Color.newColor("FF7400"));
		lineWithBias.setLineStyle(LineStyle.THICK_LINE);
		lineWithBias.addMarkers(Markers.newShapeMarker(Shape.DIAMOND, Color.newColor("FF7400"), 15));
		
		LineChart chart = GCharts.newLineChart(line,lineWithBias);
		chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("FFB273")));
		chart.setSize(300, 300);
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0.0, 1.0, 0.1));
		chart.addXAxisLabels(AxisLabelsFactory.newNumericAxisLabels(xValues));
		chart.setMargins(20, 20, 20, 20);
		chart.setTitle("% Successes Vs. Noise Magnitude");
		chart.setLegendPosition(LegendPosition.RIGHT);
		System.out.println(chart.toURLString());
	}
	
	public static void main(String [] args) {
		List<Double> noises = new ArrayList<Double>();
		
		useBias = false;
		
		List<Double> percentagesNoBias = new ArrayList<Double>();
		for(double noise = minNoiseMagnitude; noise <= maxNoiseMagnitude; noise += step) {
			System.out.println("noise="+noise);
			int successes = 0;
			for(int i = 0; i < numBrains; i++) {
				if(buildAndTestRandomBrain(noise)) {
					successes++;
				}
			}
			noises.add(noise);
			percentagesNoBias.add(((double)successes)/numBrains);
		}

		useBias = true;
		
		List<Double> percentagesWithBias = new ArrayList<Double>();
		for(double noise = minNoiseMagnitude; noise <= maxNoiseMagnitude; noise += step) {
			System.out.println("noise="+noise);
			int successes = 0;
			for(int i = 0; i < numBrains; i++) {
				if(buildAndTestRandomBrain(noise)) {
					successes++;
				}
			}
			noises.add(noise);
			percentagesWithBias.add(((double)successes)/numBrains);
		}

		plot(noises,percentagesNoBias,percentagesWithBias);
	}
	
}
