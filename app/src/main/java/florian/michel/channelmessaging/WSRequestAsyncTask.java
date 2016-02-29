package florian.michel.channelmessaging;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WSRequestAsyncTask extends AsyncTask<Long, Integer, String> {

    private Context context;
    private String url;
    private HashMap<String,String> params;
    private ArrayList<OnWSUpdateListener> listeners = new ArrayList<>();

    public WSRequestAsyncTask(Context context, String url, HashMap<String,String> params) {
        this.context = context;
        this.url = url;
        this.params = params;
    }

    public void addWSRequestListener(OnWSUpdateListener listener) {
        this.listeners.add(listener);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début tâche",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer...values) {
        super.onProgressUpdate(values);

        //Toast.makeText(context, "Avancement: "+values.toString()+"/100", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(Long...arg0) {
        /********* TEST ********/
        String result = performPostCall(url, params);
        /**********************/

        /*
        double retour = 0;

        for (int i=0; i <arg0.length; i++) {
            retour += arg0[i];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            publishProgress(new Integer((i*100)/arg0.length));
        }
        */
        return result;
    }

    protected void onPostExecute(String result) {

        //Toast.makeText(context, "Somme = "+result.toString(), Toast.LENGTH_SHORT).show();

        for (OnWSUpdateListener listener : listeners) {
            listener.onWSResponseUpdate(result);
        }
    }

    private String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";

        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String,String> entry : params.entrySet()) {
            if (first) first = false;
            else result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
