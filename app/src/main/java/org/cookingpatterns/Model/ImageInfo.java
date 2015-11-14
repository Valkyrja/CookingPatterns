package org.cookingpatterns.Model;

import java.io.Serializable;

public class ImageInfo implements Serializable
{
  public String imagePath;

  public ImageInfo(String path)
  {
    imagePath = path;
  }

  public Object GetImage() { return null; }

  public String GetImagePath()
    {
        return imagePath;
    }

}