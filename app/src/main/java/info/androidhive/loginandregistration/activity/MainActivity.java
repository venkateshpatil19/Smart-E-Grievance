package info.androidhive.loginandregistration.activity;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

import android.support.v4.content.ContextCompat;


public class MainActivity extends Activity  implements View.OnClickListener {
    private static final String serverip = "http://192.168.160.116:8080/Complaint";

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister, update;
    static String username="";
    private EditText address;
    private EditText description;
    private String complaintType;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private RadioButton a, b, c, d, e, f,g,h;
    LocationManager locationManager;
Button b1,bugc;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();


                takePhoto();


    }
    private Button btnLinkToRegister,viewComplaint,info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar(toolbar);
        bugc = (Button) findViewById(R.id.viewImpoComplaint);

        a = (RadioButton) findViewById(R.id.a);
        b = (RadioButton) findViewById(R.id.b);
        c = (RadioButton) findViewById(R.id.c);
        d = (RadioButton) findViewById(R.id.d);
        e = (RadioButton) findViewById(R.id.e);
        f = (RadioButton) findViewById(R.id.f);
        g = (RadioButton) findViewById(R.id.g);
        h = (RadioButton) findViewById(R.id.h);
        b1= (Button) findViewById(R.id.btnPic);
        b1.setOnClickListener(this);
        viewComplaint = (Button) findViewById(R.id.viewComplaint);

        // Creating adapter for spinner
        viewComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        viewComplaint.class);
                startActivity(intent);
                // finish();
            }
        });
        bugc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        viewImpComplaint.class);
                startActivity(intent);
                // finish();
            }
        });

        // attaching data adapter to spinner
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        address = (EditText) findViewById(R.id.address);
        description = (EditText) findViewById(R.id.description);
        //complaintType = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
        btnRegister = (Button) findViewById(R.id.btnRegister);
        update = (Button) findViewById(R.id.update);
        int flag = 0;
        if ("Admin".equalsIgnoreCase(MainActivity.username)) {
            flag = 1;
            update.setVisibility(View.VISIBLE);
        }else
        if ("Electricity".equalsIgnoreCase(MainActivity.username)) {
            flag = 1;
            update.setVisibility(View.VISIBLE);
        } else if ("Water".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);
            flag = 1;
        } else if ("Garbage".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);
            flag = 1;
        } else if ("Parking".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);
            flag = 1;
        } else if ("Sound".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);
            flag = 1;
        } else if ("Others".equalsIgnoreCase(MainActivity.username)) {
        update.setVisibility(View.VISIBLE);

        flag = 1;
    }
        else if ("gasleak".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);

            flag = 1;
        }
        else if ("waterleak".equalsIgnoreCase(MainActivity.username)) {
            update.setVisibility(View.VISIBLE);

            flag = 1;
        }
        if (flag == 1) {
            address.setVisibility(View.INVISIBLE);
            description.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
            //address.setVisibility(View.INVISIBLE);
            a.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            c.setVisibility(View.INVISIBLE);
            d.setVisibility(View.INVISIBLE);
            e.setVisibility(View.INVISIBLE);
            f.setVisibility(View.INVISIBLE);
            g.setVisibility(View.INVISIBLE);
            h.setVisibility(View.INVISIBLE);
            b1.setVisibility(View.INVISIBLE);

        } else {
            update.setVisibility(View.INVISIBLE);
            bugc.setVisibility(View.INVISIBLE);
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        viewComplaint.class);
                startActivity(intent);

            }
        });
        GPSTracker gpsTracker = new GPSTracker(MainActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
       address.setText(latitude +","+latitude);

    // Progress dialog
    pDialog =new  ProgressDialog(this);
        pDialog.setCancelable(false);

    // Session manager
    session =new  SessionManager(getApplicationContext());

    // SQLite database handler
    db =new

    SQLiteHandler(getApplicationContext());
    // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener()

    {
        public void onClick (View view){
        String strAddress = address.getText().toString().trim();
        String descriptionstr = description.getText().toString().trim();
        String complainstr = "";
        if (a.isChecked() == true)
            complainstr += "Electricity";
        if (b.isChecked() == true)
            complainstr += "Water";
        if (c.isChecked() == true)
            complainstr += "Garbage";
        if (d.isChecked() == true)
            complainstr += "Parking";
        if (e.isChecked() == true)
            complainstr += "Sound";
        if (g.isChecked() == true)
                complainstr += "Waterleak";
        if (h.isChecked() == true)
                complainstr += "Gasleak";
        if (f.isChecked() == true)
            complainstr += "Others";

        if (!strAddress.isEmpty() && !descriptionstr.isEmpty() && !complainstr.isEmpty()) {
            registerUser(strAddress, descriptionstr, complainstr);
        } else {
            Toast.makeText(getApplicationContext(),
                    "All Fields are Mandatory!", Toast.LENGTH_LONG)
                    .show();
        }
    }
    });


    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                      /*  String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);*/

                        Toast.makeText(getApplicationContext(), "Duplicate Complaint!!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        Toast.makeText(getApplicationContext(), "Complaint Added Successfully!", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "addComplaint");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("username", MainActivity.username);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void takePhoto() {
//		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//		startActivityForResult(intent, 0);
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onActivityResult: " + this);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            setPic();
//			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//			if (bitmap != null) {
//				mImageView.setImageBitmap(bitmap);
//				try {
//					sendPhoto(bitmap);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
        }
    }

    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);
    }

    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        protected Void doInBackground(Bitmap... bitmaps) {
            if (bitmaps[0] == null)
                return null;
            setProgress(0);

            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httppost = new HttpPost(
                        serverip + "/Upload"); // server

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("myFile",
                        System.currentTimeMillis() + ".jpg", in);
                httppost.setEntity(reqEntity);

                Log.i(TAG, "request " + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    response = httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(MainActivity.this, e +"", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(MainActivity.this, e +"", Toast.LENGTH_LONG).show();
                }
                try {
                    if (response != null) {
                        Log.i(TAG, "response " + response.getStatusLine().toString());
                       /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(serverip +"/Upload.jsp"));
                        startActivity(browserIntent);*/
                    }
                } finally {

                }
            } finally {

            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i(TAG, "onResume: " + this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;
    File photoFile = null;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * http://developer.android.com/training/camera/photobasics.html
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
      //  int targetW = mImageView.getWidth();
       // int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
       // int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
     //   bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Matrix mtx = new Matrix();
        mtx.postRotate(90);
        // Rotating Bitmap
        Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap)
            bitmap.recycle();

       // mImageView.setImageBitmap(rotatedBMP);

        try {
            sendPhoto(rotatedBMP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.viewComplaint:
                startActivity(new Intent(this, ViewUserComplaint.class));
                return true;

            case R.id.logout:
                //signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
