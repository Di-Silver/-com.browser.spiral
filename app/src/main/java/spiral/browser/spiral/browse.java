package spiral.browser.spiral;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class browse extends AppCompatActivity {

    WebView webview;
    String url;
    FloatingActionButton menus;
    ProgressBar progressBar;
    ViewSwitcher viewSwitcher;
    Handler handler = new Handler();
    Runnable showButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        menus = findViewById(R.id.floating);
        webview = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.web_progress);
        viewSwitcher = findViewById(R.id.viewSwitcher);
        url = getIntent().getStringExtra("url");

        if (url != null){
            webview.loadUrl(url);
        }else {
            webview.loadUrl("www.google.com");
        }

        menus.setOnClickListener(v ->{menus();});
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setLoadWithOverviewMode(true);
        showButton = new Runnable() {
            @Override
            public void run() {
                menus.setVisibility(View.INVISIBLE);
            }
        };
        webview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i > i3) {
                    //Toast.makeText(getApplicationContext(), "scroll down wards", Toast.LENGTH_SHORT).show();
                    menus.setVisibility(View.VISIBLE);
                    handler.postDelayed(showButton,2000);
                }
            }
        });
        safeBrowsing();
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack();
        }else {
            super.onBackPressed();
        }
    }

    private  void safeBrowsing(){
        try {
            webview.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webview.getSettings().setSafeBrowsingEnabled(true);
            }
            webview.getSettings().setAllowFileAccess(false);
            webview.getSettings().setAllowContentAccess(false);
            CookieManager.getInstance().removeAllCookies(null);
            WebStorage.getInstance().deleteAllData();
            webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        } finally {

        }
    }

    private void menus(){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),menus);
        popupMenu.getMenuInflater().inflate(R.menu.spiral,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int ids = menuItem.getItemId();
                if (ids == R.id.bing){
                    Toast.makeText(getApplicationContext(), "bing", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private  void normalBrowsing(){
        try {
            webview.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webview.getSettings().setSafeBrowsingEnabled(true);
            }
            webview.getSettings().setAllowFileAccess(false);
            webview.getSettings().setAllowContentAccess(false);
            CookieManager.getInstance().removeAllCookies(null);
            WebStorage.getInstance().deleteAllData();
            webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        } finally {

        }
    }

}