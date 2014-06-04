package org.droidplanner.core.drone.variables;

import org.droidplanner.core.MAVLink.MavLinkCalibration;
import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneVariable;

import android.widget.Toast;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.ardupilotmega.msg_statustext;

public class Calibration extends DroneVariable {
	private Drone myDrone;
	private String mavMsg;
	private static boolean calibrating;

	public Calibration(Drone drone) {
		super(drone);
		this.myDrone = drone;
	}

	public void startCalibration(int n) {
		Calibration.calibrating = true;
		switch (n){
		case 1:{
	
			MavLinkCalibration.sendStartGyroCalibrationMessage(myDrone);
			break;
		}
		case 2:{
			MavLinkCalibration.sendStartMagCalibrationMessage(myDrone);
			break;
		}
		case 5:{
			MavLinkCalibration.sendStartAccelCalibrationMessage(myDrone);
			break;
		}
		}
		}

	public void sendAckk(int step) {
		MavLinkCalibration.sendCalibrationAckMessage(step, myDrone);
	}

	public void processMessage(MAVLinkMessage msg) {
		if (msg.msgid == msg_statustext.MAVLINK_MSG_ID_STATUSTEXT) {
			msg_statustext statusMsg = (msg_statustext) msg;
			mavMsg = statusMsg.getText();
		//	Toast.makeText(myDrone.context, mavMsg, Toast.LENGTH_SHORT).show();
			if (mavMsg.contains("accel calibration: started"))
				Calibration.calibrating = false;

			myDrone.events.notifyDroneEvent(DroneEventsType.CALIBRATION_IMU);
			myDrone.events.notifyDroneEvent(DroneEventsType.CALIBRATION_GYRO);
			
		}
	}

	public String getMessage() {
		return mavMsg;
	}

	public static void setClibrating(boolean flag) {
		Calibration.calibrating = flag;
	}

	public static boolean isCalibrating() {
		return Calibration.calibrating;
	}
}
