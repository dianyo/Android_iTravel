package com.example.dianyo.itraveler;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
    private ListView listview;
    private TextView total_budget;
    private String[] objects_tmp = {"吃", "住宿", "交通", "娛樂", "購物", "其他"};
    private ArrayList<String> objects = new ArrayList<String>();
    //private ArrayAdapter<String> adapter;
    public MenuAdapter myadapter;
    private ArrayList<String> eat = new ArrayList<String>();
    private ArrayList<String> live = new ArrayList<String>();
    private ArrayList<String> transport = new ArrayList<String>();
    private ArrayList<String> entertain = new ArrayList<String>();
    private ArrayList<String> buy = new ArrayList<String>();
    private ArrayList<String> other = new ArrayList<String>();
    private String[] eat_tmp = {"餐廳", "夜市", "超商", "速食", "自助餐", "泡麵"};
    private String[] live_tmp = {"民宿", "飯店", "旅館", "網咖"};
    private String[] transport_tmp = {"火車", "公車", "客運", "計程車", "飛機", "渡輪", "租車", "潛水艇"};
    private String[] entertain_tmp = {"遊樂園", "網咖", "海灘", "景點"};
    private String[] buy_tmp = {"衣服", "鞋子", "紀念品"};
    private String[] other_tmp = {};
    private String menu_case = "";
    private ArrayList<Integer> eat_cost = new ArrayList<Integer>();
    private ArrayList<Integer> live_cost = new ArrayList<Integer>();
    private ArrayList<Integer> transport_cost = new ArrayList<Integer>();
    private ArrayList<Integer> entertain_cost = new ArrayList<Integer>();
    private ArrayList<Integer> buy_cost = new ArrayList<Integer>();
    private ArrayList<Integer> other_cost = new ArrayList<Integer>();
    private int eat_total = 0;
    private int live_total = 0;
    private int transport_total = 0;
    private int entertain_total = 0;
    private int buy_total = 0;
    private int other_total = 0;
    private int total = 0;
    private String user_name;
    private String tripname;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    public void init_history(Bundle bundle) {
        int i;
        for (i = 0; i < objects_tmp.length; i++) {
            objects.add(objects_tmp[i]);
        }
        eat = bundle.getStringArrayList("eat");
        live = bundle.getStringArrayList("live");
        transport = bundle.getStringArrayList("transport");
        entertain = bundle.getStringArrayList("entertain");
        buy = bundle.getStringArrayList("buy");
        other = bundle.getStringArrayList("other");
        eat_cost = bundle.getIntegerArrayList("eat_cost");
        live_cost = bundle.getIntegerArrayList("live_cost");
        transport_cost = bundle.getIntegerArrayList("transport_cost");
        entertain_cost = bundle.getIntegerArrayList("entertain_cost");
        buy_cost = bundle.getIntegerArrayList("buy_cost");
        other_cost = bundle.getIntegerArrayList("other_cost");
        user_name = bundle.getString("user_name");
        tripname = bundle.getString("trip_name");
        eat_total = calculate_budget("eat");
        live_total = calculate_budget("live");
        transport_total = calculate_budget("transport");
        entertain_total = calculate_budget("entertain");
        buy_total = calculate_budget("buy");
        other_total = calculate_budget("other");
        total = eat_total + live_total + transport_total + entertain_total + buy_total + other_total;
        return;
    }

    public void init_state() {
        int i;
        for (i = 0; i < objects_tmp.length; i++) {
            objects.add(objects_tmp[i]);
        }
        for (i = 0; i < eat_tmp.length; i++) {
            eat.add(eat_tmp[i]);
            eat_cost.add(0);
        }
        for (i = 0; i < live_tmp.length; i++) {
            live.add(live_tmp[i]);
            live_cost.add(0);
        }
        for (i = 0; i < transport_tmp.length; i++) {
            transport.add(transport_tmp[i]);
            transport_cost.add(0);
        }
        for (i = 0; i < entertain_tmp.length; i++) {
            entertain.add(entertain_tmp[i]);
            entertain_cost.add(0);
        }
        for (i = 0; i < buy_tmp.length; i++) {
            buy.add(buy_tmp[i]);
            buy_cost.add(0);
        }
        for (i = 0; i < other_tmp.length; i++) {
            other.add(other_tmp[i]);
            other_cost.add(0);
        }
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            user_name = bundle.getString("name");
            tripname = bundle.getString("travel");
            init_history(bundle);
        } else {
            init_state();
        }
        //setTitle(tripname);
        start_menu();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1) {
            if (resultCode == 2) {
                save();
                StartActivity.this.finish();
            }
            if (resultCode == 3) StartActivity.this.finish();
        }

    }

    public void start_menu() {
        setContentView(R.layout.object_layout);

        listview = (ListView) this.findViewById(R.id.object_list);
        total_budget = (TextView) this.findViewById(R.id.textView_total_budget);
        total_budget.setText(Integer.toString(total));
        myadapter = new MenuAdapter(this);

        //adapter = new ArrayAdapter<>(StartActivity.this, android.R.layout.simple_list_item_1, objects);
        listview.setDivider(new ColorDrawable(Color.BLACK));
        listview.setDividerHeight(2);
        listview.setAdapter(myadapter);
        //listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (myadapter.getItem(position).toString()) {
                    case "吃":
                        Toast.makeText(StartActivity.this, "吃", Toast.LENGTH_SHORT).show();
                        eat_menu();
                        break;
                    case "住宿":
                        Toast.makeText(StartActivity.this, "住宿", Toast.LENGTH_SHORT).show();
                        live_menu();
                        break;
                    case "交通":
                        Toast.makeText(StartActivity.this, "交通", Toast.LENGTH_SHORT).show();
                        transport_menu();
                        break;
                    case "娛樂":
                        Toast.makeText(StartActivity.this, "娛樂", Toast.LENGTH_SHORT).show();
                        entertain_menu();
                        break;
                    case "購物":
                        Toast.makeText(StartActivity.this, "購物", Toast.LENGTH_SHORT).show();
                        buy_menu();
                        break;
                    case "其他":
                        Toast.makeText(StartActivity.this, "其他", Toast.LENGTH_SHORT).show();
                        other_menu();
                        break;
                }
            }
        });
    }

    public void eat_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "eat";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(eat_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            eat.add(tmp);
                            eat_cost.add(0);
                            eat_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    public void live_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "live";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(live_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            live.add(tmp);
                            live_cost.add(0);
                            live_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    public void transport_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "transport";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(transport_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            transport.add(tmp);
                            transport_cost.add(0);
                            transport_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    public void entertain_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "entertain";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(entertain_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            entertain.add(tmp);
                            entertain_cost.add(0);
                            entertain_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    public void buy_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "buy";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(buy_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            buy.add(tmp);
                            buy_cost.add(0);
                            buy_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    public void other_menu() {
        ObjectAdapter adapter = null;
        setContentView(R.layout.eat_layout);
        menu_case = "other";
        TextView option_budget = (TextView) this.findViewById(R.id.textView_budget);
        option_budget.setText(Integer.toString(other_total));
        final ImageButton button_add = (ImageButton) this.findViewById(R.id.imageButton_addobject);
        final ListView list = (ListView) this.findViewById(R.id.listView_eat);
        adapter = new ObjectAdapter(this);
        list.setAdapter(adapter);
        button_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("加入選項");
                View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_list, null);
                final EditText object_add = (EditText) view.findViewById(R.id.editText_add_list);
                builder.setView(view);
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = object_add.getText().toString();
                        if (tmp.length() == 0) {

                        } else {
                            other.add(tmp);
                            other_cost.add(0);
                            other_menu();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }



    public class MenuAdapter extends BaseAdapter {
        private LayoutInflater myInflater;

        public MenuAdapter(Context c) {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.menu_object, null);
            final TextView title = (TextView) convertView.findViewById(R.id.textView_object_title);
            final TextView cost = (TextView) convertView.findViewById(R.id.textView_object_money);
            title.setText(objects.get(position));
            switch (objects.get(position)) {
                case "吃":
                    cost.setText(Integer.toString(eat_total));
                    break;
                case "住宿":
                    cost.setText(Integer.toString(live_total));
                    break;
                case "交通":
                    cost.setText(Integer.toString(transport_total));
                    break;
                case "娛樂":
                    cost.setText(Integer.toString(entertain_total));
                    break;
                case "購物":
                    cost.setText(Integer.toString(buy_total));
                    break;
                case "其他":
                    cost.setText(Integer.toString(other_total));
                    break;
            }
            //budget.setText(Integer.toString(eat_total));
            return convertView;
        }
    }

    public class ObjectAdapter extends BaseAdapter {
        private LayoutInflater myInflater;

        public ObjectAdapter(Context c) {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            switch (menu_case) {
                case "eat":
                    return eat.size();
                case "live":
                    return live.size();
                case "transport":
                    return transport.size();
                case "entertain":
                    return entertain.size();
                case "buy":
                    return buy.size();
                case "other":
                    return other.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            switch (menu_case) {
                case "eat":
                    return eat.get(position);
                case "live":
                    return live.get(position);
                case "transport":
                    return transport.get(position);
                case "entertain":
                    return entertain.get(position);
                case "buy":
                    return buy.get(position);
                case "other":
                    return other.get(position);
            }
            return 0;

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.object_detail_xml, null);
            final TextView name = (TextView) convertView.findViewById(R.id.textView_object);
            final TextView title = (TextView) findViewById(R.id.textView_eat_title);
            final EditText cost = (EditText) convertView.findViewById(R.id.editText_money);
            final TextView budget = (TextView) findViewById(R.id.textView_budget);
            final ImageButton imagebutton = (ImageButton) convertView.findViewById(R.id.imageButton_add);
            final ImageButton imagebutton_minus = (ImageButton) convertView.findViewById(R.id.imageButton_minus);
            final ImageButton imagebutton_clear = (ImageButton) convertView.findViewById(R.id.imageButton_clear);

            switch (menu_case) {
                case "eat":
                    name.setText(eat.get(position));
                    cost.setText(eat_cost.get(position).toString());
                    budget.setText(Integer.toString(eat_total));
                    title.setText("選擇食物預算");
                    break;
                case "live":
                    name.setText(live.get(position));
                    cost.setText(live_cost.get(position).toString());
                    budget.setText(Integer.toString(live_total));
                    title.setText("選擇住宿預算");
                    break;
                case "transport":
                    name.setText(transport.get(position));
                    cost.setText(transport_cost.get(position).toString());
                    budget.setText(Integer.toString(transport_total));
                    title.setText("選擇交通預算");
                    break;
                case "entertain":
                    name.setText(entertain.get(position));
                    cost.setText(entertain_cost.get(position).toString());
                    budget.setText(Integer.toString(entertain_total));
                    title.setText("選擇娛樂預算");
                    break;
                case "buy":
                    name.setText(buy.get(position));
                    cost.setText(buy_cost.get(position).toString());
                    budget.setText(Integer.toString(buy_total));
                    title.setText("選擇購物預算");
                    break;
                case "other":
                    name.setText(other.get(position));
                    cost.setText(other_cost.get(position).toString());
                    budget.setText(Integer.toString(other_total));
                    title.setText("選擇其他預算");
                    break;
            }


            cost.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub
                    int temp;
                    if (cost.getText().length() == 0) {
                    } else {
                        switch (menu_case) {
                            case "eat":
                                temp = Integer.parseInt(cost.getText().toString());
                                //eat_cost.setElementAt( temp, position);
                                eat_cost.set(position, temp);
                                break;
                            case "live":
                                temp = Integer.parseInt(cost.getText().toString());
                                //live_cost.setElementAt(temp, position);
                                live_cost.set(position, temp);
                                break;
                            case "transport":
                                temp = Integer.parseInt(cost.getText().toString());
                                //transport_cost.setElementAt(temp, position);
                                transport_cost.set(position, temp);
                                break;
                            case "entertain":
                                temp = Integer.parseInt(cost.getText().toString());
                                //entertain_cost.setElementAt(temp, position);
                                entertain_cost.set(position, temp);
                                break;
                            case "buy":
                                temp = Integer.parseInt(cost.getText().toString());
                                //buy_cost.setElementAt(temp, position);
                                buy_cost.set(position, temp);
                                break;
                            case "other":
                                temp = Integer.parseInt(cost.getText().toString());
                                //other_cost.setElementAt(temp, position);
                                other_cost.set(position, temp);
                                break;
                        }
                        //eat_cost[position] = Integer.parseInt(cost.getText().toString());
                        //budget.setText(Integer.toString(eat_cost[position]));
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                    if (cost.getText().length() == 0) {
                        cost.setText("0");
                        switch (menu_case) {
                            case "eat":
                                //eat_cost.setElementAt(0, position);
                                eat_cost.set(position, 0);
                                //eat_cost[position] = 0;
                                break;
                            case "live":
                                //live_cost.setElementAt(0, position);
                                live_cost.set(position, 0);
                                //live_cost[position] = 0;
                                break;
                            case "transport":
                                //transport_cost.setElementAt(0, position);
                                transport_cost.set(position, 0);
                                //transport_cost[position] = 0;
                                break;
                            case "entertain":
                                //entertain_cost.setElementAt(0, position);
                                entertain_cost.set(position, 0);
                                //entertain_cost[position] = 0;
                                break;
                            case "buy":
                                //buy_cost.setElementAt(0, position);
                                buy_cost.set(position, 0);
                                //buy_cost[position] = 0;
                                break;
                            case "other":
                                //other_cost.setElementAt(0, position);
                                other_cost.set(position, 0);
                                //other_cost[position] = 0;
                                break;
                        }
                    }
                    switch (menu_case) {
                        case "eat":
                            eat_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(eat_total));
                            break;
                        case "live":
                            live_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(live_total));
                            break;
                        case "transport":
                            transport_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(transport_total));
                            break;
                        case "entertain":
                            entertain_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(entertain_total));
                            break;
                        case "buy":
                            buy_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(buy_total));
                            break;
                        case "other":
                            other_total = calculate_budget(menu_case);
                            budget.setText(Integer.toString(other_total));
                            break;
                    }

                }
            });
            imagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(StartActivity.this, "click", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    builder.setTitle("加入預算");
                    View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_layout, null);
                    final EditText add_cost_dialog = (EditText) view.findViewById(R.id.editText_change_cost);
                    builder.setView(view);
                    builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int i = Integer.parseInt(cost.getText().toString());
                            int j = Integer.parseInt(add_cost_dialog.getText().toString());
                            cost.setText(Integer.toString(i + j));
                            //eat_cost[position] += j;
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            imagebutton_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    builder.setTitle("扣掉預算");
                    View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.add_layout, null);
                    final EditText minus_cost_dialog = (EditText) view.findViewById(R.id.editText_change_cost);
                    builder.setView(view);
                    builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int i = Integer.parseInt(cost.getText().toString());
                            int j = Integer.parseInt(minus_cost_dialog.getText().toString());
                            if (j >= i) {
                                cost.setText("0");
                            } else {
                                cost.setText(Integer.toString(i - j));
                                //eat_cost[position] += j;
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            imagebutton_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    builder.setTitle("確定清除 " + getItem(position) + " 的預算?");
                    builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cost.setText("0");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            convertView.setOnLongClickListener(new LongClick(position));
            return convertView;
        }

        class LongClick implements View.OnLongClickListener {
            private int position;
            public LongClick(int position){
                this.position = position;
            }//用建構子獲得該item 的值
            @Override
            public boolean onLongClick(View view) {
               // Toast mToast = Toast.makeText(view.getContext(),"Long"+position, Toast.LENGTH_SHORT);
                //mToast.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("確定刪除項目 " + getItem(position) + " 與預算?");
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove_object(position);
                        switch (menu_case) {
                            case "eat":
                                eat_total = calculate_budget(menu_case);
                                eat_menu();
                                break;
                            case "live":
                                live_total = calculate_budget(menu_case);
                                live_menu();
                                break;
                            case "transport":
                                transport_total = calculate_budget(menu_case);
                                transport_menu();
                                break;
                            case "entertain":
                                entertain_total = calculate_budget(menu_case);
                                entertain_menu();
                                break;
                            case "buy":
                                buy_total = calculate_budget(menu_case);
                                buy_menu();
                                break;
                            case "other":
                                other_total = calculate_budget(menu_case);
                                other_menu();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

                return true;
            }
        }
    }

    public void back_to_menu(View view) {
        total = eat_total + live_total + entertain_total + transport_total + buy_total + other_total;
        start_menu();
        return;
    }
    private void remove_object(int position){
        switch(menu_case){
            case "eat":
                eat.remove(position);
                eat_cost.remove(position);
                break;
            case "live":
                live.remove(position);
                live_cost.remove(position);
                break;
            case "transport":
                transport.remove(position);
                transport_cost.remove(position);
                break;
            case "entertain":
                entertain.remove(position);
                entertain_cost.remove(position);
                break;
            case "buy":
                buy.remove(position);
                buy_cost.remove(position);
                break;
            case "other":
                other.remove(position);
                other_cost.remove(position);
                break;
        }
    }
    public int calculate_budget(String input) {
        int count = 0;
        int i;
        switch (input) {
            case "eat":
                for (i = 0; i < eat_cost.size(); i++)
                    count += eat_cost.get(i);
                break;
            case "live":
                for (i = 0; i < live_cost.size(); i++) {
                    //count += Integer.parseInt((live_cost.get(i).toString()));
                    count += live_cost.get(i);
                    //count += live_cost[i];
                }
                break;
            case "transport":
                for (i = 0; i < transport_cost.size(); i++)
                    count += transport_cost.get(i);
                break;
            case "entertain":
                for (i = 0; i < entertain_cost.size(); i++)
                    count += entertain_cost.get(i);
                break;
            case "buy":
                for (i = 0; i < buy_cost.size(); i++)
                    count += buy_cost.get(i);
                break;
            case "other":
                for (i = 0; i < other_cost.size(); i++)
                    count += other_cost.get(i);
                break;
        }
        return count;
    }

    public void continue_click(View view) {
        Bundle detail = new Bundle();
        detail.putIntegerArrayList("eat_cost", eat_cost);
        detail.putIntegerArrayList("live_cost", live_cost);
        detail.putIntegerArrayList("transport_cost", transport_cost);
        detail.putIntegerArrayList("entertain_cost", entertain_cost);
        detail.putIntegerArrayList("buy_cost", buy_cost);
        detail.putIntegerArrayList("other_cost", other_cost);
        detail.putStringArrayList("eat", eat);
        detail.putStringArrayList("live", live);
        detail.putStringArrayList("transport", transport);
        detail.putStringArrayList("entertain", entertain);
        detail.putStringArrayList("buy", buy);
        detail.putStringArrayList("other", other);
        detail.putInt("eat_total", eat_total);
        detail.putInt("live_total", live_total);
        detail.putInt("transport_total", transport_total);
        detail.putInt("entertain_total", entertain_total);
        detail.putInt("buy_total", buy_total);
        detail.putInt("other_total", other_total);
        detail.putInt("total", total);
        detail.putString("user_name", user_name);
        detail.putString("trip_name", tripname);
        Intent next = new Intent();
        next.setClass(StartActivity.this, result.class);
        next.putExtras(detail);
        startActivityForResult(next, 1);
    }

    public void save() {
        Bundle detail = new Bundle();
        detail.putIntegerArrayList("eat_cost", eat_cost);
        detail.putIntegerArrayList("live_cost", live_cost);
        detail.putIntegerArrayList("transport_cost", transport_cost);
        detail.putIntegerArrayList("entertain_cost", entertain_cost);
        detail.putIntegerArrayList("buy_cost", buy_cost);
        detail.putIntegerArrayList("other_cost", other_cost);
        detail.putStringArrayList("eat", eat);
        detail.putStringArrayList("live", live);
        detail.putStringArrayList("transport", transport);
        detail.putStringArrayList("entertain", entertain);
        detail.putStringArrayList("buy", buy);
        detail.putStringArrayList("other", other);
        detail.putInt("eat_total", eat_total);
        detail.putInt("live_total", live_total);
        detail.putInt("transport_total", transport_total);
        detail.putInt("entertain_total", entertain_total);
        detail.putInt("buy_total", buy_total);
        detail.putInt("other_total", other_total);
        detail.putInt("total", total);
        detail.putString("user_name", user_name);
        detail.putString("trip_name", tripname);
        Intent next = new Intent();
        next.setClass(StartActivity.this, Write.class);
        next.putExtras(detail);
        startActivity(next);
    }

    public void activity_finish(View view) {
        finish();
    }

}
