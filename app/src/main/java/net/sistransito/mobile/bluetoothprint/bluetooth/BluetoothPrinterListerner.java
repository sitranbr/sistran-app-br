package net.sistransito.mobile.bluetoothprint.bluetooth;

public interface BluetoothPrinterListerner {

	void print(Object mainData, Object aitData);

	void print(Object mainData, Object aitData, String copy);

}
