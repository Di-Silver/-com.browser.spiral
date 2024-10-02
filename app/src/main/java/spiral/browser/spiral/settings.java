package spiral.browser.spiral;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckedTextView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class settings extends AppCompatActivity {

    Spinner spinner;
    CheckedTextView notification,safeBrowsing,history;
    RatingBar ratingBar;
    Data data= new Data(getApplicationContext());
    Boolean state = false;
    Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.spinner);
        notification = findViewById(R.id.notifications);
        safeBrowsing = findViewById(R.id.secureBrowsing);
        history = findViewById(R.id.history);
        ratingBar =  findViewById(R.id.ratingbar);
        float rates = ratingBar.getRating();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (notification.isChecked()){
                    state = true;
                    data.soundNotifications(state);
                } else{
                    data.soundNotifications(state);
                }

                if (safeBrowsing.isChecked()) {
                    state = true;
                    data.browsingState(state);
                    Toast.makeText(getApplicationContext(), "safe browsing is enabled", Toast.LENGTH_SHORT).show();
                } else {
                    data.browsingState(state);
                }

                if (history.isChecked()) {
                    state = true;
                    data.browsingState(state);
                    Toast.makeText(getApplicationContext(), "saving browser ain't safe", Toast.LENGTH_SHORT).show();
                }else {
                    data.browsingState(state);
                }


                if (rates > 1.0){
                    Toast.makeText(getApplicationContext(), "Thanks for rating us", Toast.LENGTH_SHORT).show();
                }
            }
        };

        handler.postDelayed(runnable,1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}