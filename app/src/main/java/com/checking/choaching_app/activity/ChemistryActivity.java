package com.checking.choaching_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.R;
import com.checking.choaching_app.adapters.VideoAdapter;
import com.checking.choaching_app.models.Video;
import com.checking.choaching_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChemistryActivity extends AppCompatActivity {

    public  VideoAdapter videoAdapter;
    ArrayList<Video> videos;
    RecyclerView chemistryVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);
        initVideo();
    }

    void initVideo() {
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(this, videos);
        chemistryVideo = findViewById(R.id.chemistry_video);
        getVideo();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chemistryVideo.setLayoutManager(layoutManager);
        chemistryVideo.setAdapter(videoAdapter);
    }

    void getVideo(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_VIDEO_URL;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject videoObj = new JSONObject(response);
                    if(videoObj.getString("success").equals("true")){
                        JSONArray videoArray = videoObj.getJSONArray("data");
                        for(int i = 0; i<videoArray.length(); i++){
                            JSONObject object = videoArray.getJSONObject(i);

                            if(object.getString("subject").equals("Chemistry")){
                                Video video = new Video(
                                        object.getString("videoUrl"),
                                        object.getString("title"),
                                        object.getString("subject"),
                                        object.getString("description")
                                );
                                videos.add(video);
                            }
                        }
                        videoAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e){
                    Log.e("ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
            }
        });
        requestQueue.add(request);
    }
}