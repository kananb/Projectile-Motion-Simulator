package engine.vectors;

public class ForceVector {

	private double theta, magnitude;
	private double xcomp, ycomp;

	public ForceVector() {
		this.theta = 0;
		this.magnitude = 0;
		this.xcomp = 0;
		this.ycomp = 0;
	}
	public ForceVector(double theta, double magnitude) {
		this.theta = theta;
		this.magnitude = magnitude;
		updateDimensionalComponents();
	}
	
	private void updateDimensionalComponents() {
		this.xcomp = magnitude * Math.cos(Math.toRadians(theta));
		this.ycomp = magnitude * Math.sin(Math.toRadians(theta));
	}

	public ForceVector getResultant(ForceVector vec) {
		if (vec.getTheta() == theta) {
			return new ForceVector(theta, magnitude + vec.getMagnitude());
		} else if (vec.getTheta() + 180 == theta || vec.getTheta() - 180 == theta) {
			return new ForceVector((magnitude > vec.getMagnitude()) ? theta : vec.getTheta(), Math.abs(vec.getMagnitude()-magnitude));
		} else {
			double startx = magnitude * Math.cos(Math.toRadians(theta));
			double starty = magnitude * Math.sin(Math.toRadians(theta));
			double endx = startx + (vec.getMagnitude() * Math.cos(Math.toRadians(vec.getTheta())));
			double endy = starty + (vec.getMagnitude() * Math.sin(Math.toRadians(vec.getTheta())));
	
			double magnitude = distanceFromOrigin(endx, endy);
			double theta = Math.toDegrees(Math.atan(endy / endx));
			if (theta < 0 && endy < 0) {
				theta += 360;
			} else if ((theta < 0 && endy >= 0) || endy < 0) {
				theta += 180;
			}
	
			return new ForceVector(theta, magnitude);
		}
	}

	private double distanceFromOrigin(double x, double y) {
		return Math.sqrt(x*x + y*y);
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
		updateDimensionalComponents();
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
		updateDimensionalComponents();
	}
	public double getXcomp() {
		return xcomp;
	}
	public double getYcomp() {
		return ycomp;
	}
}
