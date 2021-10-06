package com.example.deltahacksvi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to  button find  by id
        Button findButton =  (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFind();
            }
        });

        Button reportButton =  (Button) findViewById(R.id.reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport();
            }
        });

        Button logOutButton = (Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    public void openFind() {
        Intent intent = new Intent(this,Find.class);
        startActivity(intent);
    }


    public void openReport() {
        Intent intent = new Intent(this,Report.class);
        startActivity(intent);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,Auth.class);
        startActivity(intent);

    }

}