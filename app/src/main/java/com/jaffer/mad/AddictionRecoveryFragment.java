package com.jaffer.mad;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddictionRecoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddictionRecoveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddictionRecoveryFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddictionRecoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddictionRecoveryFragment newInstance(String param1, String param2) {
        AddictionRecoveryFragment fragment = new AddictionRecoveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articleArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addiction_recovery, container, false);
        recyclerView = view.findViewById(R.id.addiction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articleArrayList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(articleArrayList, getContext());
        recyclerView.setAdapter(articleAdapter);

        initializeData();
        return view;
    }

    private void initializeData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://medium.com/article/when-your-addiction-recovery-stories-backfire-cc15fed7b5fd", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.w("res", "Response: " + response);
                progressDialog.dismiss();
                Document document = Jsoup.parse(response);
                Elements head = document.select("head");
                Elements scripts = head.select("script");
                Element data = scripts.last();
                Log.w("res", "CLEAN: " + data.data());
                try {
                    Log.w("res", "DATA: " + data.data());

                    JSONObject jsonObject = new JSONObject(data.data());
                    Log.w("res", "JSONOBJECT: " + jsonObject);
                    Log.w("res", "TEST: " + jsonObject.getJSONArray("image"));
                    JSONArray imageUrl = jsonObject.getJSONArray("image");
                    JSONArray creator = jsonObject.getJSONArray("creator");
                    String []title = jsonObject.getString("headline").split("-");
                    Article article = new Article(imageUrl.getString(0), title[0], creator.getString(0), jsonObject.getString("description"), "200");
//                    JSONArray array = jsonObject.getJSONArray("items");
//                    for (int i = 0; i < array.length(); i++) {
//                        jsonArray.getString(2);
//                        JSONArray jsonObjectTitle = jsonObject.getJSONArray("headline");
//                        JSONArray jsonObjectAuthor = jsonObject.getJSONArray("creator");
//                        JSONArray jsonObjectDescription = jsonObject.getJSONArray("description");
//                        Article article = new Article(jsonArray.getString(2), jsonArray.getString(7), jsonArray.getString(12), jsonArray.getString(9), "200");
                        articleArrayList.add(article);
                        Log.d("res", "article" + article);
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Tag", response);
            }
            }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

//        Article testArticle = new Article("https://miro.medium.com/max/1200/0*uO24VIR-a6NKx-0x", "Addiction Test Title", "Georgina Nyokabi", "Blah blah blah", "200");

//        for (int i = 0; i <= 6; i++){
//            articleArrayList.add(testArticle);
//        }
        articleAdapter.notifyDataSetChanged();
    }
}