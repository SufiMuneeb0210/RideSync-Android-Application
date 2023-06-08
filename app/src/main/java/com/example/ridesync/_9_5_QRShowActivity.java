package com.example.ridesync;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

public class _9_5_QRShowActivity extends AppCompatActivity {

    static ImageView imageView;
    _9_ModelPassData passData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_95_qrshow);
        imageView = findViewById(R.id.qrimage);
        Gson gson = new Gson();
        String json = getIntent().getStringExtra("data");
        passData = gson.fromJson(json, _9_ModelPassData.class);
        generateQRCode();

    }
    public ResultReceiver qrCodeResultReceiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 0 && resultData != null) {
                Bitmap qrCodeImage = resultData.getParcelable("qrCodeImage");
                // Do something with the generated QR code image
                String str = qrCodeImage.toString();
                Log.d("QR",str);
                imageView.setImageBitmap(qrCodeImage);
            }
        }
    };

    public String generateQRCodeData() {

        StringBuilder qrText = new StringBuilder();
        qrText.append("Name: ").append(passData.get_passHoldername()).append("\n");
        qrText.append("Email: ").append(passData.get_passHolderEmail()).append("\n");
        qrText.append("Dated: ").append(passData.get_passDate()).append("\n");
        qrText.append("Month: ").append(passData.get_passDays()).append("\n");
        qrText.append("Payment: ").append(passData.get_passPayment()).append("\n");
        qrText.append("BusNumber: ").append(passData.get_busNumber()).append("\n");
        return qrText.toString();
    }
    public void generateQRCode() {
        new _9_5_QRCodeGenerator(qrCodeResultReceiver).execute(generateQRCodeData());
    }
}