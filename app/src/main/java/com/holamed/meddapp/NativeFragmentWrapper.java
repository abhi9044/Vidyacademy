package com.holamed.meddapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Era on 7/26/2015.
 */
@SuppressLint("ValidFragment")
public class NativeFragmentWrapper extends android.support.v4.app.Fragment {
    private final Fragment nativeFragment;

    public NativeFragmentWrapper(Fragment nativeFragment) {
        this.nativeFragment = nativeFragment;
        Log.d("fail", "5");
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Log.d("fail","6");
        nativeFragment.startActivityForResult(intent, requestCode);
        Log.d("fail","7");
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        Log.d("fail","8");
        nativeFragment.onActivityResult(requestCode, resultCode, data);
        Log.d("fail","9");
    }
}
