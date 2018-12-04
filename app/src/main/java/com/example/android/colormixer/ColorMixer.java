package com.example.android.colormixer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by the_insider on 08/12/2017.
 */

public class ColorMixer extends RelativeLayout {

    private static final String SUPERSTATE = "superstate";
    private static final String COLOR  = "color";
    private View swatch  = null;
    private SeekBar redSeekBar = null;
    private SeekBar greenSeekBar = null;
    private SeekBar blueSeekBar = null;
    private OnColorChangedListener listener = null;

    public ColorMixer(Context context) {
        this(context, null);
    }

    public ColorMixer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorMixer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((AppCompatActivity) getContext()).getLayoutInflater()
                .inflate(R.layout.mixer, this, true);

        swatch = findViewById(R.id.swatch);

        redSeekBar = findViewById(R.id.red);
        redSeekBar.setMax(0xFF);
        redSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        greenSeekBar = findViewById(R.id.green);
        greenSeekBar.setMax(0xFF);
        greenSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        blueSeekBar = findViewById(R.id.blue);
        blueSeekBar.setMax(0xFF);
        blueSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        if(attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs,
                    R.styleable.ColorMixer, 0, 0);

            setColor(ta.getInt(R.styleable.ColorMixer_initialColor, Color.BLACK));
            ta.recycle();
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();

        state.putParcelable(SUPERSTATE, super.onSaveInstanceState());
        state.putInt(COLOR, getColor());

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable ss) {
        Bundle state = (Bundle) ss;

        super.onRestoreInstanceState(state.getParcelable(SUPERSTATE));
        setColor(state.getInt(COLOR));
    }

    public int getColor() {
        return Color.argb(0xFF, redSeekBar.getProgress(),
                greenSeekBar.getProgress(), blueSeekBar.getProgress());
    }

    public void setColor(int color) {
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));
        swatch.setBackgroundColor(color);
    }

    public OnColorChangedListener getOnColorChangedListener() {
        return listener;
    }

    public void setOnColorChangedListener(OnColorChangedListener listener) {
        this.listener = listener;
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            int color = getColor();
            swatch.setBackgroundColor(color);

            if(listener != null) {
                listener.onColorChange(color);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public interface OnColorChangedListener {
        public void onColorChange(int argb);
    }
}
