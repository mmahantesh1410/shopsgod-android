package app.shopsgod.com;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference notificationPref = (SwitchPreferenceCompat)findPreference("notification");
            Preference privacy = (Preference) findPreference("privacy");
            Preference about = (Preference) findPreference("version");
            Preference rate= (Preference) findPreference("rate");

            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //open browser or intent here
                    Toast.makeText(getContext(), "Shopsgod 1.0", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            rate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //open browser or intent here
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=app.shopsgod.com")));
                    Toast.makeText(getContext(), "Opening Play Store", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //open browser or intent here
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_LONG).show();
                    Intent ii=new Intent(getContext(), MainActivity.class);
                    ii.putExtra("url", "file:///android_asset/privacy.html");
                    startActivity(ii);


                    return true;
                }
            });
            notificationPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean isNotificationOn = (Boolean) newValue;
                    if(preference.getKey().equals("notification") && isNotificationOn){
                        // call the push registration for this user

                        FirebaseMessaging.getInstance().subscribeToTopic("shopsgod");
                        String token = FirebaseInstanceId.getInstance().getToken();
                        Log.e("Sub",token);
                        Toast.makeText(getContext(), "Notification Turned On", Toast.LENGTH_LONG).show();
                        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                                .edit()
                                .putBoolean("Notification", true)
                                .apply();

                    }else{
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("shopsgod");
                        Log.e("unsub","dd");
                        Toast.makeText(getContext(), "Notification Turned Off", Toast.LENGTH_LONG).show();
                        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                                .edit()
                                .putBoolean("Notification", false)
                                .apply();
                    }
                    return true;
                }
            });





        }
    }
}