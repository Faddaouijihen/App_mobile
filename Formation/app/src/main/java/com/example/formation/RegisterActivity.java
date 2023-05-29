package com.example.formation;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    TextView title_text;
    EditText edtname,edtprenom, edtphone, edtmail, edtpassword;
    Button btn_Rg;
    AlertDialog.Builder builder;
    private String register_url = "http://192.168.43.149/course_app_api/register.php";
    String strname, stremail, strpass, strphone,strprenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        title_text = findViewById(R.id.title_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
        title_text.setTypeface(custom_font);
        edtpassword = findViewById(R.id.edtpassword);
        edtmail = findViewById(R.id.edtemail);
        edtname = findViewById(R.id.edtnom);
        btn_Rg =  findViewById(R.id.btn_register);
        edtphone = findViewById(R.id.edtphone);
        edtprenom = findViewById(R.id.edtprenom);

        btn_Rg.setTypeface(custom_font);





        builder = new AlertDialog.Builder(RegisterActivity.this);

        btn_Rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strname = edtname.getText().toString();
                stremail = edtmail.getText().toString();
                strpass = edtpassword.getText().toString();
                strphone = edtphone.getText().toString();
                strprenom = edtprenom.getText().toString();


                if (strname.equals("") || strphone.equals("") || stremail.equals("") || strpass.equals("") || strprenom.equals("")) {
                    builder.setTitle("ERREUR");
                    builder.setMessage("Merci de remplir toutes les champs...");
                    displayAlert("input_error0");
                } else if (!stremail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    builder.setTitle("ERREUR");
                    builder.setMessage("Email invalide ...");
                    displayAlert("input_error_email");

                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                String message = jsonObject.getString("message");
                                builder.setTitle("server response...");
                                builder.setMessage(message);
                                displayAlert(code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("nom", strname);
                            params.put("prenom", strprenom);
                            params.put("email", stremail);
                            params.put("telephone", strphone);
                            params.put("password", strpass);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    requestQueue.add(stringRequest);
                }

            }
        });
    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("input_error0")) {


                } else if (code.equals("reg_success")) {
                    finish();
                    startActivity(new Intent(RegisterActivity.this,AuthenticationActivity.class));

                } else if (code.equals("reg_failed")) {
                    edtmail.setText("");
                    edtpassword.setText("");
                    edtphone.setText("");

                } else if (code.equals("input_error_email")) {

                    edtmail.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
