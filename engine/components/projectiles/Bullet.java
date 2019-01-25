package engine.components.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import engine.PEngine;
import engine.components.PComponent;
import engine.vectors.ForceVector;

public class Bullet extends Projectile {
	
	private boolean hit = false;
	private PComponent owner;

	public Bullet(int x, int y, double mass, int diameter, ForceVector vector, PComponent owner) {
		super(x, y, mass, diameter, diameter, vector, Color.WHITE);
		this.owner = owner;
	}

	@Override
	public void update() {
		motionVector = motionVector.getResultant(PEngine.GRAVITY_VEC);
		updateInertiaVector();

		if (hasCollided() || hit) {
			hit = true;
			motionVector.setMagnitude(0);
		} else if (hasHitFloor()) {
//			hit = true;
//			motionVector.setMagnitude(0);
//			y = floorHeight - height;
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
		
		if (tracking && pt != null) {
			pt.update();
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		if (tracking && pt != null) {
			pt.render(g);
		}
		g.fillRect(x, y, width, height);
	}
	
	public boolean hasHit() {
		return hit;
	}
	
	public boolean hasHitFloor() {
		return y >= floorHeight - height;
	}
	
	public boolean hasCollided() {
		for (PComponent c : engine.getComponents()) {
			if (c != this && c != owner && !(c instanceof Bullet) && collided(c)) {
				return true;
			}
		}
		return false;
	}
	
	public PComponent getOwner() {
		return owner;
	}
	
	public void setOwner(PComponent owner) {
		this.owner = owner;
	}
}
