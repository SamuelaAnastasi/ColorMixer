package com.example.android.colormixer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView color=null;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        color=(TextView)findViewById(R.id.color);

        ColorMixer mixer=(ColorMixer)findViewById(R.id.mixer);

        mixer.setOnColorChangedListener(onColorChange);
    }

    private ColorMixer.OnColorChangedListener onColorChange=
            new ColorMixer.OnColorChangedListener() {
                public void onColorChange(int argb) {
                    color.setText(Integer.toHexString(argb));
                }
            };
}
