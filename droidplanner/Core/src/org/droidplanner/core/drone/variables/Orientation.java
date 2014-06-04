package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

public class Orientation extends DroneVariable {
	private double time_boot_ms =0;
	private double roll = 0;
	private double pitch = 0;
	private double yaw = 0;
	private double rollspeed = 0;
	private double pitchspeed = 0;
	private double yawspeed = 0;

	public Orientation(Drone myDrone) {
		super(myDrone);
	}
	
	public double getTime() {
		return time_boot_ms;
	}
	


	public double getRoll() {
		return roll;
	}

	public double getPitch() {
		return pitch;
	}

	public double getYaw() {
		return yaw;
	}
	public double getRollSpeed() {
		return rollspeed;
	}

	public double getPitchSpeed() {
		return pitchspeed;
	}

	public double getYawSpeed() {
		return yawspeed;
	}

	public void setRollPitchYaw(double time_boot_ms, double roll, double pitch, double yaw , double rollspeed, double pitchspeed, double yawspeed ) {
		this.time_boot_ms= time_boot_ms;
		this.roll = roll;
		this.pitch = pitch;
		this.yaw = yaw;
		this.rollspeed = rollspeed;
		this.pitchspeed = pitchspeed;
		this.yawspeed = yawspeed;
		myDrone.events.notifyDroneEvent(DroneEventsType.ATTITUDE);
		myDrone.events.notifyDroneEvent(DroneEventsType.ORIENTATION);
	}

}