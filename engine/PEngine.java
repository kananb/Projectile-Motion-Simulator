package engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import engine.components.PComponent;
import engine.components.projectiles.Bullet;
import engine.vectors.ForceVector;
import main.Main;

public class PEngine {

	public static final double GRAVITY_CONSTANT = 9.80665;
	public static final double FRICTION_COEFFICIENT = .1;
	public static final ForceVector GRAVITY_VEC = new ForceVector(270, GRAVITY_CONSTANT/Main.FPS);
	
	private ArrayList<PComponent> components;
	private Iterator<PComponent> iter;
	
	private boolean activated = false;
	private int floorHeight;
	private int sideWidth;
	private int maximumComponents;

	public PEngine(int floorHeight, int sideWidth, int maximumComponents) {
		this.components = new ArrayList<PComponent>();
		this.floorHeight = floorHeight;
		this.sideWidth = sideWidth;
		this.maximumComponents = maximumComponents;
	}

	public synchronized void update() {
		if (activated) {
			for (PComponent c : components) {
				c.update();
			}
		}
	}

	public synchronized void render(Graphics g) {
		for (PComponent c : components) {
			c.render(g);
		}
	}
	
	public void startSimulation() {
		activated = true;
	}
	public void pauseSimulation() {
		activated = false;
	}

	public ArrayList<PComponent> getComponents() {
		return components;
	}

	public synchronized void addComponent(PComponent component) {
		this.components.add(component);
		component.setFloorHeight(floorHeight);
		component.setSideWidth(sideWidth);
		component.setPEngine(this);
		if (component instanceof Bullet) {
			((Bullet) component).startTracking(60);
		}
		if (numberOf(component.getClass()) > maximumComponents) {
			removeFirst(component.getClass());
		}
	}
	
	private int numberOf(Class<? extends PComponent> objectClass) {
		int amount = 0;
		for (PComponent comp : components) {
			if (comp.getClass() == objectClass) amount++;
		}
		return amount;
	}
	
	private void removeFirst(Class<? extends PComponent> objectClass) {
		iter = components.iterator();
		while (iter.hasNext()) {
			PComponent current = iter.next();
			if (current.getClass() == objectClass) {
				iter.remove();
				break;
			}
		}
	}

	public synchronized void addComponents(PComponent... components) {
		for (PComponent pc : components) {
			this.components.add(pc);
			pc.setFloorHeight(floorHeight);
			pc.setSideWidth(sideWidth);
			pc.setPEngine(this);
			if (pc instanceof Bullet) {
				((Bullet) pc).startTracking(60);
			}
			if (this.components.size() > maximumComponents) {
				this.components.remove(0);
			}
		}
	}
	
	public synchronized void removeComponent(PComponent component) {
		iter = components.iterator();
		while (iter.hasNext()) {
			PComponent current = iter.next();
			if (current == component) {
				iter.remove();
				break;
			}
		}
	}
	
	public synchronized void removeComponents(PComponent...components) {
		for (PComponent pc : components) {
			iter = this.components.iterator();
			while (iter.hasNext()) {
				PComponent current = iter.next();
				if (current == pc) {
					iter.remove();
					break;
				}
			}
		}
	}
	
	public synchronized void clearComponents() {
		components.clear();
	}
	
	public boolean isActivated() {
		return activated;
	}

	public synchronized void setComponents(ArrayList<PComponent> components) {
		this.components = components;
	}

	public int getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(int floorHeight) {
		this.floorHeight = floorHeight;
	}

	public int getSideWidth() {
		return sideWidth;
	}

	public void setSideWidth(int sideWidth) {
		this.sideWidth = sideWidth;
	}
}
