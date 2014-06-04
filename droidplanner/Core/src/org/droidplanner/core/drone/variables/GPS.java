package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;
import org.droidplanner.core.helpers.coordinates.Coord2D;

public class GPS extends DroneVariable {
	private int time_boot_ms;
	private int lat;
	private int lon;
	private int alt;
	private int relative_alt;
	private short vx;
	private short vy;
	private short vz;
	private short hdg;
	
	private double gps_eph = -1;
	private int satCount = -1;
	private int fixType = -1;
	private Coord2D position;

	public GPS(Drone myDrone) {
		super(myDrone);
	}

	public boolean isPositionValid() {
		return (position != null);
	}

	public Coord2D getPosition() {
		if (isPositionValid()) {
			return position;
		} else {
			return new Coord2D(0, 0);
		}
	}

	public double getGpsEPH() {
		return gps_eph;
	}

	public int getSatCount() {
		return satCount;
	}

	public String getFixType() {
		String gpsFix = "";
		switch (fixType) {
		case 2:
			gpsFix = ("2D");
			break;
		case 3:
			gpsFix = ("3D");
			break;
		default:
			gpsFix = ("NoFix");
			break;
		}
		return gpsFix;
	}

	public int getFixTypeNumeric() {
		return fixType;
	}

	public void setGpsState(int fix, int satellites_visible, int eph) {
		if (satCount != satellites_visible) {
			satCount = satellites_visible;
			gps_eph = (double) eph / 100; // convert from eph(cm) to gps_eph(m)
			myDrone.events.notifyDroneEvent(DroneEventsType.GPS_COUNT);
		}
		if (fixType != fix) {
			fixType = fix;
			myDrone.events.notifyDroneEvent(DroneEventsType.GPS_FIX);
		}
	}

	public void setPosition(Coord2D position) {
		if (this.position != position) {
			this.position = position;
			myDrone.events.notifyDroneEvent(DroneEventsType.GPS);
		}
	}
	
	public int getTime()
	{
		return time_boot_ms;
		
	}
	
	public int getLat()
	{
		return lat;
		
	}
	
	public int getLon()
	{
		return lon;
		
	}
	
	public int getAlt()
	{
		return alt;
		
	}
	
	public short getVx()
	{
		return vx;
		
	}
	
	public short getVy()
	{
		return vy;
		
	}
	
	public short getVz()
	{
		return vz;
		
	}
	
	public short getHdg()
	{
		return hdg;
		
	}
	
	
	public void setGps(int time_boot_ms, int lat, int lon, int alt, short vx, short vy, short vz, short hdg) {
		this.time_boot_ms = time_boot_ms;
		this.lat =	lat;	
		this.lon = lon;
		this.alt =	alt;
		this.vx =vx ;
		this.vy =	vy;
		this.vz =vz ;
		this.hdg =hdg	;
			myDrone.events.notifyDroneEvent(DroneEventsType.GPS);
		
	}
	
	
}