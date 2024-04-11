package info.androidhive.loginandregistration.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncServerTask extends AsyncTask<String, Void, String>{
	
	public AsyncResponse delegate=null;
	public final static String SEND_LIST = "sendList";
	StringBuilder param=new StringBuilder();
	StringBuilder response=new StringBuilder();
	Map<String,String> respMap=new HashMap<String,String>();
	Map<String,String> sendList=null;
	boolean loopStatus=false;
	Activity act=new Activity();
	private Gson gson;
	
	AsyncServerTask(Map<String,String> sendList,boolean loopStatus,Activity act)
	{
		this.sendList=sendList;
		this.loopStatus=loopStatus;
		this.act=act;
	}
	
    public AsyncServerTask() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
    protected String doInBackground(String... params) {
    	
    	try
		{

    		
    		 URL url = new URL("http://192.168.160.116:8080/Complaint/Business");
    		 URLConnection conn = url.openConnection(); 
  
             conn.setDoInput(true);
             conn.setDoOutput(true); 
             OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
             wr.write( param.toString() ); 
             wr.flush(); 
             InputStream inputStream = null;
             if (conn != null) {
                 inputStream = conn.getInputStream();
             } 

             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     inputStream));

             String line = "";
             while ((line = reader.readLine()) != null) {
                 response.append(line);
             }
             gson=new Gson();
             
     		 Type respMapType = new TypeToken<Map<String,String>>() {}.getType();
     		 respMap=(Map<String,String>)gson.fromJson(response.toString(),respMapType);
             reader.close();        
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
        return "Executed\nasfahgsfhga";
    }

    @Override
    protected void onPostExecute(String result) {
	    	Map<String,Object> resultMap=new HashMap<String, Object>();
	    	resultMap.put("status", respMap.get("status"));
    		if(respMap.get("status").equals("lock"))
    			resultMap.put("pin", respMap.get("pin"));
		if(delegate!=null)
    		delegate.processFinish(resultMap);
    }

    @Override
    protected void onPreExecute() {
 
    	int count=1;
    	if(sendList!=null)
		for(Map.Entry<String, String> data:sendList.entrySet())
		{
			if(count!=1)
			{
				param.append("&");
			}
			try {
				param.append(URLEncoder.encode(data.getKey(), "UTF-8")+ "=" + URLEncoder.encode(data.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
		}
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    	/*super.onProgressUpdate(values);
    	TextView statusText=(TextView) act.findViewById(R.id.StatusText);
    	statusText.setText(response.toString());*/
    }
}
