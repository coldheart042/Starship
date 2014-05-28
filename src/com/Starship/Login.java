package com.Starship;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.Starship.JSONWorker;

public class Login extends Activity {
  EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
  EditText txtPassword = (EditText) findViewById(R.id.txtUsername);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // TODO: Set the Available list to refresh the roster at an interval (Makes it real-time)
  }

  public void login(View view){
    // TODO: Create interface and get url login parameters to pass into JSONWorker
    String username = String.valueOf(txtUsername.getText());
    String password = String.valueOf(txtPassword.getText());
  }
}
