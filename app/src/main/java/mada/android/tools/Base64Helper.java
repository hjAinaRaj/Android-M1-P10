package mada.android.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Base64Helper {

    public static String removeB64Head (String base64){
        String[] splited = base64.split(";base64");
        if(splited.length == 1) return base64;
        return splited[1];
    }
        public static Bitmap decodeBase64ToBitmap(String base64Image) {
            base64Image = removeB64Head(base64Image);
            byte[] bt = Base64.decode(base64Image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bt, 0, bt.length);
        }
}
