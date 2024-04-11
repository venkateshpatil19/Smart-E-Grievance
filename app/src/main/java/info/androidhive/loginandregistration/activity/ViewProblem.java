package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class ViewProblem extends Activity implements AsyncResponse{

    String ip ="";
    TextView a,b,c,d,due,sdate,cdate,token,count,remarktext,feed,feed1;
    Button button1,button2,button3;
    EditText remark;
    @Override
    public void processFinish(Map<String,Object> output) {
        Intent intent = new Intent(ViewProblem.this,viewComplaint.class);
        startActivity(intent);
        finish();
    }
     String filename ="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewproblem);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        List<String> categories = new ArrayList<String>();
        categories.add("Open");
        categories.add("InProgress");
        categories.add("Close");
        categories.add("Reject");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

            spinner.setAdapter(dataAdapter);

        Bundle extras = getIntent().getExtras();
        final String item = extras.getString("a");
        ip = "https://www.google.com/maps/@"+item.split("!")[0]+"\"";
        a = (TextView) findViewById(R.id.a);
        b = (TextView) findViewById(R.id.b);
        c = (TextView) findViewById(R.id.c);
        d = (TextView) findViewById(R.id.d);
        remarktext = (TextView) findViewById(R.id.remarktext);
        due = (TextView) findViewById(R.id.due);
        cdate = (TextView) findViewById(R.id.cdate);
        feed = (TextView) findViewById(R.id.feed);
        feed1 = (TextView) findViewById(R.id.feed2);
        sdate = (TextView) findViewById(R.id.sdate);
        count = (TextView) findViewById(R.id.count);
        token = (TextView) findViewById(R.id.Token);
        button1 = (Button) findViewById(R.id.btnPic);
        button2 = (Button) findViewById(R.id.update);
        button3 = (Button) findViewById(R.id.rate);
        remark = (EditText) findViewById(R.id.remark);
       // a.setClickable(true);
        //a.setMovementMethod(LinkMovementMethod.getInstance());
        a.setText(Html.fromHtml(ip, Html.FROM_HTML_MODE_COMPACT));
        final String title =item.split("!")[1];
        String status = item.split("!")[3];
      // a.setText("Problem Description");
        b.setText(title);
        c.setText(item.split("!")[2]);
        d.setText(status);
        String sdate2 =  item.split("!")[6].split("#")[0];
        String sdate1 =  item.split("!")[6].split("#")[1];
        due.setText(sdate1);
        sdate.setText(sdate2);
        count.setText(item.split("!")[7]);
        token.setText(item.split("!")[8]);
        if(status.equalsIgnoreCase("close"))
        {
            spinner.setVisibility(View.INVISIBLE);
            cdate.setText(item.split("!")[6].split("#")[2]);
        }
else if(status.equalsIgnoreCase("reject"))
        {
            spinner.setVisibility(View.INVISIBLE);
         //   cdate.setText("Complaint Rejected due to \n1.Fake Complain\n2.Already Solved");

        }
        else{
            cdate.setText(" Not Close");
        }

        final String a = item.split("!")[4];
        final String r = item.split("!")[5];
        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar); // initiate a rating bar
        simpleRatingBar.setRating(Float.parseFloat(r));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String serverip = "http://192.168.160.116:8080/Complaint";
                Toast.makeText(getApplicationContext(),"a"+a,Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(serverip +"/Download.jsp?filename="+a));
                startActivity(browserIntent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar); // initiate a rating bar
                Float ratingNumber = simpleRatingBar.getRating();
                Map<String, String> sendList = new HashMap<String, String>();
                sendList.put("title", title);
                sendList.put("action", "title");
                sendList.put("ratingNumber", ""+ratingNumber);
                sendList.put("remark", remark.getText().toString());
                sendList.put("ip", ip);
                try {
                    AsyncServerTask asyncTask = new AsyncServerTask(sendList, false, null);
                    asyncTask.delegate = null;
                    String message = asyncTask.execute().get();
                    Toast.makeText(getBaseContext(), "Rating Given Successfully", Toast.LENGTH_LONG).show();
                    /* */
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> sendList = new HashMap<String, String>();
                sendList.put("drop", spinner.getSelectedItem().toString());
                sendList.put("action", "close");
                    sendList.put("IMEI", title);
                    sendList.put("ip", ip);
                sendList.put("remark", remark.getText().toString());
                    try {
                        AsyncServerTask asyncTask = new AsyncServerTask(sendList, false, null);
                        asyncTask.delegate = null;
                        String message = asyncTask.execute().get();
                        Toast.makeText(getBaseContext(), "Update the Status of Problem", Toast.LENGTH_LONG).show();
                       /* */
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                    }

            }
        });
//        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        if ("Admin".equals(MainActivity.username)||"Water".equals(MainActivity.username) || "Garabage".equals(MainActivity.username)|| "Parking".equals(MainActivity.username)|| "Electricity".equals(MainActivity.username)|| "Sound".equals(MainActivity.username)|| "Others".equals(MainActivity.username)) {
            if(status.equalsIgnoreCase("close")) {
                spinner.setVisibility(View.INVISIBLE);
            }else{
                spinner.setVisibility(View.VISIBLE);
            }
            remark.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.INVISIBLE);
            remarktext.setText("Remark");
            simpleRatingBar.setRating(Float.parseFloat(r));
            simpleRatingBar.setIsIndicator(true);
            if(item.split("!").length>9)
             feed1.setText(item.split("!")[9]);

            if(item.split("!").length>10)
             remark.setText(item.split("!")[10]);


        }
        else {
            if(item.split("!").length>10)
                feed1.setText(item.split("!")[10]);
            if(item.split("!").length>9) {
                remark.setText(item.split("!")[9]);
                feed.setText("Admin Remark:");
                remarktext.setText("Feedback:");
                remark.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.VISIBLE);
                button2.setVisibility(View.INVISIBLE);
            }

        }
        if(MainActivity.username.length()==0){
            simpleRatingBar.setIsIndicator(true);
            button3.setVisibility(View.INVISIBLE);}
    }
    }
