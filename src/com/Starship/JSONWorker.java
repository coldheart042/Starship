package com.Starship;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Richard on 5/28/14.
 */
public class JSONWorker {
  public JSONObject getJSON(HttpPost httpPost){
    JSONObject result = null;

    // Connect to HTTP and get the JSON Object (Modified from this URL: http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android )
    DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams()); //← This is without parameters
    InputStream inputStream = null;
    try {
      HttpResponse response = httpClient.execute(httpPost);                     //← This returns a response, which is the raw data
      HttpEntity entity = response.getEntity();                                 //← This refines the raw data
      inputStream = entity.getContent();                                        //← This is now a (barely) usable form of the data

      // The setup to read and parse the raw data
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null){
        stringBuilder.append(line + "\n");
      }
      result = new JSONObject(stringBuilder.toString());                        //← This attempts to turn the String into a JSON object, ex caught
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e){
      e.printStackTrace();
    } catch (Exception e){
      e.printStackTrace();
    }finally {
      try {
        if (inputStream != null) inputStream.close();
      }catch (Exception e){
        e.printStackTrace();
      }
    }

    return result;
  }
}