package com.brody.climbers.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.brody.climbers.R;
import com.brody.climbers.utils.ImageTools;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI(){
        Bitmap bitmap = ImageTools.sampleImage(this, R.drawable.login_background);
        bitmap = ImageTools.gaosiImage(this, bitmap, 20);
        BitmapDrawable drawbale = new BitmapDrawable(this.getResources(), bitmap);
        View layoutView = findViewById(R.id.main_layout);
        layoutView.setBackground(drawbale);


        final float beginValue = 0.25f;
        final float endValue = 0.45f;

        final MagicProgressCircle ringView = findViewById(R.id.ring);
        ringView.setSmoothPercent(beginValue);
        final TextView numberView = findViewById(R.id.info_message);

        final int beginSteps = 12000;
        final int endSteps = 14000;
        numberView.setText("" + beginSteps);



        ValueAnimator valueAnimator = ValueAnimator.ofFloat(beginValue, endValue);
        valueAnimator.setDuration(800);
        valueAnimator.setStartDelay(400);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float)animation.getAnimatedValue();
                ringView.setSmoothPercent(curValue);

                float ratio = (curValue - beginValue) / (endValue-beginValue);
                int changeSteps = (int)((endSteps-beginSteps) * ratio);

                numberView.setText("" + (changeSteps + beginSteps));
            }
        });

    }




}
