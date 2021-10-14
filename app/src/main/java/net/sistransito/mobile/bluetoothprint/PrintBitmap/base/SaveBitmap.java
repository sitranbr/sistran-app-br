package net.sistransito.mobile.bluetoothprint.PrintBitmap.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import net.sistransito.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SaveBitmap {
    public enum FileExtension {
        PNG(".png");
        private String type;

        FileExtension(String type) {
            this.type = type;
        }
    }

    private Context context;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public SaveBitmap(Context context) {
        this.context = context;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean deleteFile() {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean delete = file.delete();
                return delete;
            }
        }
        return false;
    }

    protected void saveImage(Bitmap finalBitmap) {
        try {
            OutputStream out = getFileOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OutputStream getFileOutputStream() {
        OutputStream out = null;
        File file = getFile(FileExtension.PNG);
        setFilePath(file.getPath());
        try {
            out = new FileOutputStream(file);
        } catch (Exception ignored) {
        }
        return out;
    }

    private File getFile(FileExtension extension) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + context.getString(R.string.folder_app));
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fName = System.currentTimeMillis() + extension.type;
        File file = new File(myDir, fName);
        if (file.exists()) {
            file.delete();
        }
        filePath = file.getPath();
        return file;
    }
}

