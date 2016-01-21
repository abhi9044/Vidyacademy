package com.holamed.meddapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.holamed.meddapp.MainActivity;
import com.holamed.meddapp.R;
import com.holamed.meddapp.TutorialActivityMedd;

/**
 * Created by Era on 5/27/2015.
 */
public class ImageAdapterTutorial extends PagerAdapter {
    Context context;
    private int p;
    private int[] GalImages;
   public ImageAdapterTutorial()
   {

   }
    public ImageAdapterTutorial(Context context) {
        this.context = context;
        GalImages=new int[]{
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
        Log.d("position check 1", String.valueOf(position));
        setP(position);
        //imageView.setImageResource(GalImages[position]);

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.drawable.tut_1;
                imageView.setImageResource(resId);
                ((ViewPager) container).addView(imageView, 0);

                break;
            case 1:
                resId = R.drawable.tut_2;
                imageView.setImageResource(resId);
                ((ViewPager) container).addView(imageView, 0);

                break;
            case 2:
                resId = R.drawable.tut_3;
                imageView.setImageResource(resId);
                ((ViewPager) container).addView(imageView, 0);
                break;
            case 3:
                resId = R.drawable.tut_4;
                imageView.setImageResource(resId);
                ((ViewPager) container).addView(imageView, 0);
                break;
        }
        return imageView;
    }
    public int getP()
    {
        return p;
    }
   public void setP(int p)
   {
       this.p=p;
   }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}