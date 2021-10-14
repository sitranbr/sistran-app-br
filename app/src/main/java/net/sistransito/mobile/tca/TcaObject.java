package net.sistransito.mobile.tca;

public class TcaObject {
	private static TcaData TCAData;

	private TcaObject() {
		TCAData = new TcaData();
	}

	public static TcaData getTCAOject() {
		return TCAData;
	}

	public static void setTCAObject(TcaData data) {
		TCAData = data;
	}
}
