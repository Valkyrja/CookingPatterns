package org.cookingpatterns.Model;

import android.graphics.drawable.Drawable;

public class ImageAsDrawable extends ImageInfo
{
  public Object GetImage()
  {
    return Drawable.createFromPath(super.imagePath);
  }

}