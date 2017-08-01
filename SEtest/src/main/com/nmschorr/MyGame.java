package com.nmschorr;

import java.awt.Dimension;





public class MyGame {
	int width = 5;
	int scale = 2;
	int height = 3;
	
	
public static void Game() {
    Dimension size = new java.awt.Dimension(width * scale, height * scale);
    setPreferredSize(size);

    screen = new Screen(width, height);
    frame = new JFrame();
    key = new Keyboard();
    level = new RandomLevel(64, 64);
    player = new Player(key);

    addKeyListener(key);
}

public Level(int width, int height) {
	this.width = width;
	this.height = height;
	tiles = new int[width * height];
	generateLevel();
}

public static void main(String[] args) { 
	Game game = new Game(); 
	game.frame.setResizable(false); 
	game.frame.setTitle(Game.title); 
	game.frame.add(game); 
	game.frame.pack(); 
	game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	game.frame.setLocationRelativeTo(null); 
	game.frame.setVisible(true);

	game.start();
}
}