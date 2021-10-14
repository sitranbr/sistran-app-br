package net.sistransito.mobile.bluetoothprint.PrintBitmap;

import android.content.Context;
import android.graphics.Bitmap;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.BasePrintBitmap;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.PrintBitmapFormat;
import net.sistransito.mobile.tca.TcaData;

/**
 * Created by GAZI_RIMON on 8/15/2016.
 */
public class TcaPrintBitmap extends BasePrintBitmap {
    private AitData aitData;
    private TcaData tcaData;

    public TcaPrintBitmap(Context context, TcaData tcaData, AitData aitData) {
        super(context);
        this.aitData = aitData;
        this.tcaData = tcaData;
    }

    @Override
    public Bitmap getBitmap() {
        PrintBitmapFormat bitmapFormat = new PrintBitmapFormat(context);


        // here you can design like as the autode print dada oK? ok

        bitmapFormat.printDocumentClose();
        bitmapFormat.saveBitmap();
        return bitmapFormat.getPrintBitmap();
    }
}

