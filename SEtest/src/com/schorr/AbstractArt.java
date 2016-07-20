package com.schorr;

import java.awt.Graphics;
import java.awt.*;
import java.awt.Canvas;
import java.awt.Color;

import java.util.Random;

public class AbstractArt {
	
	public static Color getRandomColor() {
		Random numGen = new Random();	 
	      return new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
	}
 
	public static void main(String[] args)  {
		Canvas canvas = new Canvas();
		Graphics g = canvas.getGraphics();
		Random rand = new Random();
		for (int i = 0; i < 10; i++)
		{
			int width = 25 + rand.nextInt(76);
			int arc = 0 + rand.nextInt(361);
			int x = rand.nextInt(canvas.getWidth()-width);
			int y = rand.nextInt(canvas.getHeight()-width);

			drawCurvedSpiral(g, x, y, width, width, arc, arc, getRandomColor());
		}
    
  }    
    
    