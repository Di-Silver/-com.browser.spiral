package spiral.browser.spiral;

import android.content.Context;
import android.content.SharedPreferences;

public class Data {
    public final String sharedPrefs = "SPIRAL";
    public final String browseState = "BROWSE_STATE";
    public final String soundNotification = "SOUND_NOTIFICATION";
    public final String history = "HISTORY";



    Context context;
    public Data(Context context) {
        this.context = context;
    }

    public SharedPreferences.Editor sharedpref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    public void browsingState(Boolean state){
        SharedPreferences.Editor editor = sharedpref();
        editor.putBoolean(browseState,state);
        editor.apply();
    }

    public void soundNotifications(Boolean state){
        SharedPreferences.Editor editor = sharedpref();
        editor.putBoolean(soundNotification,state);
        editor.apply();
    }

    public void historyState(Boolean state){
        SharedPreferences.Editor editor = sharedpref();
        editor.putBoolean(history,state);
        editor.apply();
    }



}
