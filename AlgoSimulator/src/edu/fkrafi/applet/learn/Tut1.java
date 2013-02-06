package edu.fkrafi.applet.learn;

import java.awt.Graphics;

import javax.swing.JApplet;

public class Tut1 extends JApplet {
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("This is working!", 25, 25);
	}
}
