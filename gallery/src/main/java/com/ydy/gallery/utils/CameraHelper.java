package com.ydy.gallery.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ydy
 */
public class CameraHelper {

    private static String cameraFolder = Environment.getExternalStorageDirectory().getPath() + "/DCIM/camera";
    private static File takeImageFile;

    public static void take(Fragment fragment, int requestCode) {
        createFolder(cameraFolder);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            takeImageFile = createFile(cameraFolder, "IMG_", ".jpg");
            Uri uri;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                String authorities = ProviderUtil.getFileProviderName(fragment.getContext());
                uri = FileProvider.getUriForFile(fragment.getActivity(), authorities, takeImageFile);
            } else {
                uri = Uri.fromFile(takeImageFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        fragment.startActivityForResult(takePictureIntent, requestCode);
    }

    public static File getTakeImageFile() {
        return takeImageFile;
    }

    private static File createFile(String folder, String prefix, String suffix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    public static void scanPic(Context context, File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 创建文件夹目录
     */
    private static void createFolder(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
