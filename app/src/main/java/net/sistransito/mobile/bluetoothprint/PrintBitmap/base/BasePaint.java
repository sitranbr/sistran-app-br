package net.sistransito.mobile.bluetoothprint.PrintBitmap.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

public class BasePaint {

    public final int PAGE_WIDTH = 576, PAGE_HEIGHT = 3200;
    public final int MARGIN_SMALL = 5;
    public final int MARGIN_LARGE = 10;
    public final int TABLE_BORDER = 2;
    private Context context;
    public Paint paint;
    protected Bitmap printBitmap;
    public int yPosition, xPositionStart, xTextStart, xPositionEnd;
    public Canvas canvas;

    public BasePaint(Context context) {
        this.context = context;

        yPosition = MARGIN_LARGE + MARGIN_SMALL;
        xPositionStart = MARGIN_LARGE;
        xPositionEnd = PAGE_WIDTH - MARGIN_LARGE;
        xTextStart = xPositionStart + MARGIN_SMALL;

        printBitmap = getRawBitmap();
        canvas = new Canvas(printBitmap);
        paint = new Paint(Color.BLACK);
        setPaintNormal();
        //        paintTitle.setTextSize(17);
//
//        paintTitleBold = new Paint(Color.BLACK);
//        paintTitleBold.setTextSize(19);
//        paintTitleBold.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
//
//        paintSubTitleBold = new Paint(Color.BLACK);
//        paintSubTitleBold.setTextSize((float) 17.5);
//        paintSubTitleBold.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
//
//        paintNormal = new Paint(Color.BLACK);
//        paintNormal.setTextSize(15);
//        paintNormal.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//                Typeface.NORMAL));
//
//        paintMedio = new Paint(Color.BLACK);
//        paintMedio.setTextSize(19);
//        paintMedio.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//                Typeface.NORMAL));
//
//        paintMaior = new Paint(Color.BLACK);
//        paintMaior.setTextSize(25);
//        paintMaior.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//                Typeface.BOLD));

    }

    public void setPaintBold() {
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF,
                Typeface.BOLD));

    }

    public void setPaintNormal() {
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF,
                Typeface.NORMAL));

    }

    public Context getContext() {
        return context;
    }

    public Bitmap getBitmapFromAsset(String filePath, int widthHeight) {
        AssetManager assetManager = getContext().getAssets();
        InputStream stream;
        Bitmap bitmap = null;
        try {
            stream = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(stream);
        } catch (IOException ignored) {
        }
        return scaleDown(bitmap, true, widthHeight);
    }

    public Bitmap scaleDown(Bitmap realImage,
                            boolean filter, int widthHeight) {
        float ratio = Math.min(
                (float) widthHeight / realImage.getWidth(),
                (float) widthHeight / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    private Bitmap getRawBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(PAGE_WIDTH, PAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        return bitmap;
    }

    public Bitmap getPrintBitmap() {
        return printBitmap;
    }

    public void saveBitmap() {
        SaveBitmap saveBitmap = new SaveBitmap(context);
        saveBitmap.saveImage(printBitmap);
    }

    public void drawBitmap(String fileName, int widthHeight, int left, int top) {
        widthHeight = widthHeight - 4 * MARGIN_SMALL;
        left += MARGIN_SMALL;
        top += MARGIN_SMALL;
        Bitmap bitmap = getBitmapFromAsset(fileName, widthHeight);
        canvas.drawBitmap(bitmap, left, top, new Paint());
    }

    private Bitmap getFinalBitmap() {
        Bitmap bitmapReturn = Bitmap.createBitmap(printBitmap.getWidth(), yPosition,
                Bitmap.Config.RGB_565);
        Canvas g = new Canvas(bitmapReturn);
        g.drawColor(Color.WHITE);
        g.drawBitmap(printBitmap, 0, 0, new Paint());
        return bitmapReturn;
    }

    public void printDocumentClose() {
        printBitmap = getFinalBitmap();
    }
}
