package com.example.formation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private String JSON_URL = "http://192.168.43.149/course_app_api/allcourses.php";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    Formation formation;
    private List<Formation> formationList = new ArrayList<>();
    private RecyclerView recyclerView;
    Toolbar toolbar;
    FormationAdapter adapter;
    String profile;
    Button btn_ajouter_formation,btn_mes_formation;
    TextView txt_head_auth;
    String prenom;
    LinearLayout online_holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        btn_ajouter_formation =  findViewById(R.id.btn_ajouter_formation);
        btn_mes_formation =  findViewById(R.id.btn_mes_formation);
        txt_head_auth =  findViewById(R.id.txt_head_auth);
        online_holder =  findViewById(R.id.online_holder);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                online_holder.setVisibility(View.VISIBLE);
            }
        });
        profile = sharedPreferences.getString("profile","");
        prenom = sharedPreferences.getString("prenom","");
        online_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                online_holder.setVisibility(View.GONE);
            }
        });
        txt_head_auth.setText(" En Ligne: " + prenom);
        if(profile.equals("admin"))
        {
            Typeface home_txt_face = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
            btn_ajouter_formation.setVisibility(View.VISIBLE);
            btn_ajouter_formation.setTypeface(home_txt_face);
            txt_head_auth.setTypeface(home_txt_face);
            btn_ajouter_formation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,AddCourseActivity.class);
                    startActivity(i);
                }
            });
        }else{
            Typeface home_txt_face = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
            btn_mes_formation.setVisibility(View.VISIBLE);
            txt_head_auth.setTypeface(home_txt_face);
            btn_mes_formation.setTypeface(home_txt_face);
            btn_mes_formation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,MyCoursesActivity.class);
                    startActivity(i);
                }
            });
        }
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
        jsonArrayRequest = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
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
                adapter = new FormationAdapter(MainActivity.this, formationList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
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
