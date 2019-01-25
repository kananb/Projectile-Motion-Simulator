package main;

public class Launcher {

	public static void main(String[] args) {
		int width, height;
		
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			width = 1000;
			height = 950;
		}
		
		new Main(width, height, "2D Projectile Motion Simulator").run();
	}
}
