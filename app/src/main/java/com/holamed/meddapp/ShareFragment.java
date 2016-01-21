package com.holamed.meddapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShareFragment extends Fragment {
    // private ArrayList<FaqObject> faqobjlist;
    public ShareFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


        //intializing animaion

        View rootView = inflater.inflate(R.layout.fragment_share, container, false);


        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Medd helps me book all my medical tests through my smartphone, and get my reports in one place!\n" +
                "Download Android app now and get great discounts: bit.ly/medd-app";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));


/*      Bitmap b =BitmapFactory.decodeResource(getResources(),R.drawable.shareimage);
        Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
        share.setType("image/*");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                b, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
share.putExtra(Intent.EXTRA_TEXT,"share this");
        startActivity(Intent.createChooser(share, "Select"));
*/
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        return rootView;
    }
}


