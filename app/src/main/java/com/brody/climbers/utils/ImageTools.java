package com.brody.climbers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

public class ImageTools {

    public static int calculateInSampleSize(BitmapFactory.Options op, int reqWidth,
                                     int reqheight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;

        Log.d("abc", "originalWidth = " + originalWidth);
        Log.d("abc", "originalHeight = " + originalHeight);
        int inSampleSize = 1;
        if (originalWidth > reqWidth || originalHeight > reqheight) {
            int halfWidth = originalWidth / 2;
            int halfHeight = originalHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth)
                    &&(halfHeight / inSampleSize > reqheight)) {
                inSampleSize *= 2;

            }
        }
        return inSampleSize;
    }

    public static Bitmap sampleImage(Context context, int imageId){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageId, options);

        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        int scale = calculateInSampleSize(options, 40, 60);;
        Log.d("abc", "scale = " + scale);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId, options);

        return bitmap;
    }


    public static Bitmap gaosiImage(Context context,Bitmap source,int radius){

        Bitmap inputBmp = source;
        //(1)
        RenderScript renderScript =  RenderScript.create(context);


        // Allocate memory for Renderscript to work with
        //(2)
        final Allocation input = Allocation.createFromBitmap(renderScript,inputBmp);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());
        //(3)
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        //(4)
        scriptIntrinsicBlur.setInput(input);
        //(5)
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        //(6)
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        //(7)
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);
        //(8)
        renderScript.destroy();

        return inputBmp;
    }


}
