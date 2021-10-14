package net.sistransito.mobile.bluetoothprint.PrintBitmap.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

public class PrintBitmapFormat extends BasePaint {

    public enum TableCellAlign {
        LEFT, RIGHT, MIDDLE
    }

    public static final float TITLE_FONT_SIZE = 21.5f;
    public static final float PAINT_TITLE_BOLD_FONT = 21;
    public static final float SUB_TITLE_FONT_SIZE = 19;
    public static final float NORMAL_FONT = 18;
    public static final float MEDIO_FONT = 20;
    public static final float MAIOR_FONT = 25;

    public PrintBitmapFormat(Context context) {
        super(context);
    }

    public void createTitle(String title, String subTitle, String leftImagePath, String rightImagePath, float titleFontSize, float subTitleFontSize) {
        int widthHeight = 80;
        int yInit = yPosition;
        setNewLine(3);
        drawBitmap(leftImagePath, widthHeight, xPositionStart, yPosition);
        drawBitmap(rightImagePath, widthHeight, xPositionEnd - widthHeight + MARGIN_LARGE, yPosition);
        String[] mTitle = TextFormat.split(title);

        paint.setTextSize(titleFontSize);
        setPaintNormal();
        for (String mText : mTitle) {
            yPosition = drawText(mText.trim(), xPositionStart + widthHeight, xPositionEnd - widthHeight,
                    yPosition, Paint.Align.CENTER);
        }

        setNewLine(5);
        paint.setTextSize(subTitleFontSize);
        setPaintNormal();
        yPosition = drawText(subTitle, xPositionStart + widthHeight, xPositionEnd - widthHeight,
                yPosition, Paint.Align.CENTER);
        setNewLine(4);
        drawBox(new Rect(xPositionStart, yInit, xPositionEnd, yPosition));
    }

    public void createTextCaptions(String subTitle, String title, float titleFontSize) {
        int widthHeight = 100;
        int y_init = yPosition;
        setNewLine(3);

        paint.setTextSize(titleFontSize);
        setPaintBold();
        yPosition = drawText(subTitle, xPositionStart + widthHeight, xPositionEnd - widthHeight,
                yPosition, Paint.Align.CENTER);
        setNewLine(4);

        String[] mTitle = TextFormat.split(title);

        paint.setTextSize(titleFontSize);
        setPaintNormal();
        for (String mText : mTitle) {
            yPosition = drawText(mText.trim(), xPositionStart + widthHeight, xPositionEnd - widthHeight,
                    yPosition, Paint.Align.CENTER);
        }
        setNewLine(4);
        drawBox(new Rect(xPositionStart, y_init, xPositionEnd, yPosition));
    }

    public int drawText(String text, int xStart, int xEnd, int yStart, Paint.Align align) {
        int width = xEnd - xStart;
        if (Paint.Align.CENTER == align) {
            int text_width = TextFormat.getTextBoundWidth(text, paint);
            int x_increase = (width - text_width) / 2;
            xStart += x_increase;
        }
        yStart += paint.getTextSize();
        canvas.drawText(text, xStart, yStart, paint);
        return yStart;
    }

//    public void createTableIdentificacao(String nomeOrgao, String codOrgao, String numeroAit, String numeracaoAit, boolean isAdd, TableCellAlign align) {
//        if (!isAdd) {
//            y_position += TABLE_BORDER;
//        }
//        int y_int = y_position;
//        int x2 = 0;
//        if (TableCellAlign.MIDDLE == align) {
//            x2 = (int) PAGE_WIDTH / 2;
//
//        } else if (TableCellAlign.LEFT == align) {
//
//            int width_1 = Math.max(TextFormat.getTextBoundWidth(nomeOrgao, paintNormal),
//                    TextFormat.getTextBoundWidth(codOrgao, paintTitleBold));
//            x2 = width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE;
//
//        } else if (TableCellAlign.RIGHT == align) {
//            int width_1 = Math.max(TextFormat.getTextBoundWidth(numeroAit, paintNormal),
//                    TextFormat.getTextBoundWidth(numeracaoAit, paintMaior));
//            x2 = x_position_end - (width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE);
//        }
//        setNewLine(1);
//        drawText(nomeOrgao, x_text_start, x2, y_position, paintNormal, Paint.Align.LEFT);
//        y_position = drawText(numeroAit, x2 + MARGIN_LARGE, x_position_end, y_position, paintNormal, Paint.Align.LEFT);
//        setNewLine(2);
//
//        drawText(codOrgao, x_position_start, x2, y_position, paintMaior, Paint.Align.CENTER);
//        y_position = drawText(numeracaoAit, x2, x_position_end, y_position, paintMaior, Paint.Align.CENTER);
//        setNewLine(2);
//
//        drawBox(new Rect(x_position_start, y_int, x2, y_position));
//        drawBox(new Rect(x2, y_int, x_position_end, y_position));
//        System.gc();
//    }

    public void createNameTable(String title1, String value1, String title2, String value2, boolean isAdd, TableCellAlign align, float nameFont, float valuefont) {
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        int x2 = 0;
        if (TableCellAlign.MIDDLE == align) {
            x2 = PAGE_WIDTH / 2;

        } else if (TableCellAlign.LEFT == align) {
            setPaintNormal();
            paint.setTextSize(nameFont);

            int w1 = TextFormat.getTextBoundWidth(title1, paint);
            setPaintBold();
            paint.setTextSize(valuefont);

            int w2 = TextFormat.getTextBoundWidth(value1, paint);
            int width_1 = Math.max(w1, w2);

            x2 = width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE;

        } else if (TableCellAlign.RIGHT == align) {
            setPaintNormal();
            paint.setTextSize(nameFont);

            int w1 = TextFormat.getTextBoundWidth(title2, paint);
            setPaintBold();
            paint.setTextSize(valuefont);
            int w2 = TextFormat.getTextBoundWidth(value2, paint);
            int width_1 = Math.max(w1, w2);

            x2 = xPositionEnd - (width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE);
        }
        setPaintNormal();
        paint.setTextSize(nameFont);
        setNewLine(1);
        drawText(title1, xTextStart, x2, yPosition, Paint.Align.LEFT);
        yPosition = drawText(title2, x2 + MARGIN_LARGE, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(2);

        setPaintBold();
        paint.setTextSize(valuefont);
        drawText(value1, xPositionStart, x2, yPosition, Paint.Align.CENTER);
        yPosition = drawText(value2, x2, xPositionEnd, yPosition, Paint.Align.CENTER);
        setNewLine(2);

        drawBox(new Rect(xPositionStart, y_int, x2, yPosition));
        drawBox(new Rect(x2, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createStructureTable(String title_1, String value_1, String title_2, String value_2, boolean isAdd, TableCellAlign align, float titleFont, float valueFont) {
        //int widthHeight = 200;

        if (!isAdd) {
            yPosition += TABLE_BORDER; // 15 += 2 = 17;
        }

        int y_int = yPosition;
        int x2 = 0;
        int y2 = 7;

        if (TableCellAlign.MIDDLE == align) {
            x2 = PAGE_WIDTH / 2; //x2 = 576/2 = 288
        }

        setPaintNormal();
        paint.setTextSize(titleFont);
        setNewLine(1);

        drawText(title_1, xTextStart, x2, yPosition, Paint.Align.CENTER);
        yPosition = drawText(title_2, x2 + MARGIN_LARGE, xPositionEnd, yPosition, Paint.Align.CENTER);

        y2 += yPosition;

        setPaintBold();
        paint.setTextSize(valueFont);
        setNewLine(2);

        paint.setTextSize(valueFont);
        setPaintNormal();

        String[] tValue_1 = TextFormat.split(value_1);
        String[] tValue_2 = TextFormat.split(value_2);

        for (String mText : tValue_1) {
            yPosition = drawText(mText.trim(), xPositionStart, x2, yPosition, Paint.Align.CENTER);
        }

        setNewLine(1);

        for (String mText2 : tValue_2) {
            y2 = drawText(mText2.trim(), x2, xPositionEnd, y2, Paint.Align.CENTER);
        }

        setNewLine(10);

        drawBox(new Rect(xPositionStart, y_int, x2, yPosition));
        drawBox(new Rect(x2, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createTable(String title_1, String value_1, String title_2, String value_2, boolean isAdd, TableCellAlign align, float titleFont, float valueFont) {
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        int x2 = 0;
        if (TableCellAlign.MIDDLE == align) {
            x2 = PAGE_WIDTH / 2;

        } else if (TableCellAlign.LEFT == align) {

            setPaintNormal();
            paint.setTextSize(titleFont);
            int w1 = TextFormat.getTextBoundWidth(title_1, paint);

            setPaintBold();
            paint.setTextSize(valueFont);
            int w2 = TextFormat.getTextBoundWidth(value_1, paint);
            int width_1 = Math.max(w1, w2);
            x2 = width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE;

        } else if (TableCellAlign.RIGHT == align) {

            setPaintNormal();
            paint.setTextSize(titleFont);
            int w1 = TextFormat.getTextBoundWidth(title_2, paint);

            setPaintBold();
            paint.setTextSize(valueFont);
            int w2 = TextFormat.getTextBoundWidth(value_2, paint);

            int width_1 = Math.max(w1, w2);
            x2 = xPositionEnd - (width_1 + MARGIN_LARGE + MARGIN_LARGE + MARGIN_LARGE);
        }

        setPaintNormal();
        paint.setTextSize(titleFont);
        setNewLine(1);
        drawText(title_1, xTextStart, x2, yPosition, Paint.Align.LEFT);
        yPosition = drawText(title_2, x2 + MARGIN_LARGE, xPositionEnd, yPosition, Paint.Align.LEFT);

        setPaintBold();
        paint.setTextSize(valueFont);
        setNewLine(2);

        drawText(value_1, xPositionStart, x2, yPosition, Paint.Align.CENTER);
        yPosition = drawText(value_2, x2, xPositionEnd, yPosition, Paint.Align.CENTER);
        setNewLine(2);

        drawBox(new Rect(xPositionStart, y_int, x2, yPosition));
        drawBox(new Rect(x2, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createTable(String title_1, String value_1, String title_2,
                            String value_2, String title_3, String value_3, boolean isAdd, float nameFont, float valueFont) {
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        setPaintBold();
        paint.setTextSize(valueFont);
        int w11 = TextFormat.getTextBoundWidth(value_1, paint);
        int w12 = TextFormat.getTextBoundWidth(value_2, paint);
        int w13 = TextFormat.getTextBoundWidth(value_3, paint);

        setPaintNormal();
        paint.setTextSize(nameFont);
        int w1 = TextFormat.getTextBoundWidth(title_1, paint);
        int w2 = TextFormat.getTextBoundWidth(title_2, paint);
        int w3 = TextFormat.getTextBoundWidth(title_3, paint);

        int width_1 = Math.max(w1, w11);
        int width_2 = Math.max(w2, w12);
        int width_3 = Math.max(w3, w13);

        int total = width_1 + width_2 + width_3;
        double divider = (double) PAGE_WIDTH / total;
        int y_int = yPosition;
        int x2 = (int) (width_1 * divider);
        int x3 = (int) (width_2 * divider) + x2;

        setNewLine(1);
        drawText(title_1, xTextStart, x2, yPosition, Paint.Align.LEFT);
        drawText(title_2, x2 + MARGIN_LARGE, x3, yPosition, Paint.Align.LEFT);
        yPosition = drawText(title_3, x3 + MARGIN_LARGE, xPositionEnd, yPosition, Paint.Align.LEFT);

        setNewLine(2);
        setPaintBold();
        paint.setTextSize(valueFont);
        drawText(value_1, xPositionStart, x2, yPosition, Paint.Align.CENTER);
        drawText(value_2, x2, x3, yPosition, Paint.Align.CENTER);
        yPosition = drawText(value_3, x3, xPositionEnd, yPosition, Paint.Align.CENTER);
        setNewLine(2);

        drawBox(new Rect(xPositionStart, y_int, x2, yPosition));
        drawBox(new Rect(x2, y_int, x3, yPosition));
        drawBox(new Rect(x3, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createTable(String title_1, String value_1, String title_2, String value_2,
                            String title_3, String value_3, String title_4, String value_4, Boolean isAdd, float nameFont, float valueFont) {
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }

        setPaintBold();
        paint.setTextSize(valueFont);
        int w11 = TextFormat.getTextBoundWidth(value_1, paint);
        int w12 = TextFormat.getTextBoundWidth(value_2, paint);
        int w13 = TextFormat.getTextBoundWidth(value_3, paint);
        int w14 = TextFormat.getTextBoundWidth(value_4, paint);

        setPaintNormal();
        paint.setTextSize(nameFont);
        int w1 = TextFormat.getTextBoundWidth(title_1, paint);
        int w2 = TextFormat.getTextBoundWidth(title_2, paint);
        int w3 = TextFormat.getTextBoundWidth(title_3, paint);
        int w4 = TextFormat.getTextBoundWidth(title_4, paint);

        int width_1 = Math.max(w1, w11);
        int width_2 = Math.max(w2, w12);
        int width_3 = Math.max(w3, w13);
        int width_4 = Math.max(w4, w14);

        int total = width_1 + width_2 + width_3 + width_4;
        double divider = (double) PAGE_WIDTH / total;
        int y_int = yPosition;
        int x2 = (int) (width_1 * divider);
        int x3 = (int) (width_2 * divider) + x2;
        int x4 = (int) (width_3 * divider) + x3;

        setNewLine(1);
        drawText(title_1, xTextStart, x2, yPosition, Paint.Align.LEFT);
        drawText(title_2, x2 + MARGIN_LARGE, x3, yPosition, Paint.Align.LEFT);
        drawText(title_3, x3 + MARGIN_LARGE, x4, yPosition, Paint.Align.LEFT);
        yPosition = drawText(title_4, x4 + MARGIN_LARGE, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(2);

        setPaintBold();
        paint.setTextSize(valueFont);
        drawText(value_1, xPositionStart, x2, yPosition, Paint.Align.CENTER);
        drawText(value_2, x2, x3, yPosition, Paint.Align.CENTER);
        drawText(value_3, x3, x4, yPosition, Paint.Align.CENTER);
        yPosition = drawText(value_4, x4, xPositionEnd, yPosition, Paint.Align.CENTER);
        setNewLine(2);

        drawBox(new Rect(xPositionStart, y_int, x2, yPosition));
        drawBox(new Rect(x2, y_int, x3, yPosition));
        drawBox(new Rect(x3, y_int, x4, yPosition));
        drawBox(new Rect(x4, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void setNewLine(int number) {
        yPosition += MARGIN_SMALL * number;
    }

    public void drawBox(Rect boxRect) {
        Paint paintRect = new Paint(Color.WHITE);
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setStrokeWidth(TABLE_BORDER);
        paintRect.setPathEffect(new DashPathEffect(new float[]{12, 0}, 0));
        canvas.drawRect(boxRect, paintRect);
        System.gc();
    }

    public void createSignatureQuotes(String s1, String s2, boolean isAdd, float fontSize) {
        setPaintNormal();
        paint.setTextSize(fontSize);
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        setNewLine(1);
        yPosition = drawText(s1, xTextStart, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(1);
        createQuotes(s2, false, fontSize);
        setNewLine(8);
        drawBox(new Rect(xPositionStart, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createObservationQuotes(String s1, String s2, boolean isAdd, float fontSize) {
        setPaintNormal();
        paint.setTextSize(fontSize);
        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        setNewLine(1);
        yPosition = drawText(s1, xTextStart, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(1);
        createQuotes(s2, false, fontSize);
        setNewLine(20);
        drawBox(new Rect(xPositionStart, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createQuotes(String s1, String s2, boolean isAdd, boolean isBold, float fontSize) {

        if (isBold) {
            setPaintBold();
        } else {
            setPaintNormal();
        }
        paint.setTextSize(fontSize);

        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        setNewLine(1);
        yPosition = drawText(s1, xTextStart, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(1);
        createQuotes(s2, false, fontSize);
        setNewLine(1);
        drawBox(new Rect(xPositionStart, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createQuotes(String s1, String s2, boolean isAdd, boolean isBold, float fontSize, float fontSizes) {

        if (isBold) {
            setPaintBold();
        } else {
            setPaintNormal();
        }
        paint.setTextSize(fontSize);

        if (!isAdd) {
            yPosition += TABLE_BORDER;
        }
        int y_int = yPosition;
        setNewLine(1);
        yPosition = drawText(s1, xTextStart, xPositionEnd, yPosition, Paint.Align.LEFT);
        setNewLine(1);
        createQuotes(s2, false, fontSizes);
        setNewLine(1);
        drawBox(new Rect(xPositionStart, y_int, xPositionEnd, yPosition));
        System.gc();
    }

    public void createQuotes(String s, float fontSize) {
        createQuotes(s, Paint.Align.LEFT, fontSize);
    }

    public void createQuotes(String s, boolean isLineAdd, float fontSize) {
        createQuotes(s, Paint.Align.LEFT, true, isLineAdd, fontSize);  //bold nome do condutor
    }

    public void createQuotes(String s, Paint.Align align, float fontSize) {
        createQuotes(s, align, false, true, fontSize);
    }

    public void createQuotes(String s, Paint.Align align, boolean isBold, boolean isLineAdd, float fontSize) {

        if (isBold) {
            setPaintBold();
        } else {
            setPaintNormal();
        }
        paint.setTextSize(fontSize);

        setNewLine(1);
        if (isLineAdd) yPosition += MARGIN_SMALL;
        List<CharSequence> mTitle = TextFormat.getTextLine(s, paint, xPositionEnd - xTextStart);
        for (CharSequence mText : mTitle) {
            yPosition = drawText(mText.toString(), xTextStart, xPositionEnd,
                    yPosition, align);
            setNewLine(1);
        }
        if (isLineAdd) yPosition += MARGIN_SMALL;
    }
}
