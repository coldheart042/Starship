package com.Starship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends Activity {
  EditText txtUsername;
  EditText txtPassword;
  ListView lvwAvailable;
  ArrayAdapter<String> arrayAdapter;
  AsyncHttpClient client;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    txtUsername = (EditText) findViewById(R.id.txtUsername);
    txtPassword = (EditText) findViewById(R.id.txtPassword);
    lvwAvailable = (ListView) findViewById(R.id.lvwAvailable);
    final ArrayList<String> list = new ArrayList<String>();

    client = new AsyncHttpClient();
    client.get("http://battlegameserver.com/api/v1/available_users", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONArray users) {
        if (users != null){        // Do nothing if users is null
          int length = users.length();
          for (int i = 0; i < length; i++){
            try {
              list.add(users.getString(i));
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
          arrayAdapter = new ArrayAdapter<String>(Login.this, R.layout.main, list);
          lvwAvailable.setAdapter(arrayAdapter);
        }
      }
    });
  }

  public void login(View view){
    final String username = String.valueOf(txtUsername.getText());
    final String password = String.valueOf(txtPassword.getText());
    client = new AsyncHttpClient();
    client.setBasicAuth(username, password);
    client.get("http://battlegameserver.com/api/v1/login", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
        try {
          String firstName = object.getString("first_name");
          String lastName = object.getString("last_name");
          String avatarName = object.getString("avatar_name");
          int level = object.getInt("level");
          int coins = object.getInt("coins");
          int battlesWon = object.getInt("battles_won");
          int battlesTied = object.getInt("battles_tied");
          int battlesLost = object.getInt("battles_lost");
          int xp = object.getInt("experience_points");

          Toast.makeText(Login.this,"Welcome, " + firstName + "!", Toast.LENGTH_LONG).show();

          SharedPreferences player = getSharedPreferences("player", MODE_PRIVATE);
          SharedPreferences.Editor editor = player.edit();
          // Save only Credentials for now...
          editor.putString("email", username);
          editor.putString("password", password);
          editor.commit();

          Intent intent = new Intent(Login.this, Profile.class);
          startActivity(intent);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }
    );
  }
}