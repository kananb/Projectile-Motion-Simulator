package engine.components.projectiles.tracker;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import engine.components.projectiles.Projectile;
import main.Main;

public class ProjectileTracker {

	private ArrayList<TrackerPoint> points;
	private Iterator<TrackerPoint> iter;
	private int pointLifeInMilliseconds;
	private Projectile projectile;
	private int updateFrequency, counter = 0;
	
	public ProjectileTracker(Projectile projectile, int updatesPerSecond, int pointLifeInMilliseconds) {
		this.projectile = projectile;
		this.pointLifeInMilliseconds = pointLifeInMilliseconds;
		points = new ArrayList<TrackerPoint>();
		iter = points.iterator();
		points.add(new TrackerPoint(projectile.getX() + projectile.getWidth() / 2, projectile.getY() + projectile.getHeight()/2, pointLifeInMilliseconds));
		updateFrequency = updatesPerSecond;
	}
	
	public void update() {
		iter = points.iterator();
		while (iter.hasNext()) {
			TrackerPoint current = iter.next();
			current.update();
			if (current.hasExpired()) {
				iter.remove();
			}
		}
		if (counter >= Main.FPS/updateFrequency) {
			points.add(new TrackerPoint(projectile.getX() + projectile.getWidth() / 2, projectile.getY() + projectile.getHeight()/2, pointLifeInMilliseconds));
			counter = 0;
		}
		counter++;
	}
	
	public void render(Graphics g) {
		g.setColor(projectile.getColor());
		for (TrackerPoint p : points) {
			g.drawRect((int)p.getX(), (int)p.getY(), 1, 1);
		}
	}
	
	public ArrayList<TrackerPoint> getPoints() {
		return points;
	}
	
	public void setPoints(ArrayList<TrackerPoint> points) {
		this.points = points;
	}
	
	public void clearPoints() {
		points.clear();
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

	public int getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(int updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	public int getPointLifeInMilliseconds() {
		return pointLifeInMilliseconds;
	}

	public void setPointLifeInMilliseconds(int pointLifeInMilliseconds) {
		this.pointLifeInMilliseconds = pointLifeInMilliseconds;
	}
}
