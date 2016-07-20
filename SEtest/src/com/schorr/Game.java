package com.schorr;

import java.awt.Dimension;
import javax.swing.*;

public class Game  {
	int width = 5;
	int tscale = 2;
	int height = 3;
	Dimension nd;
	int first = width * tscale;
	int second = height * tscale;
	
	//nd = new java.awt.Dimension(first,second);
    //setPreferredSize(size);

    Screen screen = new Screen(width, height);
    JFrame.frame = new JFrame();
    key = new Keyboard();
    level = new RandomLevel(64, 64);
    player = new Player(key);

    addKeyListener(key);
}
