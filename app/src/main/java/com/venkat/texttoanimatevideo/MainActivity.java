package com.venkat.texttoanimatevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextInputEditText userPrompt;
    private Button generateButton;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPrompt=findViewById(R.id.textInputEditText);
        generateButton=findViewById(R.id.generate_button);
        //Initialization
        textToSpeech=new TextToSpeech(this,this);


        generateButton.setOnClickListener(e-> speech(userPrompt.getText()+""));
    }

    private void speech(String text) {
        if(text!=null || !text.isEmpty()){
            HashMap<String,String> map=new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"utteranceId");

            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,map);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    Log.i("SPEECH","speech start success");
                }

                @Override
                public void onDone(String s) {
                    //Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(String s) {

                }
            });
        }
    }

    @Override
    public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("SPEECH","Lang not support || Missing data");
            }else{
                Log.i("SPEECH","tts success");
            }

        }else{
            Log.e("SPEECH","Initialization failed .. Check onInit method");
        }

    }

    @Override
    protected void onDestroy() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}