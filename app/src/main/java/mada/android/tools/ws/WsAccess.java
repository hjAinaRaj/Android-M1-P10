package mada.android.tools.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class WsAccess  extends AsyncTask<String, Integer, Long> {

    private String returnString = null;
    private ArrayList<NameValuePair> parameters;

    private WsResponseHandler responseHandler;

    public WsAccess(){
        parameters = new ArrayList<NameValuePair>();
    }


    public void addParam(String key, String valeur){
        parameters.add(new BasicNameValuePair(key, valeur));

    }


    @Override
    protected Long doInBackground(String... strings) {
        HttpPost cxParams= new HttpPost(strings[0]);
        HttpClient client = new DefaultHttpClient();

        try{
            //encoding the parameters
            cxParams.setEntity(new UrlEncodedFormEntity(parameters));
            //Fetch response
            HttpResponse response = client.execute(cxParams);
            //Response transformation
            returnString = EntityUtils.toString(response.getEntity());

        }catch(Exception e){
            Log.d("Erreur", "****************"+e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long result){
        this.responseHandler.onRequestFinished(this.returnString);
    }
}
