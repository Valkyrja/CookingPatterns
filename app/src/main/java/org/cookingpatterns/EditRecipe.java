package org.cookingpatterns;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditRecipe extends AppCompatActivity
{
    public static final int PICTURE_SELECTED = 1;

    private TextView Name;
    private TextView Categoty;
    private TextView Time;
    private TextView Rating;
    private TextView Portion;

    private ImageView Picture;
    private LinearLayout Ingredients;
    private TextView Preparation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        Name = (TextView) this.findViewById(R.id.recipename);
        Categoty = (TextView) this.findViewById(R.id.category);
        Time = (TextView) this.findViewById(R.id.time);
        Rating = (TextView) this.findViewById(R.id.rating);
        Portion = (TextView) this.findViewById(R.id.portion);

        Picture = (ImageView) this.findViewById(R.id.picture);
        Ingredients = (LinearLayout) this.findViewById(R.id.listofingredients);
        Preparation = (TextView) this.findViewById(R.id.preparation);

		//http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically/2636538#2636538
        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_SELECTED);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_SELECTED) {
                Uri selectedImageUri = data.getData();
                getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri)
    {
        // just some safety built in
        if( uri == null ) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
}
