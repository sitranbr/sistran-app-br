package net.sistransito.library.printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class PrinterActivity extends AppCompatActivity {
	// android built in classes for bluetooth operations
	public BluetoothAdapter mBluetoothAdapter;
	public BluetoothSocket mmSocket;
	public BluetoothDevice mmDevice;

	public OutputStream mmOutputStream;
	public InputStream mmInputStream;
	public Thread workerThread;
	public byte[] readBuffer;
	public int readBufferPosition;
	public int counter;
	public volatile boolean stopWorker;

	// This will find a bluetooth printer device
	public void findBT() {

		try {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

			if (mBluetoothAdapter == null) {
				showMessage("Dispositivo bluetooth n�o encontrado");
			}

			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBluetooth = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.startActivityForResult(enableBluetooth, 0);
			}

			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {

					// MPT-III is the name of the bluetooth printer device
					if (device.getName().equals("MPT-III")) {
						mmDevice = device;
						break;
					} else {
						showMessage("No printer device found");
					}
				}
			}
			showMessage("Bluetooth Device Found");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Tries to open a connection to the bluetooth printer device
	public void openBT() throws IOException {
		try {
			// Standard SerialPortService ID
			UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
			mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
			mBluetoothAdapter.cancelDiscovery();
			mmSocket.connect();
			mmOutputStream = mmSocket.getOutputStream();
			mmInputStream = mmSocket.getInputStream();
			beginListenForData();
			showMessage("Bluetooth Opened");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// After opening a connection to bluetooth printer device,
	// we have to listen and check if a data were sent to be printed.
	public void beginListenForData() {
		try {
			final Handler handler = new Handler();

			// This is the ASCII code for a newline character
			final byte delimiter = 10;

			stopWorker = false;
			readBufferPosition = 0;
			readBuffer = new byte[1024];

			workerThread = new Thread(new Runnable() {
				public void run() {
					while (!Thread.currentThread().isInterrupted()
							&& !stopWorker) {

						try {

							int bytesAvailable = mmInputStream.available();
							if (bytesAvailable > 0) {
								byte[] packetBytes = new byte[bytesAvailable];
								mmInputStream.read(packetBytes);
								for (int i = 0; i < bytesAvailable; i++) {
									byte b = packetBytes[i];
									if (b == delimiter) {
										byte[] encodedBytes = new byte[readBufferPosition];
										System.arraycopy(readBuffer, 0,
												encodedBytes, 0,
												encodedBytes.length);
										final String data = new String(
												encodedBytes, "US-ASCII");
										readBufferPosition = 0;

										handler.post(new Runnable() {
											public void run() {
												showMessage(data);
											}
										});
									} else {
										readBuffer[readBufferPosition++] = b;
									}
								}
							}

						} catch (IOException ex) {
							stopWorker = true;
						}

					}
				}
			});

			workerThread.start();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This will send data to be printed by the bluetooth printer
	 */
	public void sendData(String printData) throws IOException {
		try {

			// the text typed by the user
			String msg = printData;
			msg += "\n";

			mmOutputStream.write(msg.getBytes());

			// tell the user data were sent
			showMessage("Data Sent");

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Close the connection to bluetooth printer.
	public void closeBT() throws IOException {
		try {
			stopWorker = true;
			mmOutputStream.close();
			mmInputStream.close();
			mmSocket.close();
			showMessage("Bluetooth Closed");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showMessage(String mgs) {
		Toast.makeText(this, mgs, Toast.LENGTH_LONG).show();
	}

}
