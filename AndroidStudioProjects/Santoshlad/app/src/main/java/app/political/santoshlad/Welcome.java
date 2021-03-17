package app.political.santoshlad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Welcome extends AppCompatActivity {




    /**
     * Permissions that need to be explicitly requested from end user.
     */

    ProgressBar progressBar;
    TextView per;
    int progressStatus = 0;
    Handler handler = new Handler();
    public String mytoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        per = (TextView) findViewById(R.id.loadper);
        progressBar=(ProgressBar)findViewById(R.id.horprogress);

        Log.d("welcometoken ",""+ FirebaseInstanceId.getInstance().getToken());
        mytoken = FirebaseInstanceId.getInstance().getToken();


        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100)
                {
                    progressStatus += 1;
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            progressBar.setProgress(progressStatus);
                            per.setText(progressStatus + "%");
                        }
                    });
                    try
                    {
                        Thread.sleep(20);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (progressStatus==100)
                {

                    Intent i = new Intent(Welcome.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }).start();







    }



}
