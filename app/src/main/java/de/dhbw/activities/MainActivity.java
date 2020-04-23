package de.dhbw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import de.dhbw.R;
import de.dhbw.util.spinnerAdapter;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private static boolean playSound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.tower_defense_title_soundtrack);

        //Initialize Play-Button
        Button playButton = findViewById(R.id.playButtonMain);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //go to GameActivity
                if(parent.getSelectedItem().equals("PLAY"))
                {
                    //nothing
                }
                else{
                    Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(gameIntent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        Spinner spinner = findViewById(R.id.spinner_choose_difficulties);
        spinnerAdapter adapter = new spinnerAdapter(MainActivity.this, R.layout.spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        adapter.addAll("easy", "medium", "hard");
        adapter.add("PLAY");
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());


        spinner.setOnItemSelectedListener(listener);


        Button soundButton = findViewById(R.id.sound);
        soundButton.setBackgroundResource(R.drawable.sound);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playSound){
                    playSound = false;
                    soundButton.setBackgroundResource(R.drawable.no_sound);
                    mediaPlayer.pause();
                }else{
                    playSound = true;
                    soundButton.setBackgroundResource(R.drawable.sound);
                    mediaPlayer.start();
                }
            }
        });
        startMediaplayer();
    }

    public void startMediaplayer(){
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        if(playSound){
            mediaPlayer.start();
        }
    }
}
