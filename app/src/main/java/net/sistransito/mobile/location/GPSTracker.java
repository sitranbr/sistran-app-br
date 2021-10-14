package net.sistransito.mobile.location;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class GPSTracker extends Service implements LocationListener {
	private LocationManager locationManager;
	private Context mContext;
	private Location myLocation;

	public GPSTracker(Context context) {
		this.mContext = context;

	}

	public boolean isGpsEnable() {
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

			Location gpsLocation = null;
			Location networkLocation = null;

			locationManager.removeUpdates(this);
			gpsLocation = requestUpdateFromProvider(LocationManager.GPS_PROVIDER);
			networkLocation = requestUpdateFromProvider(LocationManager.NETWORK_PROVIDER);

			if (gpsLocation != null && networkLocation != null) {
				myLocation = getBetterLocation(gpsLocation, networkLocation);
			} else if (gpsLocation != null) {
				myLocation = gpsLocation;
			} else if (networkLocation != null) {
				myLocation = networkLocation;
			}

			return true;

		} else {

			return false;
		}

	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	public Location getLocation() {
		return myLocation;
	}

	public boolean canGetLocation() {
		if (isGpsEnable())
			return true;
		else {
			showSettingsAlert();
			return false;
		}
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private Location requestUpdateFromProvider(String provider) {
		Location location = null;
		if (locationManager.isProviderEnabled(provider)) {

			locationManager.requestLocationUpdates(provider, 1, 10, this);
			location = locationManager.getLastKnownLocation(provider);

		}
		return location;
	}

	private Location getBetterLocation(Location newLocation, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > 60000;
		boolean isSignificantlyOlder = timeDelta < 60000;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;

		// Determine location quality using a combination of timelines and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		}
		return currentBestLocation;
	}

}
