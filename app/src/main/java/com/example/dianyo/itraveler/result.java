package com.example.dianyo.itraveler; // need package name

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class result extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = this.getIntent().getExtras();
        // input parameters
        PieChart.arrPer[0]= bundle.getInt("eat_total");
        PieChart.arrPer[1] = bundle.getInt("live_total");
        PieChart.arrPer[2] = bundle.getInt("transport_total");
        PieChart.arrPer[3] = bundle.getInt("buy_total");
        PieChart.arrPer[4] = bundle.getInt("entertain_total");
        PieChart.arrPer[5] = bundle.getInt("other_total");

    }
    public void clickEdit(View view) {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    public void clickHome(View view) {
        Intent intent = new Intent();
        setResult(3, intent);
        finish();
    }

    public void clickSave(View view) {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();

    }
}