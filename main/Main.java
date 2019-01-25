package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import engine.PEngine;
import engine.components.projectiles.Bullet;
import engine.components.projectiles.Projectile;
import engine.vectors.ForceVector;
import gui.Display;

public class Main implements Runnable, KeyListener {
	
	public static int FPS = 60;

	private Display display;
	private Graphics g;
	private BufferStrategy bs;
	
	private PEngine engine;
	private Projectile projectile;

	private int windowWidth, windowHeight;
	private String windowTitle;
	
	private float red, green, blue;
	private float colorTimer = 0;

	public Main(int windowWidth, int windowHeight, String windowTitle) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowTitle = windowTitle;
	}

	private void init() {
		display = new Display(windowWidth, windowHeight, windowTitle);
		display.getCanvas().addKeyListener(this);
		
		engine = new PEngine(windowHeight, windowWidth, 500);
		
		projectile = new Projectile(windowWidth / 2 - 10, windowHeight/20, 100, 20, 20, new Color(210, 210, 230));
		
		engine.addComponent(projectile);
	}

	private void update() {
		engine.update();

		colorTimer += 0.005;

		red = (float)(Math.sin(colorTimer)+1)/2f;
		green = (float)(Math.sin(colorTimer+Math.PI/2)+1)/2f;
		blue = (float)(Math.sin(colorTimer+Math.PI) + 1)/2f;
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			render();
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, windowWidth, windowHeight);
		g.setColor(new Color(red/10, green/10, blue/10));
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		engine.render(g);
		
		bs.show();
		g.dispose();
	}

	@Override
	public void run() {
		double timePerUpdate = 1 * Math.pow(10, 9) / FPS;
		double delta = 0;
		long now, lastTime = System.nanoTime();

		init();

		while (true) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;

			if (delta >= 1) {
				update();
				render();
				delta--;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int horizontalPushForce = 3;
		int verticalPushForce = 5;
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (engine.isActivated()) {
				engine.pauseSimulation();
			} else {
				engine.startSimulation();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation(), displayLocation = display.getFrame().getLocation();
			double rise = -(mouseLocation.getY() - projectile.getHeight()*2) + (displayLocation.getY() + projectile.getY());
			double run = (mouseLocation.getX() - projectile.getWidth()/2) - (displayLocation.getX() + projectile.getX());
			double theta = Math.toDegrees(Math.atan( (rise) / (run) ));
			
			if (theta < 0 && rise < 0) {
				theta += 360;
			} else if ((theta < 0 && rise >= 0) || rise < 0) {
				theta += 180;
			}
			int diameter = 7;
			engine.addComponent(new Bullet(projectile.getX() + projectile.getWidth()/2 - diameter/2, projectile.getY() + projectile.getHeight()/2 - diameter/2, 1, diameter, new ForceVector(theta, 15).getResultant(projectile.getMotionVector()), projectile));
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			engine.clearComponents();
			engine.addComponent(projectile);
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			projectile.push(new ForceVector(90, verticalPushForce));
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			projectile.push(new ForceVector(270, verticalPushForce));
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			projectile.push(new ForceVector(180, horizontalPushForce));
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			projectile.push(new ForceVector(0, horizontalPushForce));
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {		
	}
}
