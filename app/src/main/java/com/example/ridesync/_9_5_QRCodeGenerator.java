package com.example.ridesync;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class _9_5_QRCodeGenerator extends AsyncTask<String, Void, Bitmap> {

    private ResultReceiver receiver;

    public _9_5_QRCodeGenerator(ResultReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if (strings.length > 0) {
            String qrCodeData = strings[0];
            return generateQRCodeImage(qrCodeData);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap qrCodeImage) {
        Bundle resultData = new Bundle();
        resultData.putParcelable("qrCodeImage", qrCodeImage);
        receiver.send(0, resultData);
    }

    private Bitmap generateQRCodeImage(String data) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

