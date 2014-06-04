package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneVariable;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;

public class LocalPositionNed extends DroneVariable {
	private double time_boot_ms = 0;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double vx = 0;
	private double vy = 0;
	private double vz = 0;


	public LocalPositionNed(Drone myDrone) {
		super(myDrone);
	}

	public double getTime(){
		return time_boot_ms;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public double getVX() {
		return vx;
	}
	public double getVY() {
		return vy;
	}
	public double getVZ() {
		return vz;
	}

	public void setLocal(double time_boot_ms, double x, double y, double z, double vx, double vy, double vz) {
		this.time_boot_ms =time_boot_ms;
		this.x = x;
		this.y = y;
		this.z = vz;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		
		myDrone.events.notifyDroneEvent(DroneEventsType.LOCALPOSITIONNED);
		
	}

}