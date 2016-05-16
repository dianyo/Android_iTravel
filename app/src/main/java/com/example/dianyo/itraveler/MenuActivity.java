package com.example.dianyo.itraveler;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MenuActivity extends Activity {
    private ArrayList<String> eat = new ArrayList<String>();
    private ArrayList<String> live = new ArrayList<String>();
    private ArrayList<String> transport = new ArrayList<String>();
    private ArrayList<String> entertain = new ArrayList<String>();
    private ArrayList<String> buy = new ArrayList<String>();
    private ArrayList<String> other = new ArrayList<String>();
    private String[] eat_tmp = {"餐廳","夜市","超商","速食","自助餐","泡麵"};
    private String[] live_tmp = {"民宿","飯店","旅館","網咖"};
    private String[] transport_tmp = {"火車","公車","客運","計程車","飛機","渡輪","租車","潛水艇"};
    private String[] entertain_tmp = {"遊樂園","網咖","海灘","景點"};
    private String[] buy_tmp = {"衣服","鞋子","紀念品"};
    private String[] other_tmp = {};
    private ArrayList<Integer> eat_cost = new ArrayList<Integer>();
    private ArrayList<Integer> live_cost = new ArrayList<Integer>();
    private ArrayList<Integer> transport_cost = new ArrayList<Integer>();
    private ArrayList<Integer> entertain_cost = new ArrayList<Integer>();
    private ArrayList<Integer> buy_cost = new ArrayList<Integer>();
    private ArrayList<Integer> other_cost = new ArrayList<Integer>();
    public void init_next(){
        int i;
        for(i=0;i<eat_tmp.length;i++){
            eat.add(eat_tmp[i]);
            eat_cost.add(0);
        }
        for(i=0;i<live_tmp.length;i++){
            live.add(live_tmp[i]);
            live_cost.add(0);
        }
        for(i=0;i<transport_tmp.length;i++){
            transport.add(transport_tmp[i]);
            transport_cost.add(0);
        }
        for(i=0;i<entertain_tmp.length;i++){
            entertain.add(entertain_tmp[i]);
            entertain_cost.add(0);
        }
        for(i=0;i<buy_tmp.length;i++){
            buy.add(buy_tmp[i]);
            buy_cost.add(0);
        }
        for(i=0;i<other_tmp.length;i++){
            other.add(other_tmp[i]);
            other_cost.add(0);
        }
        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_name);
        //initActionBar();
    }
    public String name;
    private void initActionBar(){
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }
    public void continue_click(View view){
        EditText txt_name = (EditText) findViewById(R.id.edit_name);
        name = txt_name.getText().toString();
        if(name.length() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            builder.setTitle("請輸入有效的名字");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }else {
            setContentView(R.layout.activity_menu);
            TextView txt_name_menu = (TextView) findViewById(R.id.text_hi);
            String output = "嗨" + name;
            txt_name_menu.setText(output);
        }
    }
    public void start_click(View view){
        EditText txt_tripname = (EditText) findViewById(R.id.edit_trip_name);
        String tripname = txt_tripname.getText().toString();
        Intent start_intent = new Intent();
        start_intent.setClass(MenuActivity.this, StartActivity.class);
        init_next();
        Bundle bundle = new Bundle();
        bundle.putString("user_name", name);
        bundle.putString("trip_name", tripname);
        bundle.putStringArrayList("eat", eat);
        bundle.putStringArrayList("live", live);
        bundle.putStringArrayList("transport", transport);
        bundle.putStringArrayList("entertain", entertain);
        bundle.putStringArrayList("buy", buy);
        bundle.putStringArrayList("other", other);
        bundle.putIntegerArrayList("eat_cost", eat_cost);
        bundle.putIntegerArrayList("live_cost", live_cost);
        bundle.putIntegerArrayList("transport_cost", transport_cost);
        bundle.putIntegerArrayList("entertain_cost", entertain_cost);
        bundle.putIntegerArrayList("buy_cost", buy_cost);
        bundle.putIntegerArrayList("other_cost", other_cost);
        start_intent.putExtras(bundle);
        startActivity(start_intent);
        finish();
    }
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
