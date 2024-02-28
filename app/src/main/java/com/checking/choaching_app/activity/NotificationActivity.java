package com.checking.choaching_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.adapters.NotificationAdapter;
import com.checking.choaching_app.databinding.ActivityNotificationBinding;
import com.checking.choaching_app.models.Notification;
import com.checking.choaching_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    private NotificationAdapter notificationAdapter;
    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNotification();

    }
    private void initNotification() {
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications);

        getNotification();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.notificationList.setLayoutManager(layoutManager);
        binding.notificationList.setAdapter(notificationAdapter);
    }


    void getNotification(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_NOTIFICATION_URL;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("ERR", response);

                try {
                    JSONObject notiObj = new JSONObject(response);
                    if(notiObj.getString("success").equals("true")) {
                        JSONArray notiArray = notiObj.getJSONArray("data");
                        for(int i = 0; i<notiArray.length(); i++){

                            JSONObject object = notiArray.getJSONObject(i);

                            // Extracting the date and removing the time portion
                            String dateTime = object.getString("date");
                            String date = dateTime.substring(0, 10);

                            Notification notification = new Notification(
                                    object.getString("imgUrl"),
                                    object.getString("title"),
                                    date,
                                    object.getString("message")
                            );
                            notifications.add(notification);
                        }
                        notificationAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e("ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do Nothing
            }
        });
        requestQueue.add(request);
    }
}
