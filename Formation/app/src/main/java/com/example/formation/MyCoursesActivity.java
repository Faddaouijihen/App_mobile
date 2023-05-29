package com.example.formation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCoursesActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private String JSON_URL = "http://192.168.43.149/course_app_api/mycourses.php";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    Formation formation;
    private List<Formation> formationList = new ArrayList<>();
    private RecyclerView recyclerView;
    Toolbar toolbar;
    FormationAdapter adapter;
    String profile;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        recyclerView = (RecyclerView) findViewById(R.id.mes_recyclerview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs",MODE_PRIVATE);

        jsonCall();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void jsonCall() {
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs", MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        StringRequest jsonArrayRequest =  new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    // JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            formation = new Formation();
                            formation.setId(jsonObject.getInt("id") + "");
                            formation.setTitre(jsonObject.getString("titre"));
                            formation.setCategorie(jsonObject.getString("categorie"));
                            formation.setDescription(jsonObject.getString("description"));
                            formation.setNbr_heure(jsonObject.getString("nbr_heure"));
                            formation.setDate(jsonObject.getString("date"));

                            formationList.add(formation);


                        } catch (Exception e) {

                        }

                    }
                    adapter = new FormationAdapter(MyCoursesActivity.this, formationList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyCoursesActivity.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyCoursesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("client_id", user_id);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(MyCoursesActivity.this);
        requestQueue.add(jsonArrayRequest);


    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String useriput = s.toLowerCase();
        List<Formation> newList = new ArrayList<Formation>();
        for (Formation items : formationList) {
            if ((items.getCategorie().toLowerCase().contains(useriput)) || (items.getTitre().toLowerCase().contains(useriput))) {
                newList.add(items);
            }
        }
        adapter.filterlist(newList);
        return true;
    }




}
