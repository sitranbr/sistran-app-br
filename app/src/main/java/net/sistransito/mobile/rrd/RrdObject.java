package net.sistransito.mobile.rrd;

public class RrdObject {

	private static RrdData RRDData;

	private RrdObject() {
		RRDData = new RrdData();
	}

	public static RrdData getRRDOject() {
		return RRDData;
	}

	public static void setRRDObject(RrdData data) {
		RRDData = data;
	}

}
