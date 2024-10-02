package spiral.browser.spiral;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Device is offline", Toast.LENGTH_SHORT).show();

                }
            }
        });

        findViewById(R.id.spiralImages);
        ObjectAnimator animation = ObjectAnimator.ofFloat(findViewById(R.id.spiralImages), "rotation", 0f, 360f);
        animation.setDuration(3000); // 2 seconds
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.expand_in,R.anim.fade_out);
            }

        });


    }
}