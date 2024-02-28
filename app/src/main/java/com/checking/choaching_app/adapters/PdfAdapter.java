package com.checking.choaching_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.checking.choaching_app.R;
import com.checking.choaching_app.activity.PdfViewerActivity;
import com.checking.choaching_app.databinding.ItemPdfBinding;
import com.checking.choaching_app.models.Pdf;

import java.util.ArrayList;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    Context context;
    ArrayList<Pdf> pdfs;

    public PdfAdapter(Context context, ArrayList<Pdf> pdfs) {
        this.context = context;
        this.pdfs = pdfs;
    }

    public void setPdfList(ArrayList<Pdf> pdfs) {
        this.pdfs = pdfs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PdfViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pdf, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {

        Pdf pdf = pdfs.get(position);

        holder.binding.pdfTitle.setText(pdf.getTitle());
        holder.binding.pdfSubject.setText("Subject : " + pdf.getSubject());


        if (pdf.getSubject().equalsIgnoreCase("math")) {
            holder.binding.pdfThumbnail.setImageResource(R.drawable.math_image);
        } else if (pdf.getSubject().equalsIgnoreCase("physics")) {
            holder.binding.pdfThumbnail.setImageResource(R.drawable.physics_image);
        } else if (pdf.getSubject().equalsIgnoreCase("biology")) {
            holder.binding.pdfThumbnail.setImageResource(R.drawable.biology_image);
        } else if (pdf.getSubject().equalsIgnoreCase("chemistry")) {
            holder.binding.pdfThumbnail.setImageResource(R.drawable.chemistry_image);
        } else {
            holder.binding.pdfThumbnail.setImageResource(R.drawable.placeholder);
        }

        holder.binding.pdfDate.setText("Upload Date : " + pdf.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdfUrl = pdf.getPdfUrl();
                Intent intent = new Intent(context, PdfViewerActivity.class);
                intent.putExtra("pdfUrl", pdfUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfs.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {
        ItemPdfBinding binding;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPdfBinding.bind(itemView);
        }
    }
}
