package florian.michel.channelmessaging.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.listchannels.ListChannelsActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnWSUpdateListener {

    private EditText login_id;
    private EditText pass_id;
    private Button validate_button;
    private HashMap logs = new HashMap();

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String PREFS_FILE = "PREFS_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        validate_button = (Button) findViewById(R.id.validate_button);
        login_id = (EditText) findViewById(R.id.login_id);
        pass_id = (EditText) findViewById(R.id.pass_id);
        validate_button.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {

        //logs.put("username", login_id.getText().toString());
        //logs.put("password", pass_id.getText().toString());

        /*************** TEST USAGE ****************/
        logs.put("username","fmich");
        logs.put("password","florianmichel");
        /******************************************/

        WSRequestAsyncTask request = new WSRequestAsyncTask(this.getApplicationContext(),"http://www.raphaelbischof.fr/messaging/?function=connect", logs);
        request.addWSRequestListener(this);
        request.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        ConnectionResponse token = gson.fromJson(response, ConnectionResponse.class);
        Log.d("reponse:", response);

        if (token.getCode().equals("200") && token.getResponse().equals("Ok")) {
            SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(ACCESS_TOKEN, token.getAccesstoken());

            editor.commit();

            Intent listChanAct = new Intent(getApplicationContext(), ListChannelsActivity.class);

            startActivity(listChanAct);
        }
    }
}
