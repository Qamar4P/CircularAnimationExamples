package com.qamar4p.circularanimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieImageAsset;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    private LottieAnimationView animationView;
    int imageCounter = 0;
    private ImageView animationImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        animationView = findViewById(R.id.animation_view);
        animationImgView = findViewById(R.id.animation_img_view);
//        final LottieDrawable drawable = new LottieDrawable();
//        drawable.setImagesAssetsFolder("demo");
//        animationImgView.setImageDrawable(drawable);
//        animationImgView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                drawable.playAnimation();
//            }
//        },1000);
//        drawable.playAnimation();
        animationView.setImageAssetsFolder("demo");
//        animationView.playAnimation();
//        animationView.setImageAssetDelegate(new ImageAssetDelegate() {
//            @Override
//            public Bitmap fetchBitmap(LottieImageAsset asset) {
//                try {
//                    if(imageCounter++>25) imageCounter = 0;
//                    return BitmapFactory.decodeStream(getAssets().open("img_"+imageCounter+".gif"));
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                return null;
//            }
//        });
    }
}
