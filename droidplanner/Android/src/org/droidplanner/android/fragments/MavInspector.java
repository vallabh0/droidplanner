package org.droidplanner.android.fragments;

import org.droidplanner.R;
import org.droidplanner.android.DroidPlannerApp;
import org.droidplanner.android.widgets.AttitudeIndicator;
import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneInterfaces.OnDroneListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MavInspector extends Fragment implements OnDroneListener,OnItemSelectedListener{

	private AttitudeIndicator attitudeIndicator;
	private Drone drone;
	private TextView[] text = new TextView[21];
	
	private Spinner spinner;
	private int index=0;
	
	private boolean headingModeFPV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mav_inspector12, container,
				false);
		attitudeIndicator = (AttitudeIndicator) view.findViewById(R.id.aiView);

		spinner = (Spinner)view.findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(this);
		
		text[1] =(TextView) view.findViewById(R.id.textView1);
		text[2] =(TextView) view.findViewById(R.id.textView2);
		text[3] =(TextView) view.findViewById(R.id.textView3);
		text[4] =(TextView) view.findViewById(R.id.textView4);
		text[5] =(TextView) view.findViewById(R.id.textView5);
		text[6] =(TextView) view.findViewById(R.id.textView6);
		text[7] =(TextView) view.findViewById(R.id.textView7);
		text[8] =(TextView) view.findViewById(R.id.textView8);
		text[9] =(TextView) view.findViewById(R.id.textView9);
		text[10] =(TextView) view.findViewById(R.id.textView10);
		text[11] =(TextView) view.findViewById(R.id.textView11);
		text[12] =(TextView) view.findViewById(R.id.textView12);
		text[13] =(TextView) view.findViewById(R.id.textView13);
		text[14] =(TextView) view.findViewById(R.id.textView14);
		text[15] =(TextView) view.findViewById(R.id.textView15);
		text[16] =(TextView) view.findViewById(R.id.textView16);
		text[17] =(TextView) view.findViewById(R.id.textView17);
		text[18] =(TextView) view.findViewById(R.id.textView18);
		text[19] =(TextView) view.findViewById(R.id.textView19);
		text[20] =(TextView) view.findViewById(R.id.textView20);
	
		drone = ((DroidPlannerApp) getActivity().getApplication()).drone;
		
		

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		drone.events.addDroneListener(this);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity().getApplicationContext());
		headingModeFPV = prefs.getBoolean("pref_heading_mode", false);
	}

	@Override
	public void onStop() {
		super.onStop();
		drone.events.removeDroneListener(this);
	}

	@Override
	public void onDroneEvent(DroneEventsType event, Drone drone) {
		switch (event) {
		
		case HEARTBEAT:
			heartBeatUpdate(drone);
			break;
		case PARAMVALUE:
			paramValueUpdate(drone);
			break;
		case ATTITUDE:
			attitudeUpdate(drone);
			break;
		case LOCALPOSITIONNED:
			localUpdate(drone);
			break;
		case GPS:
			gpsUpdate(drone);
			break;
		case MANUAL:
			manualControlUpdate(drone);
			break;
		case OPTICAL:
			opticalFlowUpdate(drone);
			break;
		case COMPUTER:
			computerUpdate(drone);
			break;
		default:
			break;
		}

	}
	
	
	
	
	public void heartBeatUpdate(Drone drone)
	{
		String [] a={ " ","custom_mode","type","autoppilot","base_mode","system_status","mavlink_version"};
		float[] b = new float[7];
		b[1] = (float) drone.heartbeat.getCustom_mode();
		b[2] = (float) drone.heartbeat.getType();
		b[3] = (float) drone.heartbeat.getAutopilot();
		b[4] = (float) drone.heartbeat.getBase_mode();
		b[5] = (float) drone.heartbeat.getSystem_status();
		b[6] = (float) drone.heartbeat.getMavlink_version();
		
		if( index==0)
			loadText(a,b,6);
		
	}
	public void paramValueUpdate(Drone drone)
	{
		String [] a= {" ","param_value","param_count","param_index","param_id","param_type"};
		float[] b = new float[6];
		b[1] = (float) drone.paramvalue.getparam_value();
		b[2] = (float) drone.paramvalue.getparam_count();
		b[3] = (float) drone.paramvalue.getparam_index();
		b[4] = (float)0.0;
		b[5] = (float) drone.paramvalue.getparam_type();
		
		String c=drone.paramvalue.getparam_id();
		Toast.makeText(getActivity(),c ,Toast.LENGTH_SHORT).show();
		
		if (index==1)
		{
			loadText(a,b,5);
			//text[14].setText(String.format("%s ",c));
		}
		
		
	}
	
	public void attitudeUpdate(Drone drone)
	{
		String [] a={" ","time_boot_ms","roll","pitch","yaw","rollspeed","pitchspeed","yawspeed"};
		float [] b = new float[8];
		b[1] = (float) drone.orientation.getTime();
		b[2] = (float) drone.orientation.getRoll();
		b[3] = (float) drone.orientation.getPitch();
		b[4] = (float) drone.orientation.getYaw();
		b[5] = (float) drone.orientation.getRollSpeed();
		b[6] = (float) drone.orientation.getPitchSpeed();
		b[7] = (float) drone.orientation.getYawSpeed();
		
		if( index==2)
			loadText(a,b,7);
		
	}
	
	public void localUpdate(Drone drone)
	{
		String [] a ={" ","time_boot_ms","x","y","z","vx","vy","vz"};
		float[] b = new float[8];
		b[1] = (float) drone.localpositionned.getTime();
		b[2] = (float) drone.localpositionned.getX();
		b[3] = (float) drone.localpositionned.getY();
		b[4] = (float) drone.localpositionned.getZ();
		b[5] = (float) drone.localpositionned.getVX();
		b[6] = (float) drone.localpositionned.getVY();
		b[7] = (float) drone.localpositionned.getVZ();
		
		if( index==3)
			loadText(a,b,7);
	}
	
	
	public void gpsUpdate(Drone drone)
	{
		String [] a ={" ","time_boot_ms","lat","lon","alt","vx","vy","vz","hdg"};
		float[] b = new float[9];
		b[1] = (float) drone.GPS.getTime();
		b[2] = (float) drone.GPS.getLat();
		b[3] = (float) drone.GPS.getLon();
		b[4] = (float) drone.GPS.getAlt();
		b[5] = (float) drone.GPS.getVx();
		b[6] = (float) drone.GPS.getVy();
		b[7] = (float) drone.GPS.getVz();
		b[7] = (float) drone.GPS.getHdg();
		
		if( index==4)
			loadText(a,b,8);
		
		
		
	}
	
	public void computerUpdate(Drone drone)
	{
		String [] a={" ","time_usec","x_m","y_m","comp_m_x","comp_m_y","ground_distance","flag"};
		float[] b = new float[8];
		b[1] = (float) drone.computer.getTime_usec();
		b[2] = (float) drone.computer.getx_m();
		b[3] = (float) drone.computer.gety_m();
		b[4] = (float) drone.computer.getFlow_comp_m_x();
		b[5] = (float) drone.computer.getFlow_comp_m_y();
		b[6] = (float) drone.computer.getGround_distance();
		b[7] = (float) drone.computer.getFlag();
		
		
		//Toast.makeText(getActivity(),"asdfha " ,Toast.LENGTH_SHORT).show();
		if( index==17)
			loadText(a,b,7);
		
	}

	public void manualControlUpdate(Drone drone) {
		
		String [] a={" ","x","y","z","r","Buttons","Target"};
	//	Toast.makeText(getActivity(),"asdfha"+ a ,Toast.LENGTH_SHORT).show();
		float[] b= new float[7];
		
		b[1] = (float) drone.manual.getx();
		b[2] = (float) drone.manual.gety();
		b[3] = (float) drone.manual.getz();
		b[4] = (float) drone.manual.getr();
		b[5] = (float) drone.manual.getButtons();
		b[6] = (float) drone.manual.getTarget();
		
	//	Toast.makeText(getActivity(),"asdfha"+ a ,Toast.LENGTH_SHORT).show();
		
		
		if( index==9)
			loadText(a,b,6);
		
		
	}


	public void opticalFlowUpdate(Drone drone){
		
		String[] a = {" ","Time_usec","Flow_comp_m_x","Flow_comp_m_y","Ground_distance","Flow_x",
				"Flow_y","Sensor_id","Quality"};
		
		float[] b= new float[9];
		
		b[1] = (float) drone.optical.getTime_usec();
		b[2] = (float) drone.optical.getFlow_comp_m_x();
		b[3] = (float) drone.optical.getFlow_comp_m_y();
		b[4] = (float) drone.optical.getGround_distance();
		b[5] = (float) drone.optical.getFlow_x();
		b[6] = (float) drone.optical.getFlow_y();
		b[7] = (float) drone.optical.getSensor_id();
		b[8] = (float) drone.optical.getQuality();
	
		if( index==16)
			loadText(a,b,8);
		
	}
	
	public void loadText(String[] a,float[] b,int i)
	{
		//Toast.makeText(getActivity(),"asdfha " ,Toast.LENGTH_SHORT).show();
		
		
		while(i>0){
			text[i].setText(String.format("%s ",a[i]));
			text[10+i].setText(String.format("%3.0f ",b[i]));
			i--;
	}
			
		
	}		

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		index=arg2;
		initText();
		// TODO Auto-generated method stub
		
	}
	public void initText()
	{
		for (int i=11;i<=20;i++){
			text[i-10].setText(String.format(" "));
			text[i].setText(String.format(" "));
			}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	


}
