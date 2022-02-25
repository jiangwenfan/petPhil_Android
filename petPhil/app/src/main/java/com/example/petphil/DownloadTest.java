package com.example.petphil;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTest extends AppCompatActivity {
    TextView response_text;
    TextView response2_text;
    TextView json_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_test);
        response_text = (TextView) findViewById(R.id.response_text);
        response2_text = (TextView)findViewById(R.id.response2_text);
        json_show = (TextView)findViewById(R.id.json_show); //json show control
        //原生
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try{
                            URL url = new URL("https://www.baidu.com");
                            connection = (HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();

                            //
                            reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null){
                                response.append(line);
                            }
                            showResponse(response.toString());

                        }catch(Exception e){
                            e.printStackTrace();
                        }finally {
                            if(reader != null){
                                try{
                                    reader.close();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }
                            if(connection != null){
                                connection.disconnect();
                            }
                        }
                    }
                }
        ).start();

        //okhttp
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://www.pwall.icu").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse2(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        //get jason data
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://www.pwall.icu/data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //解析响应体的字符串
                    parseJSONWithJsonObject(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                response_text.setText(response);
            }
        });
    }
    public void showResponse2(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                response2_text.setText(response);
            }
        });
    }
    //解析json数据
    public void parseJSONWithJsonObject(String jsondata){
        try{
            //JSONArray jsonArray =new JSONArray(jsondata);
            JSONObject jsonObject = new JSONObject(jsondata);
            String xiao123 = jsonObject.getString("xiao123");

            JSONObject xiao123json = new JSONObject(xiao123);
            String contacts = xiao123json.getString("contacts");

            JSONArray contactsArray = new JSONArray(contacts);
            for(int i=0;i<contactsArray.length();i++){
                System.out.println("----json---"+contactsArray.toString());
                //----json---["wang123","wang321","wang000"]
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
