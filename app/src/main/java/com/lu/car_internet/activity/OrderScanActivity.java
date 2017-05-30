package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lu.car_internet.R;

import java.util.Hashtable;

import static android.R.attr.bitmap;

/**
 * Created by lu on 2017/4/29.
 */

public class OrderScanActivity extends Activity {
    private ImageView qrscan_image;
    private int QR_WIDTH =160;
    private int QR_HEIGHT= 160;
    private Bitmap bitmap;
    private ImageView one_code;
    String carid="";
    String gasolinetype="";
    String gasolinenumber="";
    String gasolinedate="";
    private ImageButton orderscan_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderscan);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        carid = bundle.getString("carid");
        gasolinetype = bundle.getString("gasolinetype");
        gasolinenumber = bundle.getString("gasolinenumber");
        gasolinedate = bundle.getString("gasolinedate");
        qrscan_image = (ImageView)findViewById(R.id.qrscan_image);
        orderscan_back = (ImageButton)findViewById(R.id.orderscan_back);
        orderscan_back.setOnClickListener(new orderscanback());
        one_code = (ImageView)findViewById(R.id.one_code);
        Toast.makeText(OrderScanActivity.this, carid+","+gasolinetype+","+gasolinenumber+","+gasolinedate, Toast.LENGTH_LONG).show();
        try {
            one_code.setImageBitmap(CreateOneDCode(carid+","+gasolinetype+","+gasolinenumber+","+gasolinedate));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        generate();
    }


    private void generate(){
        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();

            String text = carid+","+gasolinetype+","+gasolinenumber+","+gasolinedate+",";
            if (text == null || "".equals(text) || text.length() < 1) {
                return;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = null;
            try {
                martix = writer.encode(text, BarcodeFormat.QR_CODE,
                       250,250);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            Hashtable<EncodeHintType, String> hints = new
                    Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++  ) {
                for (int x = 0; x < QR_WIDTH; x++  ) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH+x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH +x] = 0xffffffff;
                    }

                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            System.out.println(Environment.getExternalStorageDirectory());

            qrscan_image.setImageBitmap(bitmap);
//            try {
//                saveMyBitmap(bitmap, "code");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Bitmap CreateOneDCode(String content) throws WriterException {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, 850, 160);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private class orderscanback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
