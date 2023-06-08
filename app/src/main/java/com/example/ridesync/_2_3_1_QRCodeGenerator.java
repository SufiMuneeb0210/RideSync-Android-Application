package com.example.ridesync;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class _2_3_1_QRCodeGenerator extends AppCompatActivity {

    Button btngenerateQR;
    ImageView ImgQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_231_qrcode_generator);
      //  btngenerateQR = findViewById(R.id.btnLogin);
      //  ImgQR = findViewById(R.id.LoginUpperWave);

        btngenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQR();
            }
        });
    }


    private void generateQR()
    {

//        MultiFormatWriter writer = new MultiFormatWriter();
//        try
//        {
//            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,600,600);
//            BarcodeEncoder encoder = new BarcodeEncoder();
//            Bitmap bitmap = encoder.createBitmap(matrix);
//            ImgQR.setImageBitmap(bitmap);
//
//        } catch (WriterException e)
//        {
//            e.printStackTrace();
//        }
    }
}