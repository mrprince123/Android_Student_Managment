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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checking.choaching_app.adapters.PdfAdapter;
import com.checking.choaching_app.databinding.FragmentPdfBinding;
import com.checking.choaching_app.models.Pdf;
import com.checking.choaching_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PdfFragment extends Fragment {
    PdfAdapter pdfAdapter;
    ArrayList<Pdf> pdfs;
    private FragmentPdfBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initPdf();

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = binding.searchEditText.getText().toString();
                searchPdf(searchText);
            }
        });

        return view;
    }

    void initPdf() {
        pdfs = new ArrayList<>();
        pdfAdapter = new PdfAdapter(getContext(), pdfs);

//        pdfs.add(new Pdf("Electric Charges and Fields", "Physics", "23/05/2003", "https://ncert.nic.in/textbook/pdf/leph101.pdf"));
//        pdfs.add(new Pdf("This is the basic Formula of Math", "Math", "23/05/2003", "https://www.handakafunda.com/wp-content/uploads/2017/11/HKFMathFundas.pdf"));
//        pdfs.add(new Pdf("Chemistry in Everyday Life", "Chemistry", "23/05/2003", "https://ncert.nic.in/textbook/pdf/lech207.pdf"));
//        pdfs.add(new Pdf("Reproductive Health", "Biology", "23/05/2003", "https://ncert.nic.in/textbook/pdf/lebo103.pdf"));

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
                    if(pdfObj.getString("success").equals("true")){
                        JSONArray pdfArray = pdfObj.getJSONArray("data");
                        for(int i = 0; i<pdfArray.length(); i++){
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
                } catch (JSONException e){
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

    void searchPdf(String searchText) {
        ArrayList<Pdf> filteredPdfs = new ArrayList<>();
        boolean pdfsFound = false;


        String trimmedSearchText = searchText.trim();

        for (Pdf pdf : pdfs) {
            if (pdf.getDate().equals(trimmedSearchText) || pdf.getSubject().equalsIgnoreCase(trimmedSearchText)) {
                filteredPdfs.add(pdf);
                pdfsFound = true;
            }
        }

        if (!pdfsFound) {
            binding.dataNotFound.setText("No PDFs found matching the search criteria.");
        } else {
            binding.dataNotFound.setVisibility(View.INVISIBLE);
        }

        pdfAdapter.setPdfList(filteredPdfs);
    }


}
