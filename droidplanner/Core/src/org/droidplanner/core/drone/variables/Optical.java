package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

public class Optical extends DroneVariable {
	private double time_usec=0; 
	private double flow_comp_m_x=0; 
	private double flow_comp_m_y=0; 
	private double ground_distance=0; 
	private double flow_x=0; 
	private double flow_y=0; 
	private double sensor_id=0;
	private double quality=0;

	public Optical(Drone myDrone) {
		super(myDrone);
	}

	public double getTime_usec() {
		return time_usec;
	}

	public double getFlow_comp_m_x() {
		return flow_comp_m_x;
	}
	
	public double getFlow_comp_m_y() {
		return flow_comp_m_y;
	}

	public double getGround_distance() {
		return ground_distance;
	}
	public double getFlow_x() {
		return flow_x;
	}
	public double getFlow_y() {
		return flow_y;
	}
	public double getSensor_id() {
		return sensor_id;
	}
	public double getQuality() {
		return quality;
	}

	public void setOptical(double time_usec, double flow_comp_m_x , double flow_comp_m_y
			, double ground_distance, double flow_x, double flow_y, double sensor_id, double quality) {
		this.time_usec = time_usec;
		this.flow_comp_m_x = flow_comp_m_x;
		this.flow_comp_m_y = flow_comp_m_y;
		this.ground_distance = ground_distance;
		this.flow_x = flow_x;
		this.flow_y = flow_y;
		this.sensor_id = sensor_id;
		this.quality = quality;
		myDrone.events.notifyDroneEvent(DroneEventsType.OPTICAL);
	}

}