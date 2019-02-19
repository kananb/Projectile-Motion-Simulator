# Projectile-Physics-Simulator
A simple projectile / vector motion simulator written in Java

Using Java's Swing library, this projectile physics simulator allows the user to control a small block by applying forces to it, launch projectiles from the block, and freeze and unfreeze the simulation. While the simulation is frozen, forces can still be applied to the block and projectiles can still be launched from it, although projectiles will not be updated until the simulation is unfrozen.

### Information

* Environment:
  * The left and right edges of the window act as walls that allow objects to bounce off of them elastically. The bottom edge of the window acts as a floor which applies a frictional force to objects in contact with it. The program does not simulate any air resistance, so all objects will retain their horizontal velocities as long as they are not in contact with the floor. There is a constant downard force applied to all objects which simulates gravity.
* Block
  * The block is controlled by the user via keyboard input. The block's motion is defined using a force vector from which the block's horizontal and vertical velocities can be derived. When new forces are applied, either friction or user input, they are added to the block's current force vector. A small green line is drawn to represent the block's current force vector; the length of the line is representative of the magnitude of the vector, and its direction is representative of the angle of the vector.
* Projectiles
  * Projectiles are launched from the block via keyboard input. All projectiles are launched towards the user's mouse from the block. All projectiles have an initial velocity that is added to the block's current velocity. This means that if a projectile is shot at an angle opposite of the angle at which the block is traveling, it may have a slower velocity. All projectiles leave a trail behind them showing the path that they took after being launched; paths disappear after a short amount of time.


### Controls

	- W			; applies an upward force
	- A			; applies a force left
	- S			; applies a downward force
	- D			; applies a force right

	- Space			; launches a projectile
