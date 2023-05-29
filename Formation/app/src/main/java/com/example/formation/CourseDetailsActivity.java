package com.example.formation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class CourseDetailsActivity extends AppCompatActivity {
    TextView txt_titre_details,txt_date_details,txt_nbr_heure_details,txt_categorie_details,txt_description_details,txt_head_details;
    Button btn_participer,btn_editer,btn_supprimer;
    String profile;
    AlertDialog.Builder builder;
    String url = "http://192.168.43.149/course_app_api/deletecourse.php";
    String url_participation = "http://192.168.43.149/course_app_api/participations.php";
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        profile = sharedPreferences.getString("profile","");
        txt_titre_details = findViewById(R.id.txt_titre_details);
        txt_date_details = findViewById(R.id.txt_date_details);
        txt_nbr_heure_details = findViewById(R.id.txt_nbr_heure_details);
        txt_categorie_details = findViewById(R.id.txt_categorie_details);
        txt_description_details = findViewById(R.id.txt_description_details);
        txt_head_details = findViewById(R.id.txt_head_details);
        builder = new AlertDialog.Builder(CourseDetailsActivity.this);

        btn_participer = findViewById(R.id.btn_participer);
        btn_editer = findViewById(R.id.btn_editer);
        btn_supprimer = findViewById(R.id.btn_supprimer);
        if(profile.equals("admin"))
        {
            btn_editer.setVisibility(View.VISIBLE);
            btn_supprimer.setVisibility(View.VISIBLE);
        }else{
            btn_participer.setVisibility(View.VISIBLE);
            btn_participer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participer();
                }
            });
        }

        txt_titre_details.setText("  Titre : " + getIntent().getStringExtra("titre"));
        txt_head_details.setText(getIntent().getStringExtra("titre"));
        txt_nbr_heure_details.setText("  Nbr heure : " +getIntent().getStringExtra("nbr_heure")+ " heures");
        txt_date_details.setText("  Date début : " +getIntent().getStringExtra("date"));
        txt_categorie_details.setText("  Catégorie : " + getIntent().getStringExtra("categorie"));
        txt_description_details.setText("Description : \n" + getIntent().getStringExtra("description"));
        btn_participer.setText("Participer au " + getIntent().getStringExtra("titre") );

        Typeface home_txt_face = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
        txt_titre_details.setTypeface(home_txt_face);
        txt_nbr_heure_details.setTypeface(home_txt_face);
        txt_date_details.setTypeface(home_txt_face);
        txt_categorie_details.setTypeface(home_txt_face);
        txt_description_details.setTypeface(home_txt_face);
        txt_head_details.setTypeface(home_txt_face);
        btn_participer.setTypeface(home_txt_face);
        btn_editer.setTypeface(home_txt_face);
        btn_supprimer.setTypeface(home_txt_face);
        btn_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Voulez vous Vraiment Supprimer la formation");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_item();
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_editer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CourseDetailsActivity.this, EditActivity.class);
                i.putExtra("id",getIntent().getStringExtra("id"));
                i.putExtra("titre",getIntent().getStringExtra("titre"));
                i.putExtra("nbr_heure",getIntent().getStringExtra("nbr_heure"));
                i.putExtra("date",getIntent().getStringExtra("date"));
                i.putExtra("categorie",getIntent().getStringExtra("categorie"));
                i.putExtra("description",getIntent().getStringExtra("description"));
                startActivity(i);
            }
        });
    }

    void delete_item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toasty.success(CourseDetailsActivity.this, response, Toast.LENGTH_LONG, true).show();
                finish();
                startActivity(new Intent(CourseDetailsActivity.this,MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("id",getIntent().getStringExtra("id"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CourseDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    void participer()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id","");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_participation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toasty.success(CourseDetailsActivity.this, response, Toast.LENGTH_LONG, true).show();
                finish();
                startActivity(new Intent(CourseDetailsActivity.this,MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("formation_id",getIntent().getStringExtra("id"));
                params.put("client_id",user_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CourseDetailsActivity.this);
        requestQueue.add(stringRequest);
    }
}
