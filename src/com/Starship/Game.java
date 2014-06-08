package com.Starship;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

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
  Button btnFire, btnPlace, btnGo;
  Spinner spnRow, spnColumn, spnShip, spnDirection;
  ArrayList<String> dirs;
  Ship ship = null;
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
    boardView.ships = new ArrayList<Ship>(5);

  }

  // OnClick method:
  public void addShip(View view){
    final Dialog dialog = new Dialog(Game.this);
    dialog.setTitle("Add a Ship:");
    dialog.setContentView(R.layout.add_ships);

    spnRow = (Spinner)dialog.findViewById(R.id.spnRow);
    spnColumn = (Spinner)dialog.findViewById(R.id.spnColumn);
    spnDirection = (Spinner)dialog.findViewById(R.id.spnDirection);
    spnShip = (Spinner)dialog.findViewById(R.id.spnShip);
    btnGo = (Button)dialog.findViewById(R.id.btnGo);

    // Build ArrayLists
    final ArrayList<Integer> directions = new ArrayList<Integer>();
    dirs = new ArrayList<String>();
    client.get("http://battlegameserver.com/api/v1/available_directions", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {

        if(object.has("north")){
          Log.e("north_exists:", String.valueOf(object.has("north")));
          directions.add(0);
          dirs.add("north");
        }
        if(object.has("east")){
          directions.add(2);
          dirs.add("east");
        }
        if(object.has("south")){
          directions.add(4);
          dirs.add("south");
        }
        if(object.has("west")){
          directions.add(6);
          dirs.add("west");
        }
        spnDirection.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, dirs));// Experiment 1
      }
    });

    final ArrayList<Integer> shipSize = new ArrayList<Integer>();
    final ArrayList<String> shps = new ArrayList<String>();
    client.get("http://battlegameserver.com/api/v1/available_ships", new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(JSONObject object) {
        if(object.has("carrier")){
          shipSize.add(5);
          shps.add("carrier");
        }
        if(object.has("battleship")){
          shipSize.add(4);
          shps.add("battleship");
        }
        if(object.has("cruiser")){
          shipSize.add(3);
          shps.add("cruiser");
        }
        if(object.has("submarine")){
          shipSize.add(3);
          shps.add("submarine");
        }
        if(object.has("destroyer")){
          shipSize.add(2);
          shps.add("destroyer");
        }
        spnShip.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, shps));//Experiment 2
      }
    });


    spnRow.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList( "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"))));
    spnColumn.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))));
    //Placeholder: Experiment 1
    //Placeholder: Experiment 2

    btnGo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Integer selRow = spnRow.getSelectedItemPosition();
        final Integer selCol = spnColumn.getSelectedItemPosition();
        final Integer selShip = spnShip.getSelectedItemPosition();
        final Integer selDirection = spnDirection.getSelectedItemPosition();
        Log.e("CnXn:", "http://battlegameserver.com/api/v1/game/" + game_id + "/" + "add_ship/" + shps.get(selShip)+ "/" + String.valueOf(spnRow.getSelectedItem()) + "/" + String.valueOf(selCol + 1) + "/" + String.valueOf(directions.get(selDirection)) + ".json");
        client.get("http://battlegameserver.com/api/v1/game/" + game_id + "/" + "add_ship/" + shps.get(selShip)+ "/" + String.valueOf(spnRow.getSelectedItem()) + "/" + String.valueOf(selCol + 1) + "/" + String.valueOf(directions.get(selDirection)) + ".json", new JsonHttpResponseHandler(){
          @Override
        public void onSuccess(JSONObject object){

            if (object.has("error")){
              Toast.makeText(Game.this, "Illegal ship placement!", Toast.LENGTH_LONG).show();
              dialog.dismiss();
            }
            if (object.has("status")){
              Log.e("boardView.Ships", "Count Before: " + boardView.ships.size());
              ship = new Ship(selRow + 1, selCol + 1, shipSize.get(selShip), directions.get(selDirection));
              boardView.ships.add(ship);
              Log.e("boardView.Ships", "Count After: " + boardView.ships.size());
              boardView.invalidate();
              Log.e("Game", "Successful Placement");
              dialog.dismiss();
            }
          }
        });
      }
   });

    dialog.show();
  }

  // OnClick method:
  public void runGame(View view) {
    //TODO: Show attack dialog: Build like addShipDialog.
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