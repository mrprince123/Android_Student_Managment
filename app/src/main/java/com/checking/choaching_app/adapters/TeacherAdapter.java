package com.checking.choaching_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.checking.choaching_app.R;
import com.checking.choaching_app.databinding.ItemTeacherBinding;
import com.checking.choaching_app.models.Teacher;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    Context context;
    ArrayList<Teacher> teachers;

    public TeacherAdapter(Context context, ArrayList<Teacher> teachers) {
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teacher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {

        Teacher teacher = teachers.get(position);
        holder.binding.name.setText(teacher.getName());
        holder.binding.email.setText(teacher.getEmail());

        Glide.with(context)
                        .load(teacher.getTeacherImage())
                                .into(holder.binding.image);

        holder.binding.phone.setText(teacher.getPhoneNumber());
        holder.binding.subject.setText(teacher.getSubject());
        holder.binding.about.setText(teacher.getAbout());
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder {
        ItemTeacherBinding binding;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
          binding =  ItemTeacherBinding.bind(itemView);
        }
    }
}
