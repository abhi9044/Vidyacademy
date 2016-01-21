package com.holamed.meddapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class ImagePreview extends ActionBarActivity {

    Intent i;
    ImageView image;
    String imagePath;
    boolean isImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);



        image = (ImageView) findViewById(R.id.image);

        i = getIntent();
        imagePath = i.getStringExtra("filePath");
        isImage = i.getBooleanExtra("isImage", true);
        if (imagePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage,imagePath);
            // uploading the file to server

        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void previewMedia(boolean isImage,String filePath) {
        // Checking whether captured media is image or video
        if (isImage) {
            image.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            image.setImageBitmap(bitmap);
        } else {
            image.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
    }

    public void proceed(View v){
        Intent pr = new Intent(ImagePreview.this, Registration.class);
        pr.putExtra("filePath", imagePath);
        Log.d("phase1", imagePath);
        pr.putExtra("isImage", isImage);
        pr.putExtra("is","phar");
        AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPEPHARMACY);

        startActivity(pr);
        finish();
    }

    public void discard(View v){
        onBackPressed();
        finish();

    }

}