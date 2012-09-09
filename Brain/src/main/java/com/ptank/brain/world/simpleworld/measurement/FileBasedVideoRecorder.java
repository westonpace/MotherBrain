package com.ptank.brain.world.simpleworld.measurement;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import com.ptank.util.gridworld.World.Direction;

public class FileBasedVideoRecorder extends VideoRecorder {

	public FileBasedVideoRecorder(Reader inputStream) {
		super();
		setFrames(loadVideoFromFile(inputStream));
	}
	
	private VisualInput[][] loadVideoFromFile(Reader input) {
		Scanner scanner = new Scanner(input);
		ArrayList<VisualInput[]> result = new ArrayList<VisualInput[]>();
		while(scanner.hasNext()) {
			result.add(loadNextInput(scanner));
		}
		return result.toArray(new VisualInput[][] {});
	}
	
	private VisualInput[] loadNextInput(Scanner scanner) {
		VisualInput[] result = new VisualInput[size()];
		for(int i = 0; i < size(); i++) {
			if(!scanner.hasNext()) {
				throw new RuntimeException("Couldn't load video because a frame was incomplete");
			}
			String inputLine = scanner.nextLine();
			if(inputLine.trim().equals("")) {
				i--;
				continue;
			}
			readInputLine(result,inputLine);
		}
		return result;
	}
	
	private void readInputLine(VisualInput[] result, String line) {
		String [] directionInputArray = line.split(":");
		String directions = directionInputArray[0];
		VisualInput visualInput = null;
		if(directionInputArray.length > 1) {
			String input = directionInputArray[1];
			visualInput = inputFromString(input);
		}
		Direction [] path = pathFromString(directions);
		int indexIntoArray = indexFromPath(path);
		result[indexIntoArray] = visualInput;
	}
	
	private VisualInput inputFromString(String inputString) {
		return VisualInput.valueOf(inputString);
	}
	
	private Direction [] pathFromString(String directionString) {
		String [] directions = directionString.split("\\.");
		Direction [] result = new Direction[directions.length];
		for(int i = 0; i < directions.length; i++) {
			String dir = directions[i];
			Direction correctDir = null;
			for(Direction d : Direction.values()) {
				if(d.getShortString().toLowerCase().equals(dir.toLowerCase())) {
					correctDir = d;
					break;
				}
			}
			if(correctDir == null) {
				throw new RuntimeException("Did not understand direction: " + dir);
			}
			result[i] = correctDir;
		}
		return result;
	}
	
}
