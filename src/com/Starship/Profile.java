package com.Starship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Richard on 5/28/14.
 */
public class Profile extends Activity {
  TextView lblPlayerName, lblAvatarName, lblLevel, lblCoins, lblWins, lblLosses, lblTies, lblExperience, lblEmail;
  AsyncHttpClient client;
  String username, password;
  SharedPreferences player;
  SharedPreferences.Editor editor;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile);
    lblPlayerName = (TextView) findViewById(R.id.lblPlayerName);
    lblAvatarName = (TextView) findViewById(R.id.lblAvatarName);
    lblCoins = (TextView) findViewById(R.id.lblCoins);
    lblEmail = (TextView) findViewById(R.id.lblEmail);
    lblExperience = (TextView) findViewById(R.id.lblXP);
    lblLevel = (TextView) findViewById(R.id.lblLevel);
    lblLosses = (TextView) findViewById(R.id.lblLosses);
    lblTies = (TextView) findViewById(R.id.lblTies);
    lblWins = (TextView) findViewById(R.id.lblWins);

    player = getSharedPreferences("player", MODE_PRIVATE);
    username = player.getString("email", "No Email Available");
    password = player.getString("password", "NoPassword");
    editor = player.edit();
    client = new AsyncHttpClient();
    client.setBasicAuth(username, password);

    setLabels();
  }

  private void setLabels() {
    client.get("http://battlegameserver.com/api/v1/login", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
        try {
          lblPlayerName.setText((object.getString("first_name") + " " + (object.getString("last_name"))));
          lblAvatarName.setText(object.getString("avatar_name"));
          lblLevel.setText(String.valueOf(object.getInt("level")));
          lblCoins.setText(String.valueOf(object.getInt("coins")));
          lblWins.setText(String.valueOf(object.getInt("battles_won")));
          lblTies.setText( String.valueOf(object.getInt("battles_tied")));
          lblLosses.setText( String.valueOf(object.getInt("battles_lost")));
          lblExperience.setText( String.valueOf(object.getInt("experience_points")));
          lblEmail.setText(username);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      });
  }

  public void logout(View view){
    client.get("http://battlegameserver.com/api/v1/logout", new AsyncHttpResponseHandler());

    editor.remove("email");
    editor.remove("password");
    editor.commit();
    Toast.makeText(Profile.this, "You have been logged out!", Toast.LENGTH_LONG).show();
    Intent backToLogin = new Intent(Profile.this, Login.class);
    startActivity(backToLogin);
    finish();
  }

  public void newGame(View view){
    client.get("http://battlegameserver.com/api/v1/challenge_computer.json", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object){
        try{
          int game_id = object.getInt("game_id");
          editor.putInt("game_id", game_id);
          editor.commit();
        }catch(JSONException e){e.printStackTrace();}
      }
    });
    Intent intent = new Intent(Profile.this,Game.class);
    startActivity(intent);
  }
}