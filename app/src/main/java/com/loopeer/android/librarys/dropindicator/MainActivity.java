package com.loopeer.android.librarys.dropindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shadev.dropindicator.R;
import com.loopeer.android.librarys.dropindicator.detail.FullscreenActivity;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button btnstart;
    private MagicCircle circle3;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle3 = (MagicCircle)findViewById(R.id.circle3);
        this.btnstart = (Button) findViewById(R.id.btn_start);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                circle3.startAnimation();
            }
        });

        startActivity(new Intent(this, FullscreenActivity.class));
    }
}
