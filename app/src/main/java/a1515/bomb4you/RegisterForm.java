package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static a1515.bomb4you.R.id.TConfirmPasswordRegistrationForm;
import static a1515.bomb4you.R.id.TEmailRegistrationForm;
import static a1515.bomb4you.R.id.TNameRegistrationForm;
import static a1515.bomb4you.R.id.TPasswordRegistrationForm;

public class RegisterForm extends AppCompatActivity {
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        WebRequests webRequests=new WebRequests();

        withoutRegistration();
        registerPressed();


    }
    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public boolean validateInfo(String name,String email,String password,String confPassword){
        String errorMessage;
        boolean testMail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if(name==null){
            errorMessage="Don't leave blank";
            toastText(errorMessage);
            return false;
        }

        if(testMail == false){
            errorMessage="Not valid E-mail address";
            toastText(email);
            toastText(errorMessage);
            return false;
        }

        if(!password.equals(confPassword)){
            errorMessage="Passwords do not match";
            toastText(errorMessage);
            return false;
        }

        if(password.length()<6){
            errorMessage="Password is too short (Min 6 characters)";
            toastText(errorMessage);
            return false;
        }

        return true;
    }

    public void registerPressed(){
        final EditText name=(EditText)(findViewById(TNameRegistrationForm));
        final EditText Email=(EditText)(findViewById(TEmailRegistrationForm));
        final EditText password=(EditText)(findViewById(TPasswordRegistrationForm));
        final EditText confirmPassword=(EditText)(findViewById(TConfirmPasswordRegistrationForm));

            final Button popUpMenuButton = (Button) (findViewById(R.id.BRegister));
            SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();

            popUpMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean validation=validateInfo(name.getText().toString(),Email.getText().toString(),password.getText().toString(),confirmPassword.getText().toString());

                    PopupMenu popupMenu = new PopupMenu(RegisterForm.this, popUpMenuButton);

                    popupMenu.inflate(R.menu.registration_game_mode);

                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.toString().equals("Play as terrorist")) {
                                if (validation == true) {
                                    sendRegistrationReq(name.getText().toString(), Email.getText().toString(), password.getText().toString(), "terrorist","0");
                                } else {
                                    toastText("Please, fill in all fields with correct data");
                                }
                                return true;
                            }
                            if (item.toString().equals("Play as counter-terrorist")) {
                                if (validation == true) {
                                    sendRegistrationReq(name.getText().toString(), Email.getText().toString(), password.getText().toString(), "counter_terrorist","0");
                                } else {
                                    toastText("Please, fill in all fields with correct data");
                                }

                                return true;
                            }


                            return false;
                        }
                    });
                }

                ;
            });





    }

    public void withoutRegistration(){
        final Button popUpMenuButton=(Button)(findViewById(R.id.BWihoutRegistrationRegistrationForm));
        SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        popUpMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(RegisterForm.this,popUpMenuButton);

                popupMenu.inflate(R.menu.registration_game_mode);

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.toString().equals("Play as terrorist")){
                            sendWithoutRegistrationReq("terrorist");

                            return true;
                        }
                        if(item.toString().equals("Play as counter-terrorist")){
                            sendWithoutRegistrationReq("counter_terrorist");

                            return true;
                        }


                        return false;
                    }
                });

            };
        });
    }

    public void sendRegistrationReq(final String name,final String email,final String password,
                                    final String gameMode,final String is_guest){
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        request = new StringRequest(Request.Method.POST, "http://bomb4you.tk/api/v1/auth/register ", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("user_token")){

                        toastText("Successfully registered!");

                        editor.putString("user_token",jsonObject.getString("user_token"));
                        editor.putString("Name",name);
                        editor.putString("Email",email);
                        editor.putString("GameMode",gameMode);
                        editor.commit();
                        Intent intent=new Intent(getApplicationContext(),MainScreen.class);
                        intent.getBooleanExtra("refreshed",false);
                        startActivity(intent);
                    }
                    else{
                        toastText("Already taken!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("Email is already taken");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap =new HashMap<String,String>();
                hashMap.put("gamemode",gameMode);
                hashMap.put("is_guest",is_guest);
                hashMap.put("name",name);
                hashMap.put("email",email);
                hashMap.put("password",password);
                return hashMap;
            }
        };
        requestQueue.add(request);

    }



    public void sendWithoutRegistrationReq(final String gameMode){
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        request = new StringRequest(Request.Method.POST, "http://bomb4you.tk/api/v1/auth/register ", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("user_token")){

                        toastText("Successfully registered!");

                        editor.putString("user_token",jsonObject.getString("user_token"));
                        editor.putString("GameMode",gameMode);
                        editor.putInt("RememberMe",1);
                        editor.putString("Name","guest");
                        editor.putInt("Gold",10);
                        editor.putInt("Cash",100);
                        editor.putInt("Score",0);
                        editor.commit();
                        Intent intent=new Intent(getApplicationContext(),MainScreen.class);
                        startActivity(intent);
                    }
                    else{
                        toastText("Server is busy, try again later");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("Oops, something happened");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap =new HashMap<String,String>();
                hashMap.put("gamemode",gameMode);
                hashMap.put("is_guest","1");
                return hashMap;
            }
        };
        requestQueue.add(request);

    }
}
