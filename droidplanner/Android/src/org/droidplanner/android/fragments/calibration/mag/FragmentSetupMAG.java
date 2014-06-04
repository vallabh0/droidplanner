package org.droidplanner.android.fragments.calibration.mag;
import org.droidplanner.R;
import org.droidplanner.android.fragments.calibration.SetupMainPanel;
import org.droidplanner.android.fragments.calibration.SetupSidePanel;
import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.core.drone.DroneInterfaces.OnDroneListener;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentSetupMAG extends SetupMainPanel implements OnDroneListener {

	
	private TextView textViewStep;
	private String msg;
	
	@Override
	public int getPanelLayout() {
		return R.layout.fragment_setup_mag_main;
	}

	@Override
	public SetupSidePanel getSidePanel() {
		return new FragmentSetupMAGCalibrate();
	}
	
	@Override
	public void doCalibrationStep(int step) {
		if (parentActivity.drone != null) {
			parentActivity.drone.calibrationSetup.startCalibration(2);
		}
	}
	
	

	@Override
	public void setupLocalViews(View v) {
		// TODO Auto-generated method stub
		textViewStep = (TextView) v.findViewById(R.id.textViewMAGStep);
	}

	@Override
	public void onDroneEvent(DroneEventsType event, Drone drone) {
		
			
			if (event == DroneEventsType.CALIBRATION_GYRO) {
				msg =drone.calibrationSetup.getMessage();
				textViewStep.setText(msg);
				Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
				
			}
		
	}

	
	@Override
	public void onPause() {
		super.onPause();
		if (parentActivity != null) {
			parentActivity.drone.events.removeDroneListener(this);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if (parentActivity != null) {
			parentActivity.drone.events.addDroneListener(this);
		}
	}
}
