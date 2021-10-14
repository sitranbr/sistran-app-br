package net.sistransito.mobile.rrd.lister;


import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.rrd.RrdData;

public interface RrdPrintListener {

	void print(RrdData RRDData, AitData aitData);

}
