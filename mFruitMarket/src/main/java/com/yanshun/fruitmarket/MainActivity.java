package com.yanshun.fruitmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yanshun.mfluitmarket.common.county.BreakFastAddrAct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button jump = findViewById(R.id.onclick_my);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BreakFastAddrAct.class);
                startActivity(intent);
            }
        });
    }
}
