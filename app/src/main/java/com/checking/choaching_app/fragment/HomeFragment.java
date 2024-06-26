package com.checking.choaching_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.activity.BiologyActivity;
import com.checking.choaching_app.activity.ChemistryActivity;
import com.checking.choaching_app.activity.MathActivity;
import com.checking.choaching_app.activity.PhysicsActivity;
import com.checking.choaching_app.adapters.PdfAdapter;
import com.checking.choaching_app.adapters.TeacherAdapter;
import com.checking.choaching_app.adapters.VideoAdapter;
import com.checking.choaching_app.databinding.FragmentHomeBinding;
import com.checking.choaching_app.models.Pdf;
import com.checking.choaching_app.models.Teacher;
import com.checking.choaching_app.models.Video;
import com.checking.choaching_app.util.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TeacherAdapter teacherAdapter;
    ArrayList<Teacher> teachers;

    VideoAdapter videoAdapter;
    ArrayList<Video> videos;

    PdfAdapter pdfAdapter;
    ArrayList<Pdf> pdfs;

    LinearLayout mathRedirect, physicsRedirect, chemistryRedirect, biologyRedirect;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // This is for the Carousel
        getCarousel();
        // This is for the all the Teachers
        initTeacher();
        // This is for the Recent Video Lacture
        initVideo();
        // This is for  the Recent PDFs
        initPDFs();
        // This is for the Teachers

        //This is for the Physics Video
        binding.physicsRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                physicsRedirect();
            }
        });
        // This is for the Math Video
        binding.mathRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mathRedirect();
            }
        });

//        This is for the Chemistry Video
        binding.chemistryRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chemistryRedirect();
            }
        });

//        This is for the Biology Video
        binding.bioRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bioRedirect();
            }
        });
        return view;

    }

    void physicsRedirect() {
        Intent intent = new Intent(getContext(), PhysicsActivity.class);
        startActivity(intent);
    }

    void mathRedirect() {
        Intent intent = new Intent(getContext(), MathActivity.class);
        startActivity(intent);
    }

    void bioRedirect() {
        Intent intent = new Intent(getContext(), BiologyActivity.class);
        startActivity(intent);
    }

    void chemistryRedirect() {
        Intent intent = new Intent(getContext(), ChemistryActivity.class);
        startActivity(intent);
    }

    void initTeacher() {
        teachers = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(getContext(), teachers);

        getTeachers();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.teacherList.setLayoutManager(layoutManager);
        binding.teacherList.setAdapter(teacherAdapter);

    }

    void getTeachers() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constants.GET_TEACHER_URL;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject teacherObj = new JSONObject(response);

                    if (teacherObj.getString("success").equals("true")) {
                        JSONArray teacherArray = teacherObj.getJSONArray("data");
                        for (int i = 0; i < teacherArray.length(); i++) {
                            JSONObject object = teacherArray.getJSONObject(i);

                            Teacher teacher = new Teacher(
                                    object.getString("name"),
                                    object.getString("email"),
                                    object.getString("teacherImgae"),
                                    object.getString("phoneNumber"),
                                    object.getString("subject"),
                                    object.getString("about")
                            );
                            teachers.add(teacher);
                        }
                        teacherAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    Log.e("ERROR", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
            }
        });
        queue.add(request);
    }

    void getCarousel() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CAROUSEL_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getString("success").equals("true")) {
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject carObj = array.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        carObj.getString("image"),
                                        carObj.getString("title")
                                )
                        );
                    }
                } else {
                    // DO nothing
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {

        });
        queue.add(request);
    }

    void initVideo() {
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(getContext(), videos);

//        videos.add(new Video("https://youtu.be/whvCIVRLUEs", "This is Basic Testing of Physics", "Physics", "This is the Small description of the Video Url that we are testing that if this is working or not if it works in be happy and test all things and go ahead."));
//        videos.add(new Video("https://youtu.be/14BL_FwQCpM", "This is Basic Testing of Math", "Math", "This is the Small description of the Video Url that we are testing that if this is working or not if it works in be happy and test all things and go ahead."));
//        videos.add(new Video("https://youtu.be/5JQi0PmcvEY", "This is Basic Testing of Biology", "Biology", "This is the Small description of the Video Url that we are testing that if this is working or not if it works in be happy and test all things and go ahead."));

        getVideo();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.videoYTList.setLayoutManager(layoutManager);
        binding.videoYTList.setAdapter(videoAdapter);
    }

    void getVideo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constants.GET_VIDEO_URL;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject videoObj = new JSONObject(response);
                    if (videoObj.getString("success").equals("true")) {
                        JSONArray videoArray = videoObj.getJSONArray("data");
                        for (int i = 0; i < videoArray.length(); i++) {
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

                } catch (JSONException e) {
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

    void initPDFs() {
        pdfs = new ArrayList<>();
        pdfAdapter = new PdfAdapter(getContext(), pdfs);

        getPdf();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.pdfList.setLayoutManager(layoutManager);
        binding.pdfList.setAdapter(pdfAdapter);
    }

    void getPdf() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constants.GET_PDFs_URL;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("err", response);

                try {
                    JSONObject pdfObj = new JSONObject(response);
                    if (pdfObj.getString("success").equals("true")) {
                        JSONArray pdfArray = pdfObj.getJSONArray("data");
                        for (int i = 0; i < pdfArray.length(); i++) {
                            JSONObject object = pdfArray.getJSONObject(i);

                            // Extracting the date and removing the time portion
                            String dateTime = object.getString("date");
                            String date = dateTime.substring(0, 10);

                            Pdf pdf = new Pdf(
                                    object.getString("title"),
                                    object.getString("subject"),
                                    date,
                                    object.getString("pdfUrl")
                            );

                            pdfs.add(pdf);
                        }
                        pdfAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e("NOT WORKING", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
            }
        });
        queue.add(request);
    }


}