package com.omniroid.tapan.userlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView name = (TextView) findViewById(R.id.tv_home_name);

        String message = getIntent().getStringExtra("message");
        name.setText(message);

    }
}
