package com.btp_iitj.cnfmanag;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.btp_iitj.cnfmanag.Registration.RegistrationFragment;

public class RegisterBasicsActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_basics);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.registerBasicFrame, new RegistrationFragment()).addToBackStack("registerBasisframe").commit();

    }
}
