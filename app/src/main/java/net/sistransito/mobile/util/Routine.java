package net.sistransito.mobile.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PlateSearchDatabaseHelper;
import net.sistransito.mobile.plate.data.DataFromPlate;

import java.io.ByteArrayOutputStream;

public class Routine {

    public static void saveAitGeneration(AitData aitData, Context context){

        boolean ifPlateExist = false;
        Cursor cursor = (DatabaseCreator.getSearchPlateDatabaseAdapter(context)).getPlateCursor();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int size = cursor.getCount();
            String dataPlate = aitData.getPlate();
            for (int i=0; i<size; i++){
                String placa = cursor.getString(cursor.getColumnIndex(PlateSearchDatabaseHelper.PLATE));
                if (dataPlate.equalsIgnoreCase(placa)) {
                    ifPlateExist = true;
                    break;
                }
                cursor.moveToNext();
            }
        }
        if (!ifPlateExist) {
            DataFromPlate dataPlate
                    = new DataFromPlate(context);
            dataPlate.setPlate(aitData.getPlate());
            dataPlate.setModel(aitData.getVehicleModel());
            (DatabaseCreator.getSearchPlateDatabaseAdapter(context))
                    .insertPlacaSearchData(dataPlate);
        }
        DatabaseCreator.getAitDatabaseAdapter(context)
                .setAitData(aitData);

    }

    public static void openKeyboard(View view, Context context) {
        //View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }

    public static void closeKeyboard(View view, Context context) {
        //View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showAlert(String msg, Context context){

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);

        /*View view = toast.getView();

        //To change the Background of Toast
        view.setBackgroundColor(Color.rgb(252,138,0));
        TextView text = (TextView) view.findViewById(android.R.id.message);

        //Shadow of the Of the Text Color
        text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        text.setTextColor(Color.WHITE);
        //text.setTextSize(Integer.valueOf(getResources().getString(R.string.text_size)));
        toast.show();

        View view = toast.getView();
        view.getBackground().setColorFilter(Color.rgb(252,138,0), PorterDuff.Mode.SRC_IN);
        TextView text = view.findViewById(R.id.tv_toast);
        text.setTextColor(Color.rgb(255,255,255));*/
        toast.show();

        //Toast.makeText(context, msgs, Toast.LENGTH_LONG).show();
    }

    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
