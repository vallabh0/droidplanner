package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

public class ParamValue extends DroneVariable {
	private float param_value=0; 
	private short param_count=0; 
	private short param_index=0; 
	private String param_id; 
	private byte param_type=0; 
	
	public ParamValue(Drone myDrone) {
		super(myDrone);
	}

	public float getparam_value() {
		return param_value;
	}

	public short getparam_count() {
		return param_count;
	}
	
	public short getparam_index() {
		return param_index;
	}

	public String getparam_id() {
		return param_id;
	}
	public byte getparam_type() {
		return param_type;
	}
	

	public void setParamValue(float param_value, short param_count , short param_index
			, String param_id, byte param_type) {
		this.param_value = param_value;
		this.param_count = param_count;
		this.param_index = param_index;
		
		this.param_id = param_id;
		this.param_type = param_type;
		
		myDrone.events.notifyDroneEvent(DroneEventsType.PARAMVALUE);
	}

}