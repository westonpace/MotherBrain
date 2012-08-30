package com.ptank.brain.world.simpleworld.mental;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseVisualCortex;
import com.ptank.brain.world.simpleworld.mental.mouse.MouseVisualCortex.TileIterator;
import com.ptank.brain.world.simpleworld.physical.Cat;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;
import com.ptank.util.gridworld.World.Direction;

public class MouseEyesTest {
	
	private double sumArray(List<Double> array) {
		double sum = 0;
		for(Double arrayItem : array) {
			sum += arrayItem;
		}
		return sum;
	}
	
	private boolean compareDoubles(double one, double two) {
		return Math.abs(one - two) <= 0.005;
	}
	
	@Test
	public void testMouseEyes() {
		
		World world = new World(10,10);
		Mouse mouse = new Mouse();
		Tile mouseTile = world.getTile(2,2);
		
		MouseVisualCortex mouseEyes = new MouseVisualCortex(mouse);
		mouse.place(mouseTile);
		
		Assert.assertTrue(compareDoubles(sumArray(mouseEyes.getNextInput()),5.0));

		mouse.move(Direction.North);

		Assert.assertTrue(compareDoubles(sumArray(mouseEyes.getNextInput()),8.0));
		
		mouse.move(Direction.South);
		
		Cat cat = new Cat();
		cat.place(world.getTile(2, 1));
		
		Assert.assertTrue(compareDoubles(sumArray(mouseEyes.getNextInput()),6.0));
	}
	
	@Test
	public void testTileIterator() {
		World world = new World(10,10);
		Tile tile = world.getTile(2, 3);
		List<Tile> travelledTiles = new ArrayList<Tile>();
		TileIterator tileIterator = new TileIterator(Direction.East, tile);
		
		while(tileIterator.hasNext()) {
			travelledTiles.add(tileIterator.next());
		}
		
		Dimension[] expectedCoords = new Dimension[] {new Dimension(3,2),
				                                      new Dimension(3,3),
				                                      new Dimension(3,4),
				                                      new Dimension(4,1),
				                                      new Dimension(4,2),
				                                      new Dimension(4,3),
				                                      new Dimension(4,4),
				                                      new Dimension(4,5)};
		
		for(Dimension dimension : expectedCoords) {
			boolean dimensionFound = false;
			for(Tile travelledTile : travelledTiles) {
				if(travelledTile.getX() == dimension.getWidth() && travelledTile.getY() == dimension.getHeight()) {
					dimensionFound = true;
					break;
				}
			}
			Assert.assertTrue("Did not travel the expected tile: " + dimension,dimensionFound);
		}
	}
	
}
