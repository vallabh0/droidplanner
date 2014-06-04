package org.droidplanner.core.drone.variables;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneInterfaces.Handler;
import org.droidplanner.core.drone.DroneInterfaces.OnDroneListener;
import org.droidplanner.core.drone.DroneVariable;

import com.MAVLink.Messages.ardupilotmega.msg_heartbeat;

public class HeartBeat extends DroneVariable implements OnDroneListener {

	private int custom_mode=0; 
	private byte type=0; 
	private byte autopilot=0; 
	private byte base_mode=0; 
	private byte system_status=0; 
	private byte mavlink_version=0;

	
	private static long HEARTBEAT_NORMAL_TIMEOUT = 5000;
	private static long HEARTBEAT_LOST_TIMEOUT = 15000;

	public HeartbeatState heartbeatState = HeartbeatState.FIRST_HEARTBEAT;;
	public int droneID = 1;

	enum HeartbeatState {
		FIRST_HEARTBEAT, LOST_HEARTBEAT, NORMAL_HEARTBEAT
	}

	public Handler watchdog;
	public Runnable watchdogCallback = new Runnable() {
		@Override
		public void run() {
			onHeartbeatTimeout();
		}
	};

	public HeartBeat(Drone myDrone, Handler handler) {
		super(myDrone);
		this.watchdog = handler;
		myDrone.events.addDroneListener(this);
	}

	public void onHeartbeat(msg_heartbeat msg) {
		droneID = msg.sysid;

		switch (heartbeatState) {
		case FIRST_HEARTBEAT:
			myDrone.events.notifyDroneEvent(DroneEventsType.HEARTBEAT_FIRST);
			break;
		case LOST_HEARTBEAT:
			myDrone.events.notifyDroneEvent(DroneEventsType.HEARTBEAT_RESTORED);
			break;
		case NORMAL_HEARTBEAT:
			break;
		}

		heartbeatState = HeartbeatState.NORMAL_HEARTBEAT;
		restartWatchdog(HEARTBEAT_NORMAL_TIMEOUT);
	}

	@Override
	public void onDroneEvent(DroneEventsType event, Drone drone) {
		switch (event) {
		case CONNECTED:
			notifyConnected();
			break;
		case DISCONNECTED:
			notifiyDisconnected();
			break;
		default:
			break;
		}
	}

	private void notifyConnected() {
		restartWatchdog(HEARTBEAT_NORMAL_TIMEOUT);
	}

	private void notifiyDisconnected() {
		watchdog.removeCallbacks(watchdogCallback);
		heartbeatState = HeartbeatState.FIRST_HEARTBEAT;
	}

	private void onHeartbeatTimeout() {
		heartbeatState = HeartbeatState.LOST_HEARTBEAT;
		restartWatchdog(HEARTBEAT_LOST_TIMEOUT);
		myDrone.events.notifyDroneEvent(DroneEventsType.HEARTBEAT_TIMEOUT);
	}

	private void restartWatchdog(long timeout) {
		// re-start watchdog
		watchdog.removeCallbacks(watchdogCallback);
		watchdog.postDelayed(watchdogCallback, timeout);
	}
	
	public int getCustom_mode() {
		return custom_mode;
	}

	public byte getType() {
		return type;
	}
	
	public byte getAutopilot() {
		return autopilot;
	}

	public byte getBase_mode() {
		return base_mode;
	}
	public byte getSystem_status() {
		return system_status;
	}
	public byte getMavlink_version() {
		return mavlink_version;
	}
	

	public void setHeartBeat(int custom_mode, byte type , byte autopilot
			, byte base_mode, byte system_status, byte mavlink_version) {
		this.custom_mode = custom_mode;
		this.type = type;
		this.autopilot = autopilot;
		this.base_mode = base_mode;
		this.system_status = system_status;
		this.mavlink_version = mavlink_version;
		myDrone.events.notifyDroneEvent(DroneEventsType.HEARTBEAT);
	}
}
