package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

public class Computer extends DroneVariable {
	private double time_usec=0; 
	private double x_m=0; 
	private double y_m=0;
	private double flow_comp_m_x=0; 
	private double flow_comp_m_y=0; 
	private double ground_distance=0; 
	private double flag=0;

	public Computer(Drone myDrone) {
		super(myDrone);
	}

	public double getTime_usec() {
		return time_usec;
	}
	
	public double getx_m() {
		return x_m;
	}
	
	public double gety_m() {
		return y_m;
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
	public double getFlag() {
		return flag;
	}

	public void setComputer(double time_usec,double x_m,double y_m, double flow_comp_m_x , double flow_comp_m_y
			, double ground_distance,double flag){
		this.time_usec = time_usec;
		this.x_m = x_m;
		this.y_m = y_m;
		this.flow_comp_m_x = flow_comp_m_x;
		this.flow_comp_m_y = flow_comp_m_y;
		this.ground_distance = ground_distance;
		this.flag= flag;
		myDrone.events.notifyDroneEvent(DroneEventsType.COMPUTER);
	}

}