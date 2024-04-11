package info.androidhive.loginandregistration.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;


public class viewImpComplaint extends Activity implements AsyncResponse

{
    HashMap<String,String> a1 = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        try {
            Map<String, String> sendList = new HashMap<String, String>();
            sendList.put("action", "getlistImp");
            sendList.put("username", MainActivity.username);
            sendList.put("input", "");
            AsyncServerTask asyncTask = new AsyncServerTask(sendList, false, null);
            asyncTask.delegate = this;
            String output = asyncTask.execute().get();

      //  Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_LONG).show();

    }
        catch(Exception e1)
        {
        //    Toast.makeText(getApplicationContext(),"aa "+ e1,Toast.LENGTH_LONG).show();

        }
    }
    String ip ="";
    @Override
    public void processFinish(Map<String,Object> output) {

        String processList = output.get("status").toString();
        // String ipList = output.get("ip").toString();
        //String[] iparr = ipList.split("\n");
        String[] planets = processList.split("\n");
        final ArrayList<String> planetList = new ArrayList<String>();
        String []arr = new String[30];
        planetList.add("Token->Status->Description->Type->date->count");
        for (int i=0;i<planets.length;i++)
        {
            String a[] = planets[i].split("!");
            String sdate =  a[6].split("#")[0];
            String abc = a[8]+"->" + a[3]+"->"+ a[1]+"->"+a[2]+"->"+sdate+"->"+a[7];
            arr[i]=abc;
            planetList.add(abc);
            Log.d("venk",abc);
            a1.put(abc,planets[i]);


        }
//        final ArrayList<String> planetList = new ArrayList<String>();
        //planetList.addAll(Arrays.asList(iparr));
        //planetList.addAll(Arrays.asList(planets));
        ListView listView = (ListView) findViewById(R.id.mainListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_resources, planetList){ @Override
        public View getView(int i, View convertView, ViewGroup parent){
            // Get the current item from ListView
            View view = super.getView(i,convertView,parent);
            //for(int i=0;i<planetList.size();i++) {
                if (planetList.get(i).contains("Open")) {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.GREEN);
                } else if (planetList.get(i).contains("InProgress")) {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.YELLOW);
                } else if (planetList.get(i).contains("Reject")) {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.RED);
                }
                else{
                    view.setBackgroundColor(Color.WHITE);
                }


            //}
            return view;
        }
    };

        listView.setAdapter(adapter);
        //if ("gasleak".equalsIgnoreCase(MainActivity.username)||"waterleak".equalsIgnoreCase(MainActivity.username)||"Admin".equalsIgnoreCase(MainActivity.username)||"Water".equalsIgnoreCase(MainActivity.username) || "Garabage".equalsIgnoreCase(MainActivity.username)|| "Parking".equalsIgnoreCase(MainActivity.username)|| "Electricity".equalsIgnoreCase(MainActivity.username)|| "Sound".equalsIgnoreCase(MainActivity.username)|| "Others".equalsIgnoreCase(MainActivity.username)) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Map<String, String> sendList = new HashMap<String, String>();
                    String item = ((TextView) view).getText().toString();
                    String item1 = a1.get(item);
                    Intent intent = new Intent(viewImpComplaint.this,
                            ViewProblem.class);
                    intent.putExtra("a",item1);
                    startActivity(intent);
                    finish();


/*
                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(viewComplaint.this,
                            viewComplaint.class);
                    startActivity(intent);
finish();*/
                }
            }
            );

//        }

    }}
