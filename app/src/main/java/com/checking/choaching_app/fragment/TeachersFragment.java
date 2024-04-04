package com.checking.choaching_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.R;
import com.checking.choaching_app.adapters.TeacherAdapter;
import com.checking.choaching_app.databinding.FragmentTeachersBinding;
import com.checking.choaching_app.models.Teacher;
import com.checking.choaching_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeachersFragment extends Fragment {
    private FragmentTeachersBinding binding;
    TeacherAdapter teacherAdapter;
    ArrayList<Teacher> teachers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTeachersBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();

        initTeacher();
        return  view;
    }
    void initTeacher() {
        teachers = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(getContext(), teachers);
        getTeachers();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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

                    if(teacherObj.getString("success").equals("true")){
                        JSONArray teacherArray = teacherObj.getJSONArray("data");
                        for(int i = 0; i<teacherArray.length(); i++){
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
        queue.add(request);
    }
}