package com.example.kianfar.producthunt_danakianfar.content;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageHelper {

    private Context context;
//    private Image2DiskTarget diskTarget;


    public ImageHelper(Context context) {
        this.context = context;
    }


//    public void imageUrl2Disk(String imageurl, String userid) {
//        diskTarget = new Image2DiskTarget(context, userid);
//
//        Picasso.with(context)
//                .load(imageurl)
//                .fit()
//                .tag(context)
//                .into(diskTarget);
//    }


    public void write2disk(final Bitmap bitmap, final String userid) {

        Log.d("ImageHelper", "Writing bitmap to disk, id: " + userid);

        // save image to disk
        new Thread(new Runnable() {
            @Override
            public void run() {

                File file = new File(DataPool.imagePath + userid + ".jpg");
                try {
                    file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
