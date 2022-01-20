package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Pass extends Activity {
    private EditText     inputEmail ;
    private ProgressBar  progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        //Change The Title Bar To No Tittle.
        this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        //Change The Screen To FullScreen and hide the clock and notification bar.
        this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_reset__pass );
        inputEmail = findViewById ( R.id.inputEmailReset );
        progressBar = findViewById( R.id.progressBar);

        auth = FirebaseAuth.getInstance();
    }

    public void ResetPassViaEmail ( View view ) {

        String email = inputEmail.getText().toString().trim();

        if ( TextUtils.isEmpty(email)) {
            inputEmail.setError ( getString( R.string.Required) );
            return;
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Reset_Pass.this, R.string.Send_Reset_To_Email, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Reset_Pass.this, LoginPage.class));
                            } else {
                                Toast.makeText(Reset_Pass.this, R.string.Not_Sent_Reset_To_Email, Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });


        }
    }
}
