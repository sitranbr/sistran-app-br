package net.sistransito.mobile.bluetoothprint.PrintBitmap.base;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class TextFormat {

//    public static List<String> getFormatText(String text, Paint mPaint, int width) {
//        String SPACE = " ";
//        List<String> texts = new ArrayList<>();
//        int textBoundWidth = getTextBoundWidth(text, mPaint);
//        if (textBoundWidth > width) {
//            String[] words = text.split("\\s+");
//
//            for (int i = 0; i < words.length; i++) {
//                words[i] = words[i].replaceAll("[^\\w]", "");
//            }
//
//            String temp_print_string = "";
//            for (int i = 0; i < words.length; i++) {
//
//                temp_print_string += words[i] + SPACE;
//                textBoundWidth = getTextBoundWidth(text, mPaint);
//
//                if (textBoundWidth > width) {
//                    String print_string =
//                            temp_print_string.substring(0, temp_print_string.length() - (words[i] + SPACE).length());
//                    texts.add(print_string);
//                    temp_print_string = "";
//                    --i;
//                } else if (i == (words.length - 1)) {
//                    texts.add(temp_print_string.trim());
//                }
//            }
//        } else {
//            texts.add(text.trim());
//        }
//        return texts;
//    }

    public static int getTextBoundWidth(String text, Paint mPaint) {
        Rect textBound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), textBound);
        return textBound.width();
    }

    public static String[] split(String text) {
        return text.split("\\n");
    }

    public static List<CharSequence> getTextLine(String text, Paint mPaint, int width) {
        List<CharSequence> mtext = new ArrayList<>();
        String[] words = text.split("\\s+");
        List<CharSequence> mListTemp = new ArrayList<>();
        int i;
        for (i = 0; i < words.length; i++) {
            if (width < getTextBoundWidth(getListToChar(mListTemp).toString(), mPaint)) {
                int size = mListTemp.size() - 1;
                CharSequence last = mListTemp.get(size);
                mListTemp.remove(size);
                mtext.add(getListToChar(mListTemp));
                mListTemp.clear();
                mListTemp.add(last);
                --i;
            } else {
                CharSequence s = words[i] + " ";
                mListTemp.add(s);
            }
        }
        mtext.add(getListToChar(mListTemp));
        return mtext;
    }

    private static StringBuilder getListToChar(List<CharSequence> list) {
        StringBuilder builder = new StringBuilder();
        for (CharSequence mChar : list) {
            builder.append(mChar);
        }
        return builder;
    }
}
