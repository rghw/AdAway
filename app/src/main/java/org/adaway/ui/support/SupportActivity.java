package org.adaway.ui.support;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.adaway.R;

import static android.content.Intent.ACTION_VIEW;

/**
 * This class is an activity for users to show their supports to the project.
 *
 * @author Bruce BUJON (bruce.bujon(at)gmail(dot)com)
 */
public class SupportActivity extends AppCompatActivity {
    /**
     * The support link.
     */
    public static final String SUPPORT_LINK = "https://paypal.me/BruceBUJON";
    /**
     * The sponsorship link.
     */
    public static final String SPONSORSHIP_LINK = "https://github.com/sponsors/PerfectSlayer";

    public static void animateHeart(ImageView heartImageView) {
        PropertyValuesHolder growScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 1.2F);
        PropertyValuesHolder growScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 1.2F);
        Animator growAnimator = ObjectAnimator.ofPropertyValuesHolder(heartImageView, growScaleX, growScaleY);
        growAnimator.setDuration(200);
        growAnimator.setStartDelay(2000);

        PropertyValuesHolder shrinkScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2F, 1F);
        PropertyValuesHolder shrinkScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2F, 1F);
        Animator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(heartImageView, shrinkScaleX, shrinkScaleY);
        growAnimator.setDuration(400);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationSet.start();
            }
        });
        animationSet.playSequentially(growAnimator, shrinkAnimator);
        animationSet.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);

        View view = findViewById(R.id.supportRootLayout);
        ImageView heartImageView = findViewById(R.id.headerImageView);
        animateHeart(heartImageView);
        bindPaypal(view);
        bindSponsorShip(view);
    }

    private void bindPaypal(View view) {
        bindLink(view, R.id.headerImageView, SUPPORT_LINK);
        bindLink(view, R.id.paypalCardView, SUPPORT_LINK);
    }

    private void bindSponsorShip(View view) {
        bindLink(view, R.id.sponsorshipCardView, SPONSORSHIP_LINK);
    }

    private void bindLink(View rootView, int viewId, String url) {
        View view = rootView.findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent browserIntent = new Intent(ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }
}
