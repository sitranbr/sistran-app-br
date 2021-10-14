package net.sistransito.mobile.tav;

public class TavObject {
	private static TavData data;

	private TavObject() {
		data = new TavData();
	}

	public static TavData getTAVObject() {

		if (data == null)
			data = new TavData();
		
		return data;
	}

	public static void setTAVObject(TavData tcaData) {

		data = tcaData;
	}
}
