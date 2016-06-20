package com.example.dianyo.itraveler; // need package name

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;


public class result extends Activity{
    public Bundle upload_bundle;
    private final int result_code_upload = 1;
    private final int result_success = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = this.getIntent().getExtras();
        upload_bundle = bundle;
        // input parameters
        PieChart.arrPer[0]= bundle.getInt("eat_total");
        PieChart.arrPer[1] = bundle.getInt("live_total");
        PieChart.arrPer[2] = bundle.getInt("transport_total");
        PieChart.arrPer[3] = bundle.getInt("buy_total");
        PieChart.arrPer[4] = bundle.getInt("entertain_total");
        PieChart.arrPer[5] = bundle.getInt("other_total");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == result_code_upload) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(result.this,"success",Toast.LENGTH_LONG).show();
                result.this.finish();
            }else{
                Toast.makeText(result.this,"socket error",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(result.this,"connect error",Toast.LENGTH_LONG).show();
        }

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

    public void clickSave(View view){
        Intent intent = new Intent();
        setResult(2, intent);
        AlertDialog.Builder builder = new AlertDialog.Builder(result.this);
        builder.setTitle("是否上傳至資料庫?");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent upload = new Intent();
                upload.setClass(result.this,Client_service.class);
                upload_bundle.putString("command","upload");
                //upload_bundle.putString("command","getdata"); //use to test getdata
                //upload_bundle.putString("object","eat");
                //upload_bundle.putString("subobject","noodle");
                upload.putExtras(upload_bundle);
                startActivityForResult(upload,result_code_upload);
                /*Client client = new Client("upload","json_test");*/

            }
        });
        builder.setNegativeButton("不要", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
}