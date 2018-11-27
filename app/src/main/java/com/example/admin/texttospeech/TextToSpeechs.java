package com.example.admin.texttospeech;


import android.app.Activity;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechs extends AppCompatActivity {

    private static final int HIDE_KEYBOARD_FLAG = 0;
    private Button btnToSpeech;
    private EditText etFirstName;
    private EditText etLastName;
    private TextToSpeech toSpeech;
    private TextView tvSpeechSpeed;
    private TextView tvSpeechPitch;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        btnToSpeech = findViewById(R.id.btnToSpeech);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        tvSpeechSpeed = findViewById(R.id.tvSpeechSpeed);
        tvSpeechPitch = findViewById(R.id.tvSpeechPitch);

        SeekBar sbSpeechSpeedSeekBar = findViewById(R.id.sbSpeechSpeedSeekBar);
        sbSpeechSpeedSeekBar.setMax(100);
        sbSpeechSpeedSeekBar.setProgress(50);
        String text = sbSpeechSpeedSeekBar.getProgress() + "%";
        tvSpeechSpeed.setText(text);

        sbSpeechSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = String.valueOf(progress) + "%";
                tvSpeechSpeed.setText(text);
                float rate = (float) progress / 50;
                if(rate <= 0.1) rate = 0.1F;
                toSpeech.setSpeechRate(rate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar sbSpeechPitchSeekBar = findViewById(R.id.sbSpeechPitch);
        sbSpeechPitchSeekBar.setMax(100);
        sbSpeechPitchSeekBar.setProgress(50);
        text = sbSpeechPitchSeekBar.getProgress() + "%";
        tvSpeechPitch.setText(text);

        sbSpeechPitchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = String.valueOf(progress) + "%";
                tvSpeechPitch.setText(text);
                float pitch = (float) progress / 50;
                if(pitch <= 0.1) pitch = 0.1F;
                toSpeech.setPitch(pitch);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    toSpeech.setLanguage(Locale.UK);
                    btnToSpeech.setEnabled(true);
                }
                else
                    Toast.makeText(TextToSpeechs.this, "Inintialization Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), HIDE_KEYBOARD_FLAG);
    }

    public void toSpeech(View view) {
        hideKeyboard(view);
        String fNAme = null;
        String lNAme = null;
        fNAme = etFirstName.getText().toString();
        lNAme = etLastName.getText().toString();
        if(fNAme.length() != 0 && lNAme.length() != 0 ) {
            String name = "Hello there  " + fNAme + lNAme + "   how are you doing";
            toSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null);
        }
        else
            Toast.makeText(this, "Enter First and Last Name", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }
}