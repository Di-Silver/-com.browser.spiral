package spiral.browser.spiral;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class home extends AppCompatActivity {

    Handler handler = new Handler();
    EditText input;
    FloatingActionButton monitor,paste;
    Runnable checkInternetRunnable,checkClipboardRunnable;
    private boolean doubleBackToExitPressedOnce = false;
    private String lastClipboardText = "";
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View view = findViewById(R.id.main);
       input = findViewById(R.id.input);
       monitor = findViewById(R.id.changes);
       paste = findViewById(R.id.paste);
       constraintLayout = findViewById(R.id.main);

        registerForContextMenu(view);
       ImageButton button = findViewById(R.id.imageButton);
       button.setOnClickListener(v ->{
           Toast.makeText(getApplicationContext(), "qr code scanner", Toast.LENGTH_SHORT).show();
       });

       monitor.setOnLongClickListener(v ->{
           Intent intent = new Intent(getApplicationContext(), settings.class);
           startActivity(intent);
           overridePendingTransition(R.anim.expand_in,R.anim.fade_out);
           return false;
       });

       monitor.setOnClickListener(v ->{
           String engine = "https://duckduckgo.com/?q=";
           if (empty(input)){
               startActivity(loadUrl(input.getText().toString(),engine));
           }else {
               Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
           }
       });

       paste.setOnClickListener(v ->{
           pasteFromClipboard();
       });

       startCheckingInternet();
       startCheckingClipboard();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        long backPressedTime = System.currentTimeMillis();
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false; // Reset the flag after 2 seconds
            }
        }, 2000); // 2000 milliseconds = 2 seconds
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("select search Engine");
        menu.setHeaderIcon(R.drawable.circle);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.spiral,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.gogle) {
            String engine = "https://www.google.com/search?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.duckduckgo) {
            String engine = "https://duckduckgo.com/?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.bing) {
            String engine = "https://www.bing.com/search?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.yahoo) {
            String engine = "https://search.yahoo.com/search?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.shodan) {
            String engine = "https://www.shodan.io/search?query=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.youtube) {
            String engine = "https://www.youtube.com/results?search_query=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        }else if (menuItem.getItemId() == R.id.reddit){
            String engine = "https://www.reddit.com/search/?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.tiktok) {
            String engine = "https://www.tiktok.com/search?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.Pinterest) {
            String engine = "https://www.pinterest.com/search/pins/?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.Quora) {
            String engine = "https://www.quora.com/search?q=";
            if (empty(input)){
                startActivity(loadUrl(input.getText().toString(),engine));
            }else {
                Toast.makeText(getApplicationContext(), "The webpage url is empty", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onContextItemSelected(menuItem);
    }

    public boolean empty(EditText editText){
        Boolean returns = null;
        returns = !editText.getText().toString().isEmpty();
        return returns;
    }

    public String loadUrl(String query,String Engine){
        return Engine+query;
    }

    public void startActivity(String url){
        if (isInternetAvailable()){
            Intent intent = new Intent(getApplicationContext(), browse.class);
            intent.putExtra("url",url);
            startActivity(intent);
            overridePendingTransition(R.anim.expand_in,R.anim.fade_out);
        }else {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout,"system is offline connect to the internet",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Connect", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(home.this, "connecting ....", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setActionTextColor(Color.parseColor("#DD2C00"))
                    .setTextColor(Color.parseColor("#FFFFFF"));
            snackbar.show();

        }

    }

    private void startCheckingInternet() {
        checkInternetRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isInternetAvailable()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "system is offline", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                }
                //handler.postDelayed(this, 1000);
            }
        };
        handler.post(checkInternetRunnable);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startCheckingClipboard() {
        checkClipboardRunnable = new Runnable() {
            @Override
            public void run() {
                String currentClipboardText = getClipboardText();
                if (!currentClipboardText.equals(lastClipboardText)) {
                    lastClipboardText = currentClipboardText;
                    paste.show();
                }
                handler.postDelayed(this, 2000); // Check every 2 seconds
            }
        };
        handler.post(checkClipboardRunnable);
    }

    private String getClipboardText() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            return clipData.getItemAt(0).getText().toString();
        }
        return "";
    }

    private void pasteFromClipboard() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();

        if (clipData != null && clipData.getItemCount() > 0) {
            String pastedText = clipData.getItemAt(0).getText().toString();
            String current = input.getText().toString() + pastedText;
            input.setText(current);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(checkInternetRunnable);
        handler.removeCallbacks(checkClipboardRunnable);
    }
}