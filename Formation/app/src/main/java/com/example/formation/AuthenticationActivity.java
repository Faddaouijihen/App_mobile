package com.example.formation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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


public class AuthenticationActivity extends Activity {
    TextView title_text_login,sign_in_text,sign_up_btn;
    Button btn_login;
    EditText edtmail, edtpass;
    String strusername, strpass;
    String lg_url = "http://192.168.43.149/course_app_api/login.php";
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        title_text_login = findViewById(R.id.title_text_login);
        btn_login = findViewById(R.id.btn_login);
        edtmail = findViewById(R.id.edtmail);
        edtpass = findViewById(R.id.edtpass);
        sign_in_text = findViewById(R.id.sign_in_text);
        sign_up_btn = findViewById(R.id.sign_up_btn);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
        title_text_login.setTypeface(custom_font);

        Typeface home_txt_face = Typeface.createFromAsset(getAssets(), "fonts/dbplus.otf");
        btn_login.setTypeface(home_txt_face);
        edtmail.setTypeface(home_txt_face);
        sign_in_text.setTypeface(home_txt_face);
        sign_up_btn.setTypeface(home_txt_face);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthenticationActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });



        builder = new AlertDialog.Builder(AuthenticationActivity.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strusername = edtmail.getText().toString();
                strpass = edtpass.getText().toString();
                if (strusername.equals("") || strpass.equals("")) {
                    builder.setTitle("Erreur");
                    displayAlert("Informations non valides...");
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, lg_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");


                                if (code.equals("login_failed")) {
                                    builder.setTitle("Erreur");
                                    displayAlert("Merci de vérifier vos données!");
                                } else {

                                    SharedPreferences sharedPreferences = getSharedPreferences("myprefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_id", jsonObject.getInt("user_id") + "");
                                    editor.putString("profile", jsonObject.getString("profile"));
                                    editor.putString("prenom", jsonObject.getString("prenom"));
                                    editor.apply();
                                    edtpass.setText("");
                                    edtmail.setText("");
                                    Intent i = new Intent(AuthenticationActivity.this, MainActivity.class);
                                    startActivity(i);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AuthenticationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", strusername);
                            params.put("password", strpass);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(AuthenticationActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtmail.setText("");
                edtpass.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
