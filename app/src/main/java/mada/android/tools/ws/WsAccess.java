package mada.android.tools.ws;

import android.os.AsyncTask;
import android.util.Log;


//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WsAccess  extends AsyncTask<String, Integer, String> {
    //private ArrayList<NameValuePair> parameters;
    private String returnString = null;
    private String method = "GET";
    public JSONObject jsonParams = null;
    private WsResponseHandler responseHandler;

    public WsAccess(String method, WsResponseHandler responseHandler){
        super();
        this.method = method;
        this.responseHandler = responseHandler;
        //parameters = new ArrayList<NameValuePair>();
    }


    public void addParam(String key, String valeur){
        //parameters.add(new BasicNameValuePair(key, valeur));

    }


    private void addJsonParams(HttpURLConnection conn) throws Exception{

        conn.setDoOutput(true);

        Log.i("JSON", jsonParams.toString());
        DataOutputStream dos = null;
        try {
            dos =new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            dos.writeBytes(jsonParams.toString());
            dos.flush();
        }catch (Exception e){
            throw e;
        }finally{
            if(dos != null) dos.close();
        }
    }

    private void readJsonResponseBody(HttpURLConnection conn) throws Exception{

        InputStream in = null;
        try{
            in = conn.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                returnString += (char) data;
                data = isw.read();
            }
        }catch(Exception e){
            throw e;
        }finally{
            if(in !=null) in.close();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        returnString = "";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);

                //open a URL coonnection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");

                //Add json body if necessary
                if(jsonParams != null) addJsonParams(urlConnection);

                //Read json response body
                readJsonResponseBody(urlConnection);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result){
        responseHandler.onRequestFinished( (returnString.toString()));
    }
    public static void executeGet(String link,  WsResponseHandler responseHandler){
        WsAccess access = new WsAccess("GET", responseHandler);
        access.execute(link);

    }

    public static void executePost(String link, JSONObject params, WsResponseHandler responseHandler){
        WsAccess access = new WsAccess("POST", responseHandler);
        access.jsonParams = params;
        access.execute(link);

    }
}
