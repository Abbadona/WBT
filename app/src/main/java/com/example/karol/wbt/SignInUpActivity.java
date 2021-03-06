package com.example.karol.wbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignInUpActivity extends AppCompatActivity {
    private SignInActivity signInActivity;
    private SignUpActivity signUpActivity;

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        AlertDialog alertDialog = new AlertDialog.Builder(SignInUpActivity.this).create();
        alertDialog.setTitle(getString(R.string.connection));
        alertDialog.setMessage(getString(R.string.not_signin));
        alertDialog.setCancelable(false);

        //---------------------------------------PRZYCISK--ZALOGUJ------------------------------
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.login_text), new DialogInterface.OnClickListener() {

            //
            //  Kliknąłeś w Zaloguj, więc wyświetla się alertdialog związany z logowaniem
            //

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(),SignInActivity.class);
                startActivity(intent);
                finish();

            }

        });
        //---------------------PRYCISK--REJESTRUJ-----------------------------
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,getString(R.string.registration),new DialogInterface.OnClickListener() {

            //
            //  Kliknąłeś w Rejestracje, więc tworzymy alterDialog dla Rejestracji
            //
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }

        });

        alertDialog.show();
    }
}
