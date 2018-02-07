package com.example.niden.cellwatchsharing.controllers;


import android.annotation.SuppressLint;
import android.os.Environment;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by niden on 31-Jan-18.
 */

public class Zip {
    private final File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
            "CellWatchZip");

    public String putImagesToZip(String zippath,ArrayList<String> allFiles, String zipFileName) throws IOException, ZipException {

        @SuppressLint("SimpleDateFormat") String timeStampOfZipFile = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        mediaStorageDir.mkdirs();
        zippath = mediaStorageDir.getAbsolutePath() + "/" + timeStampOfZipFile + ".zip";

        if (new File(zippath).exists()) {
           new File(zippath).delete();
        }

        ZipFile zipFile = new ZipFile(zippath);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipParameters.setPassword("Reset");

        if (allFiles.size() > 0) {
            for (String fileName : allFiles) {

                File file = new File(fileName);
                zipFile.addFile(file, zipParameters);


            }
        }

        return zippath;
    }


}
