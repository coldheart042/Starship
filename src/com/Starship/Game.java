package com.Starship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 5/31/14.
 */
public class Game extends Activity {
  AsyncHttpClient client;
  String username, password;
  int game_id;
  SharedPreferences player;
  SharedPreferences.Editor editor;
  public Spinner spnShips, spnDirections;
  List<String> list, sList;
  ArrayAdapter adapter = null, sAdapter = null;



  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game);

    client = new AsyncHttpClient();
    player = getSharedPreferences("player", MODE_PRIVATE);
    editor = player.edit();
    username = player.getString("email", "");
    password = player.getString("password", "");
    game_id = player.getInt("game_id", 0);
    client.setBasicAuth(username, password);
    spnShips = (Spinner) findViewById(R.id.spnShips);
    spnDirections = (Spinner) findViewById(R.id.spnDirections);


    getAvailableShips();
    getAvailableDirections();
    runGame();
  }

  private void runGame() {
    //TODO: Write the turn algorithm
  }

  private void getAvailableDirections() {

    client.get("http://battlegameserver.com/api/v1/available_directions", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
        try {

          //Clear the adapter before 'refreshing'
          if (adapter != null){
            adapter.clear();
          }
          //Toast.makeText(Game.this, "North: " + String.valueOf(object.getInt("north")), Toast.LENGTH_LONG).show(); //Testing
          list = new ArrayList<String>();
          list.add("North: " + String.valueOf(object.getInt("north")));
          list.add("South: " + String.valueOf(object.getInt("south")));
          list.add("East: " + String.valueOf(object.getInt("east")));
          list.add("West: " + String.valueOf(object.getInt("west")));
          adapter = new ArrayAdapter(Game.this, android.R.layout.simple_spinner_item, list);
          spnDirections.setAdapter(adapter);

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }
  private void getAvailableShips() {
    client.get("http://battlegameserver.com/api/v1/available_ships", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
        try {

          //Clear the adapter before 'refreshing'
          if (sAdapter != null){
            sAdapter.clear();
          }
          //Toast.makeText(Game.this, "North: " + String.valueOf(object.getInt("north")), Toast.LENGTH_LONG).show(); //Testing
          sList = new ArrayList<String>();
          sList.add("Carrier: " + String.valueOf(object.getInt("carrier")));
          sList.add("Battleship: " + String.valueOf(object.getInt("battleship")));
          sList.add("Cruiser: " + String.valueOf(object.getInt("cruiser")));
          sList.add("Submarine: " + String.valueOf(object.getInt("submarine")));
          sList.add("Destroyer: " + String.valueOf(object.getInt("destroyer")));
          sAdapter = new ArrayAdapter(Game.this, android.R.layout.simple_spinner_item, sList);
          spnShips.setAdapter(sAdapter);

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void quit_game(View view){
    Intent intent = new Intent(Game.this, Profile.class);
    startActivity(intent);
    finish(); // Kills the activity, so you can't return to this screen w/ the back button :)
    Toast.makeText(Game.this, "Thanks for playing!", Toast.LENGTH_LONG).show();
  }
}