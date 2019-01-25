package engine.components.projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import engine.PEngine;
import engine.components.PComponent;
import engine.components.projectiles.tracker.ProjectileTracker;
import engine.vectors.ForceVector;
import main.Main;

public class Projectile extends PComponent {

	protected double mass;
	protected ForceVector motionVector;
	protected ForceVector inertiaVector;
	protected ForceVector frictionVector;
	protected double maxFrictionForce;

	protected Color color;
	protected int vectorThickness;
	
	protected boolean tracking = false;
	protected ProjectileTracker pt;

	public Projectile(int x, int y, double mass, int width, int height, Color color) {
		super(x, y, width, height);
		this.mass = mass;
		this.color = color;
		this.vectorThickness = Math.max(((width+height) / 2) / 6, 1);
		this.motionVector = new ForceVector();
		this.frictionVector = new ForceVector();
		this.inertiaVector = new ForceVector();
		this.maxFrictionForce = PEngine.FRICTION_COEFFICIENT * mass * PEngine.GRAVITY_CONSTANT;
	}
	public Projectile(int x, int y, double mass, int width, int height, ForceVector motionVector, Color color) {
		super(x, y, width, height);
		this.mass = mass;
		this.color = color;
		this.vectorThickness = Math.max(((width+height) / 2) / 6, 1);
		this.motionVector = motionVector;
		this.frictionVector = new ForceVector();
		updateInertiaVector();
		this.maxFrictionForce = PEngine.FRICTION_COEFFICIENT * mass * PEngine.GRAVITY_CONSTANT;
	}
	
	public synchronized void update() {
		motionVector = motionVector.getResultant(PEngine.GRAVITY_VEC);
		updateInertiaVector();

		if (y >= floorHeight - height && motionVector.getYcomp() <= 0) {
			y = floorHeight - height;
			double magnitude = Math.abs(motionVector.getXcomp());
			motionVector.setTheta((motionVector.getXcomp() < 0) ? 180 : 0);
			motionVector.setMagnitude(magnitude);
			updateInertiaVector();
			frictionVector = new ForceVector((motionVector.getXcomp() > 0) ? 180 : 0,
					(maxFrictionForce > Math.abs(inertiaVector.getXcomp())) ? Math.abs(inertiaVector.getXcomp()) : maxFrictionForce);
			inertiaVector = inertiaVector.getResultant(frictionVector);
			motionVector.setMagnitude(inertiaVector.getMagnitude() / mass);
		} else {
			y -= motionVector.getYcomp();
		}

		if (x <= 0) {
			x = 0;
			motionVector.setTheta(180 - motionVector.getTheta());
		} else if (x + width >= sideWidth) {
			x = sideWidth - width;
			motionVector.setTheta(180 - motionVector.getTheta());
		}
		x += motionVector.getXcomp();
		
		updateHitbox();
		
		if (tracking && pt != null) {
			pt.update();
		}
	}
	public void updateHitbox() {
		hitbox = new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		g.setColor(color);
		if (tracking && pt != null) {
			pt.render(g);
		}
		g.fillRect(x, y, width, height);
		renderFrictionVector(g);
		renderMotionVector(g);
	}
	private void renderMotionVector(Graphics g) {
		g.setColor(new Color(100, 200, 100));
		for (int i = -vectorThickness / 2; i < vectorThickness / 2 + 1; i++) {
			for (int j = -vectorThickness / 2; j < vectorThickness / 2 + 1; j++) {
				g.drawLine(x + width / 2 + i, y + height / 2 + j,
						x + width / 2 + i + (int) motionVector.getXcomp() * 2,
						y + height / 2 + j - (int) motionVector.getYcomp() * 2);
			}
		}
	}
	private void renderFrictionVector(Graphics g) {
		g.setColor(new Color(200, 100, 100));
		for (int i = -vectorThickness / 2; i < vectorThickness / 2 + 1; i++) {
			for (int j = -vectorThickness / 2; j < vectorThickness / 2 + 1; j++) {
				g.drawLine(x + width / 2 + i, y + height / 2 + j,
						x + width / 2 + i + (int) (frictionVector.getXcomp() / mass * 10),
						y + height / 2 + j - (int) (frictionVector.getYcomp() / mass * 10));
			}
		}
	}

	public synchronized void launch(ForceVector motionVector) {
		this.motionVector = motionVector;
	}

	public synchronized void push(ForceVector motionVector) {
		this.motionVector = this.motionVector.getResultant(motionVector);
	}
	
	public void startTracking(int updatesPerSecond) {
		pt = new ProjectileTracker(this, Math.min(updatesPerSecond, Main.FPS), 1000);
		tracking = true;
	}
	
	public void stopTracking() {
		tracking = false;
		pt = null;
	}

	public void updateInertiaVector() {
		this.inertiaVector = new ForceVector(motionVector.getTheta(), motionVector.getMagnitude() * mass);
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
		updateInertiaVector();
	}

	public ForceVector getMotionVector() {
		return motionVector;
	}

	public synchronized void setMotionVector(ForceVector motionVector) {
		this.motionVector = motionVector;
		updateInertiaVector();
	}

	public ForceVector getFrictionVector() {
		return frictionVector;
	}

	public void setFrictionVector(ForceVector frictionVector) {
		this.frictionVector = frictionVector;
	}

	public ForceVector getInertiaVector() {
		return inertiaVector;
	}

	public double getMaxFrictionForce() {
		return maxFrictionForce;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
