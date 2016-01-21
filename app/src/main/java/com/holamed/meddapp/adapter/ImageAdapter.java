package com.holamed.meddapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.holamed.meddapp.MainActivity;
import com.holamed.meddapp.R;

/**
 * Created by Era on 5/23/2015.
 */
public class ImageAdapter extends PagerAdapter {
    Context context;
    private int[] GalImages ;
    public ImageAdapter()
    {

    }
    public ImageAdapter(Context context){
        this.context=context;
      GalImages  = new int[]{
                R.drawable.tut_1,
                R.drawable.tut_2,
                R.drawable.tut_3,
                R.drawable.tut_4};

    }
    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.feed_item_margin);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}

