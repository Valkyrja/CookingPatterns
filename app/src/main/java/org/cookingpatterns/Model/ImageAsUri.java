package org.cookingpatterns.Model;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ImageAsUri extends ImageInfo
{
    public ImageAsUri(String path) {
        super(path);
    }

    public Object GetImage() {
        return Uri.parse(imagePath);
    }

}