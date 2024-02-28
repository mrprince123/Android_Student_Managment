package com.checking.choaching_app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.R;
import com.checking.choaching_app.adapters.VideoAdapter;
import com.checking.choaching_app.databinding.FragmentDashboardBinding;
import com.checking.choaching_app.databinding.FragmentHomeBinding;
import com.checking.choaching_app.models.Video;
import com.checking.choaching_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    VideoAdapter videoAdapter;
    ArrayList<Video> videos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initVideo();

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchSubject = binding.searchEditText.getText().toString();
                searchVideoBySubject(searchSubject);
            }
        });
        return view;
    }

    void initVideo() {
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(getContext(), videos);

        getVideo();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.videoYTList.setLayoutManager(layoutManager);
        binding.videoYTList.setAdapter(videoAdapter);
    }

    void getVideo(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                            Video video = new Video(
                                    object.getString("videoUrl"),
                                    object.getString("title"),
                                    object.getString("subject"),
                                    object.getString("description")
                            );
                            videos.add(video);
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

    void searchVideoBySubject(String subject) {
        ArrayList<Video> filteredVideos = new ArrayList<>();
        String trimmedSubject = subject.trim();

        for (Video video : videos) {
            if (video.getSubject().equalsIgnoreCase(trimmedSubject)) {
                filteredVideos.add(video);
            } else {
                binding.dataNotFound.setText("No Video found with this subject");
            }
        }
        videoAdapter.setVideoList(filteredVideos);
    }
}