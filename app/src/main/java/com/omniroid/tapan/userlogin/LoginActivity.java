package com.omniroid.tapan.userlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    private GradientBackgroundPainter gradientBackgroundPainter;
    private TextView tvNaviSignUpPage;

    private EditText mLogEmail,mLogPassword;
    private Button mLoginButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View backgroundImage = findViewById(R.id.root_view);

        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();



        tvNaviSignUpPage = (TextView) findViewById(R.id.tv_signUp);
        tvNaviSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });




        mLogEmail = (EditText) findViewById(R.id.et_email);
        mLogPassword = (EditText) findViewById(R.id.et_pass);
        mLoginButton = (Button) findViewById(R.id.btn_log_in);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logging();
            }
        });

    }

    private void logging() {
        String email = mLogEmail.getText().toString();
        String password = mLogPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Something Went Wrong...");
            builder.setMessage("please fill all the data... ");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            LoginUser();
        }


    }

    private void LoginUser() {
        String email = mLogEmail.getText().toString();
        String password = mLogPassword.getText().toString();


        BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
        backgroundTask.execute("login",email,password);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }
}
