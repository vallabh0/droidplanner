package org.droidplanner.core.MAVLink;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.helpers.coordinates.Coord2D;

import com.MAVLink.Messages.ApmModes;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.ardupilotmega.msg_attitude;
import com.MAVLink.Messages.ardupilotmega.msg_computer_vision_processor;
import com.MAVLink.Messages.ardupilotmega.msg_global_position_int;
import com.MAVLink.Messages.ardupilotmega.msg_gps_raw_int;
import com.MAVLink.Messages.ardupilotmega.msg_heartbeat;
import com.MAVLink.Messages.ardupilotmega.msg_local_position_ned;
import com.MAVLink.Messages.ardupilotmega.msg_manual_control;
import com.MAVLink.Messages.ardupilotmega.msg_mission_current;
import com.MAVLink.Messages.ardupilotmega.msg_nav_controller_output;
import com.MAVLink.Messages.ardupilotmega.msg_optical_flow;
import com.MAVLink.Messages.ardupilotmega.msg_param_value;
import com.MAVLink.Messages.ardupilotmega.msg_radio;
import com.MAVLink.Messages.ardupilotmega.msg_rc_channels_raw;
import com.MAVLink.Messages.ardupilotmega.msg_servo_output_raw;
import com.MAVLink.Messages.ardupilotmega.msg_sys_status;
import com.MAVLink.Messages.ardupilotmega.msg_vfr_hud;
import com.MAVLink.Messages.enums.MAV_MODE_FLAG;
import com.MAVLink.Messages.enums.MAV_STATE;

public class MavLinkMsgHandler {

	private Drone drone;

	public MavLinkMsgHandler(Drone drone) {
		this.drone = drone;
	}

	public void receiveData(MAVLinkMessage msg) {
		if(drone.parameters.processMessage(msg)) {
		    return;
		}

		drone.waypointManager.processMessage(msg);
		drone.calibrationSetup.processMessage(msg);

		switch (msg.msgid) {
		
		
		case msg_local_position_ned.MAVLINK_MSG_ID_LOCAL_POSITION_NED:
			msg_local_position_ned m_loc =(msg_local_position_ned) msg;
			drone.localpositionned.setLocal(m_loc.time_boot_ms,m_loc.x,m_loc.y,m_loc.z,m_loc.vx,m_loc.vy,m_loc.vz);
			break;
		
		case msg_param_value.MAVLINK_MSG_ID_PARAM_VALUE:
			msg_param_value m_par = (msg_param_value) msg;
			drone.paramvalue.setParamValue(m_par.param_value ,m_par.param_count ,m_par.param_index ,m_par.getParam_Id() ,m_par.param_type );
			break;
			
		
		case msg_computer_vision_processor.MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR:
			
			msg_computer_vision_processor m_comp =(msg_computer_vision_processor) msg;
			drone.computer.setComputer(m_comp.time_usec, m_comp.x_m, m_comp.y_m, m_comp.comp_m_x , m_comp.comp_m_y , m_comp.ground_distance, m_comp.flag);
			break;
		
		case msg_optical_flow.MAVLINK_MSG_ID_OPTICAL_FLOW:
			msg_optical_flow m_opt =(msg_optical_flow) msg;
			drone.optical.setOptical(m_opt.time_usec, m_opt.flow_comp_m_x, m_opt.flow_comp_m_y , m_opt.ground_distance , m_opt.flow_x ,
					m_opt.flow_y , m_opt.sensor_id , m_opt.quality);
			break;
		
		case msg_manual_control.MAVLINK_MSG_ID_MANUAL_CONTROL:
			msg_manual_control m_man= (msg_manual_control) msg;
			drone.manual.setManual(m_man.x,m_man.y,m_man.z,m_man.r,m_man.buttons,m_man.target);
			break;
			
		
		case msg_attitude.MAVLINK_MSG_ID_ATTITUDE:
			msg_attitude m_att = (msg_attitude) msg;
			drone.orientation.setRollPitchYaw(m_att.time_boot_ms ,m_att.roll * 180.0 / Math.PI,
					m_att.pitch * 180.0 / Math.PI, m_att.yaw * 180.0 / Math.PI, m_att.rollspeed,m_att.pitchspeed, m_att.yawspeed);
			break;
		case msg_vfr_hud.MAVLINK_MSG_ID_VFR_HUD:
			msg_vfr_hud m_hud = (msg_vfr_hud) msg;
			drone.setAltitudeGroundAndAirSpeeds(m_hud.alt, m_hud.groundspeed,m_hud.airspeed, m_hud.climb);
			checkIsFlying(m_hud);
			break;
		case msg_mission_current.MAVLINK_MSG_ID_MISSION_CURRENT:
			drone.missionStats.setWpno(((msg_mission_current) msg).seq);
			break;
		case msg_nav_controller_output.MAVLINK_MSG_ID_NAV_CONTROLLER_OUTPUT:
			msg_nav_controller_output m_nav = (msg_nav_controller_output) msg;
			drone.setDisttowpAndSpeedAltErrors(m_nav.wp_dist, m_nav.alt_error,
					m_nav.aspd_error);
			drone.navigation.setNavPitchRollYaw(m_nav.nav_pitch,
					m_nav.nav_roll, m_nav.nav_bearing);
			break;
		case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT:
			msg_heartbeat msg_heart = (msg_heartbeat) msg;
			drone.heartbeat.setHeartBeat(msg_heart.custom_mode ,msg_heart.type ,msg_heart.autopilot ,msg_heart.base_mode ,msg_heart.system_status ,msg_heart.mavlink_version );
			
			drone.type.setType(msg_heart.type);
			processState(msg_heart);
			ApmModes newMode = ApmModes.getMode(msg_heart.custom_mode,
					drone.type.getType());
			drone.state.setMode(newMode);
			drone.heartbeat.onHeartbeat(msg_heart);
			break;
		case msg_global_position_int.MAVLINK_MSG_ID_GLOBAL_POSITION_INT:
			msg_global_position_int msg_gps =(msg_global_position_int) msg;
			drone.GPS.setPosition(new Coord2D(
					((msg_global_position_int) msg).lat / 1E7,
					((msg_global_position_int) msg).lon / 1E7));
			drone.GPS.setGps(msg_gps.time_boot_ms, msg_gps.lat, msg_gps.lon, msg_gps.alt, msg_gps.vx, msg_gps.vy, msg_gps.vz, msg_gps.hdg);
			break;
		case msg_sys_status.MAVLINK_MSG_ID_SYS_STATUS:
			msg_sys_status m_sys = (msg_sys_status) msg;
			drone.battery.setBatteryState(m_sys.voltage_battery / 1000.0,
					m_sys.battery_remaining, m_sys.current_battery / 100.0);
			break;
		case msg_radio.MAVLINK_MSG_ID_RADIO:
			msg_radio m_radio = (msg_radio) msg;
			drone.radio.setRadioState(m_radio.rxerrors, m_radio.fixed,
					m_radio.rssi, m_radio.remrssi, m_radio.txbuf,
					m_radio.noise, m_radio.remnoise);
			break;
		case msg_gps_raw_int.MAVLINK_MSG_ID_GPS_RAW_INT:
			drone.GPS.setGpsState(((msg_gps_raw_int) msg).fix_type,
					((msg_gps_raw_int) msg).satellites_visible,
					((msg_gps_raw_int) msg).eph);
			break;
		case msg_rc_channels_raw.MAVLINK_MSG_ID_RC_CHANNELS_RAW:
			drone.RC.setRcInputValues((msg_rc_channels_raw) msg);
			break;
		case msg_servo_output_raw.MAVLINK_MSG_ID_SERVO_OUTPUT_RAW:
			drone.RC.setRcOutputValues((msg_servo_output_raw) msg);
			break;
		}
	}

	public void processState(msg_heartbeat msg_heart) {
		checkArmState(msg_heart);
		checkFailsafe(msg_heart);
	}

	public void checkIsFlying(msg_vfr_hud m_hud) {
		drone.state.setIsFlying(m_hud.throttle > 0);
	}

	private void checkFailsafe(msg_heartbeat msg_heart) {
		boolean failsafe2 = msg_heart.system_status == (byte) MAV_STATE.MAV_STATE_CRITICAL;
		drone.state.setFailsafe(failsafe2);
	}

	private void checkArmState(msg_heartbeat msg_heart) {
		drone.state
				.setArmed((msg_heart.base_mode & (byte) MAV_MODE_FLAG.MAV_MODE_FLAG_SAFETY_ARMED) == (byte) MAV_MODE_FLAG.MAV_MODE_FLAG_SAFETY_ARMED);
	}
}
