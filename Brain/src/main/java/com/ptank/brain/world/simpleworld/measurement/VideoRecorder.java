package com.ptank.brain.world.simpleworld.measurement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ptank.brain.world.simpleworld.Eyes;

public class VideoRecorder extends Eyes {

	private VisualInput[][] frames;
	private int index = 0;
	
	protected VideoRecorder() {
		
	}
	
	public VideoRecorder(VisualInput[][] frames) {
		this.frames = frames;
	}
	
	protected void setFrames(VisualInput[][] frames) {
		this.frames = frames;
	}
	
	public boolean isDone() {
		return index >= frames.length;
	}
	
	public void reset() {
		index = 0;
	}
	
	@Override
	public List<VisualInput> getCurrentView() {
		List<VisualInput> result = new ArrayList<VisualInput>(Arrays.asList(frames[index]));
		index++;
		return result;
	}

}
