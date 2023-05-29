package com.example.formation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EditActivity extends AppCompatActivity {
    DatePickerDialog picker;
    ImageView img_get_date;
    TextView txt_date;
    String[] formations = { "JAVA", "JS", "C", "CSS", "PHP"};
    Spinner spin;
    String formation;
    Button btn_add;
    TextView txt_head_ajout;
    EditText edt_titre,edt_nombre_heure,edt_description;

    String str_titre,str_description,str_nbr_heure,str_date;
    String url = "http://192.168.43.149/course_app_api/editcourse.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        img_get_date = findViewById(R.id.img_get_date_edit);
        txt_date = findViewById(R.id.txt_date_edit);
        spin =   findViewById(R.id.sp_categorie_edit);
        btn_add =   findViewById(R.id.btn_edit);
        txt_head_ajout = findViewById(R.id.txt_head_edit);

        Typeface home_txt_face = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
        txt_head_ajout.setTypeface(home_txt_face);
        btn_add.setTypeface(home_txt_face);

        edt_titre = findViewById(R.id.edt_titre_edit);
        edt_nombre_heure = findViewById(R.id.edt_nombre_heure_edit);
        edt_description = findViewById(R.id.edt_description_edit);

        edt_titre.setText(getIntent().getStringExtra("titre"));
        edt_description.setText(getIntent().getStringExtra("description"));
        edt_nombre_heure.setText(getIntent().getStringExtra("nbr_heure"));
        txt_date.setText(getIntent().getStringExtra("date"));



        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,formations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formation = (String) spin.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                formation = (String) spin.getItemAtPosition(1);
            }
        });

        img_get_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txt_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_titre = edt_titre.getText().toString();
                str_date = txt_date.getText().toString();
                str_nbr_heure = edt_nombre_heure.getText().toString();
                str_description = edt_description.getText().toString();
                if(str_titre.equals("") || str_description.equals("")|| str_nbr_heure.equals(""))
                {
                    Toasty.error(EditActivity.this, "Merci de remplire toutes les champs !", Toast.LENGTH_LONG, true).show();
                }else if(str_date.equals("00-00-0000") ){
                    Toasty.error(EditActivity.this, "Merci d'entrer une date valide", Toast.LENGTH_LONG, true).show();
                }else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toasty.success(EditActivity.this, response, Toast.LENGTH_LONG, true).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(EditActivity.this, error.getMessage(), Toast.LENGTH_LONG, true).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> params = new HashMap<>();
                            params.put("id",getIntent().getStringExtra("id"));
                            params.put("titre",str_titre);
                            params.put("description",str_description);
                            params.put("nbr_heure",str_nbr_heure);
                            params.put("date",str_date);
                            params.put("formation",formation);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
                    requestQueue.add(stringRequest);

                }
            }
        });


    }
}