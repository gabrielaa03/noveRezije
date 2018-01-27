package com.gabriela.mojereije.newBill;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.gabriela.mojereije.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CameraActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_DATA = "extra_data";

    @BindView(R.id.camera_view)
    SurfaceView cameraView;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, CameraActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        setReader();
    }

    private void setReader() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.PDF417)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(24.0f)
                .build();
        startReading();
    }

    private void startReading() {
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                } catch (SecurityException securityException) {
                    Log.e("CAMERA SOURCE", securityException.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        handleDetections();
    }

    private void handleDetections() {
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    cameraView.post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                            displayData(barcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }

    private void displayData(final String barcodeText) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(barcodeText);

        alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                returnBarcodeText(barcodeText);
            }
        });

        alertBuilder.setNegativeButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                startReading();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void returnBarcodeText(String barcodeText) {
        Intent extraData = new Intent();
        extraData.putExtra(KEY_EXTRA_DATA, barcodeText);
        setResult(RESULT_OK, extraData);
        finish();
    }
}

