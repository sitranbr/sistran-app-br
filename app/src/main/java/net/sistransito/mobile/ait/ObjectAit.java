package net.sistransito.mobile.ait;

public class ObjectAit {

	private static AitData aitData;

	private ObjectAit() {
		aitData = new AitData();
	}

	public static AitData getAitData() {
		return aitData;
	}

	public static void setAitData(AitData data) {
		aitData = data;
	}

}
