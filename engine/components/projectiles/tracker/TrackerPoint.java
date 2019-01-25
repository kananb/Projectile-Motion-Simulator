package engine.components.projectiles.tracker;

public class TrackerPoint {

	private int x, y;
	private long lifeTime, age;
	private boolean expired = false;
	
	public TrackerPoint(int x, int y, int lifeInMilliseconds) {
		this.x = x;
		this.y = y;
		this.lifeTime = lifeInMilliseconds * 1000000 + System.nanoTime();
		this.age = System.nanoTime();
	}
	
	public void update() {
		age = System.nanoTime();
		if (age >= lifeTime) {
			expired = true;
		}
	}

	public boolean hasExpired() {
		return expired;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void extendLife(int lifeInMilliseconds) {
		lifeTime += lifeInMilliseconds * 1000000;
	}

	public void shortenLife(int lifeInMilliseconds) {
		lifeTime -= lifeInMilliseconds * 1000000;
	}
}
