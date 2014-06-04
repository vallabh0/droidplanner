// MESSAGE OPTICAL_FLOW PACKING
package com.MAVLink.Messages.ardupilotmega;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkPayload;
//import android.util.Log;

/**
* Optical flow from a flow sensor (e.g. optical mouse sensor)
*/
public class msg_computer_vision_processor extends MAVLinkMessage{

	public static final int MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR = 240;
	public static final int MAVLINK_MSG_LENGTH = 29;
	private static final long serialVersionUID = MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR;
	

 	/**
	* Timestamp (UNIX)
	*/
	public long time_usec;  
	public float x_m; 
	public float y_m; 
	public float comp_m_x; 
	public float comp_m_y; 
	public float ground_distance; 
	public byte flag;
	

	

	/**
	 * Generates the payload for a mavlink message for a message of this type
	 * @return
	 */
	public MAVLinkPacket pack(){
		MAVLinkPacket packet = new MAVLinkPacket();
		packet.len = MAVLINK_MSG_LENGTH;
		packet.sysid = 255;
		packet.compid = 190;
		packet.msgid = MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR;
		packet.payload.putLong(time_usec);
		packet.payload.putFloat(x_m);
		packet.payload.putFloat(y_m);
		packet.payload.putFloat(comp_m_x);
		packet.payload.putFloat(comp_m_y);
		packet.payload.putFloat(ground_distance);
		packet.payload.putByte(flag);
		return packet;		
	}

    /**
     * Decode a optical_flow message into this class fields
     *
     * @param payload The message to decode
     */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
	    time_usec = payload.getLong();
	    x_m = payload.getFloat();
	    y_m = payload.getFloat();
	    comp_m_x = payload.getFloat();
	    comp_m_y = payload.getFloat();
	    ground_distance = payload.getFloat();
	    flag = payload.getByte(); 
    }

     /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_computer_vision_processor(){
    	msgid = MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR;
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     * 
     */
    public msg_computer_vision_processor(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR;
        unpack(mavLinkPacket.payload);
        //Log.d("MAVLink", "OPTICAL_FLOW");
        //Log.d("MAVLINK_MSG_ID_OPTICAL_FLOW", toString());
    }
    
                
    /**
     * Returns a string with the MSG name and data
     */
    public String toString(){
    	return "MAVLINK_MSG_ID_COMPUTER_VISION_PROCESSOR -"+" time_usec:"+time_usec+" flag"+ flag + " x_m:"+x_m+" y_m:"+y_m+ " comp_m_x:"+ comp_m_x+" comp_m_y:"+ comp_m_y+" ground_distance:"+ground_distance+"";
    }
}
