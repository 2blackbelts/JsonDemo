package com.tafe.mcintosh.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGet = (Button) findViewById(R.id.btnGet);
        tvData = (TextView) findViewById(R.id.tvJsonItem);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONtask().execute("https://andruxnet-random-famous-quotes.p.mashape.com/?cat=movies&count=1&mashape-key=0nZqGB4kl6mshWPD2bIKxsJpelwbp1x4Un5jsniy5Ypj9wuwY7");
            }
        }
        );

    }

    public class JSONtask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                // Parse JSON
//                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("movies");
//                JSONObject finalObject = parentArray.getJSONObject(0);
//                String movieName = finalObject.getString("movie");
//                int year = finalObject.getInt("year");
//
//                return movieName + " (" + year + ")";

//                return buffer.toString();

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                String quote = parentObject.getString("quote");
                String author = parentObject.getString("author");

                return "\"" + quote + "\" was said by " + author;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);
        }
    }
}



