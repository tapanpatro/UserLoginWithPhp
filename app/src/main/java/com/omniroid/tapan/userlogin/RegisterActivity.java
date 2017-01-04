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

public class RegisterActivity extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    private TextView tvNavloginPage;
    private EditText mRegName,mRegEmail,mRegPassword,mRegConfrmPassword;
    private Button mRegButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View backgroundImage = findViewById(R.id.root_view_regi);

        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        tvNavloginPage = (TextView) findViewById(R.id.tv_logIn);
        tvNavloginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });



        mRegName = (EditText) findViewById(R.id.et_reg_name);
        mRegEmail = (EditText) findViewById(R.id.et_reg_email);
        mRegPassword = (EditText) findViewById(R.id.et_reg_password);
        mRegConfrmPassword = (EditText) findViewById(R.id.et_reg_cnfm_password);
        mRegButton = (Button) findViewById(R.id.btn_register);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registering(); // checking weather the data are correct ot not
            }
        });





    }

    private void registering() {


        String name = mRegName.getText().toString();
        String email = mRegEmail.getText().toString();
        final String password = mRegPassword.getText().toString();
        String cnfmPass = mRegConfrmPassword.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty((password))){


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

        }else if (!(TextUtils.equals(password,cnfmPass))){


            builder = new AlertDialog.Builder(this);
            builder.setTitle("Something Went Wrong...");
            builder.setMessage("password doesn't match ..");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    mRegPassword.setText("");
                    mRegConfrmPassword.setText("");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            registerNewAccount();
        }


    }

    private void registerNewAccount() {


        String name = mRegName.getText().toString();
        String email = mRegEmail.getText().toString();
        String password = mRegPassword.getText().toString();
        String cnfmPass = mRegConfrmPassword.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(RegisterActivity.this);
        backgroundTask.execute("register",name,email,password);

    }


    @Override protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
