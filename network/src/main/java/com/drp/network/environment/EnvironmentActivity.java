package com.drp.network.environment;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.drp.network.R;

public class EnvironmentActivity extends AppCompatActivity {

    private static final String SP_NAME = "data";
    private static final String KEY_TEST = "KEY_TEST";
    private boolean isTest = true;
    private RadioGroup rgEnvironment;
    private RadioButton rbTest;
    private RadioButton rbRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        isTest = sharedPreferences.getBoolean("KEY_TEST", true);
        rbRelease = findViewById(R.id.rb_release);
        rbTest = findViewById(R.id.rb_test);
        if (isTest) {
            rbTest.setChecked(true);
        } else {
            rbRelease.setChecked(true);
        }

        rgEnvironment = findViewById(R.id.rg_environment);
        rgEnvironment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_test) {
                    isTest = true;
                } else {
                    isTest = false;
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(KEY_TEST, isTest);
                edit.apply();
            }
        });
    }

    public static boolean isTestEnvironment(Application application) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("KEY_TEST", true);
    }
}