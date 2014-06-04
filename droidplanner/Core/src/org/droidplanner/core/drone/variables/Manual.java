package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

public class Manual extends DroneVariable {
	private double x=0; 
	private double y=0; 
	private double z=0; 
	private double r=0; 
	private double buttons=0; 
	private double target=0; 

	public Manual(Drone myDrone) {
		super(myDrone);
	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public double getz() {
		return z;
	}
	public double getr() {
		return r;
	}
	public double getButtons() {
		return buttons;
	}
	public double getTarget() {
		return target;
	}

	public void setManual(double x, double y , double z, double r,double buttons, double target) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.buttons = buttons;
		this.target = target;
		myDrone.events.notifyDroneEvent(DroneEventsType.MANUAL);
	}

}