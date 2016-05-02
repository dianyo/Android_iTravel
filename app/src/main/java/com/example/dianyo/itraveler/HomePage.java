package com.example.dianyo.itraveler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomePage extends AppCompatActivity {

    private TextView text_new;
    private TextView text_his;
    private TextView text_help;
    private TextView text_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //first open?
        final  String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(settings.getBoolean("my_first_time", true)){

            //ask whether need tutorial
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Hi! First to meet you here. Do you need a simple tutorial?");
            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HomePage.this, Help_page.class);
                    startActivity(intent);
                }
            });
            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertBuilder.create().show();
            settings.edit().putBoolean("my_first_time", false).apply();
        }
        processViews();
        processControllers();
    }

    private void processViews(){
        text_new = (TextView) findViewById(R.id.text_new);
        text_his = (TextView) findViewById(R.id.text_his);
        text_help = (TextView) findViewById(R.id.text_help);
        text_exit = (TextView) findViewById(R.id.text_exit);
    }

    private void processControllers(){
        //set text style
        text_new.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/orange juice 2.0.ttf"));
        text_his.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/orange juice 2.0.ttf"));
        text_help.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/orange juice 2.0.ttf"));
        text_exit.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/orange juice 2.0.ttf"));
    }

    public void help_act(View view) {
        Intent intent = new Intent(this, Help_page.class);
        startActivity(intent);
    }

    public void exit_app(View view){
        finish();
        System.exit(0);
    }

    public void startNew(View view){    
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void his_act(View view){
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
}
