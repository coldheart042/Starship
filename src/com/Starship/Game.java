package com.Starship;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
  BoardView boardView;
  AttackView attackView;
  Button btnFire, btnPlace;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game);

    boardView = (BoardView)findViewById(R.id.BoardView);
    attackView = (AttackView)findViewById(R.id.AttackView);
    client = new AsyncHttpClient();
    player = getSharedPreferences("player", MODE_PRIVATE);
    editor = player.edit();
    username = player.getString("email", "");
    password = player.getString("password", "");
    game_id = player.getInt("game_id", 0);
    client.setBasicAuth(username, password);
    btnFire = (Button)findViewById(R.id.btnFire);
    btnPlace = (Button)findViewById(R.id.btnPlace);
    btnFire.setEnabled(false);
    //Toast.makeText(this,String.valueOf(game_id),Toast.LENGTH_LONG).show(); //Works!!!
    boardView.ships = new ArrayList<Ship>(5);
  }
  // OnClick method:
  public void addShip(View view){
    final Dialog addShipDialog = new Dialog(Game.this);
    addShipDialog.setContentView(R.layout.add_ship_dialog);
    addShipDialog.setTitle("Add a ship");
    Spinner spnRow = (Spinner)findViewById(R.id.spnRow), spnColumn = (Spinner)findViewById(R.id.spnColumn), spnShip = (Spinner)findViewById(R.id.spnShip), spnDirection = (Spinner)findViewById(R.id.spnDirection);

    //Get data:
    final ArrayList<String> ships = new ArrayList<String>(5);
    client.get("http://battlegameserver.com/api/v1/available_ships", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
       // try {
          if (object.has("carrier")) ships.add("Carrier");
          if (object.has("battleship")) ships.add("Battleship");
          if (object.has("cruiser")) ships.add("Cruiser");
          if (object.has("submarine")) ships.add("Submarine");
          if (object.has("destroyer")) ships.add("Destroyer");
       // } catch (JSONException e) { //Unneeded try..catch
        //  e.printStackTrace();
       // }
      }
    });
    //Set adapters
    spnRow.setAdapter(new ArrayAdapter<String>(Game.this, android.R.layout.simple_spinner_item, boardView.letters));
    spnColumn.setAdapter(new ArrayAdapter<String>(Game.this, android.R.layout.simple_spinner_item, boardView.numbers));
    spnShip.setAdapter(new ArrayAdapter<String>(Game.this, android.R.layout.simple_spinner_item, ships));

    //TODO: In dialog, build and run the call to the place_ship method
    //TODO: Check if placement is illegal
    //TODO: Update grid
  }
  // OnClick method:
  public void runGame(View view) {
    //TODO: Show attack dialog
    //TODO: In attack dialog, build and run the call to the attack method
      //TODO: Check if the attack has already been made - if so, Toast and re-enter - if not, Save and Send.
    //TODO: Update the grid (add hits and misses)
  }

  // OnClick method:
  public void quit_game(View view){
    Intent intent = new Intent(Game.this, Profile.class);
    startActivity(intent);
    finish(); // Kills the activity, so you can't return to this screen w/ the back button :)
    Toast.makeText(Game.this, "Thanks for playing!", Toast.LENGTH_LONG).show();
  }
}