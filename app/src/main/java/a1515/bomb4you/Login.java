package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

import static a1515.bomb4you.R.id.CRemeberMyChoice;
import static a1515.bomb4you.R.id.TEmail;
import static a1515.bomb4you.R.id.TPassword;

public class Login extends AppCompatActivity {

    private RequestQueue requestQueue;private StringRequest request;
    public static final String loginURL="http://bomb4you.tk/api/v1/auth/login";
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    private final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
    private static String leaderboardsURL = "http://bomb4you.tk/api/v1/score/leaderboard";

    private int backButtonPressed=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        try {//if it's first run there won't be any intent
            Intent errorLaunch=getIntent();
            if(errorLaunch.getExtras().getString("error")!=null){
                toastText(errorLaunch.getExtras().getString("error"));
            }
            }
        catch (Exception ex){//just continue
        }
        try{//if 'remember me'
            int a = sharedPref.getInt("RememberMe",0);
            if(a==1){
                Intent intent=new Intent(this,MainScreen.class);
                Toast.makeText(this,"Welcome back",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }
        catch (Exception ex){
            toastText("Remember me failure");
        }


    }

    public boolean rememberMeBoxChecked(){
        CheckBox checkBox =(CheckBox) (findViewById(CRemeberMyChoice));
        if(checkBox.isChecked()) return true;
        return false;
    }

    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public void sendLoginRequestToWeb(final String email, final String password){
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        request = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("user_token")){
                        toastText("Welcome");

                        editor.putString("user_token",jsonObject.getString("user_token"));
                        editor.commit();

                        if(rememberMeBoxChecked()) {
                            editor.putInt("RememberMe", 1);
                            editor.commit();
                        }
                        getUserInfoFromWeb();
                        Intent intent=new Intent(Login.this,MainScreen.class);
                        intent.getBooleanExtra("refreshed",false);
                        startActivity(intent);
                    }
                    else{
                         toastText("Wrong login data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("User doesn't exist");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String,String> hashMap =new HashMap<String,String>();
                hashMap.put("email",email);
                hashMap.put("password",password);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public void getUserInfoFromWeb(){
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        final String user_token=sharedPref.getString("user_token","");
        if(user_token==null){//security check
            toastText("Login info corrupted. Please log in again");
            editor.clear();
            Intent intent =new Intent(this,Login.class);
            startActivity(intent);
        }
        else {
            request = new StringRequest(Request.Method.POST, userInfoURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        editor.putInt("Score",jsonObject.getInt("score"));
                        editor.putInt("Cash",jsonObject.getInt("money"));
                        editor.putInt("Gold",jsonObject.getInt("gold"));
                        editor.putInt("Id",jsonObject.getInt("id"));
                        editor.putString("GameMode",jsonObject.getString("gamemode"));
                        editor.apply();
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toastText("Error occurred");
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError{
                    HashMap<String,String> hashMap =new HashMap<String,String>();
                    hashMap.put("user-token",user_token);
                    return hashMap;
                }
            };
            requestQueue.add(request);
        }
    }

    public void Login(View view){
        EditText emailField= (EditText)(findViewById(TEmail));
        EditText passwordField= (EditText)(findViewById(TPassword));
        String email=emailField.getText().toString();
        String password=passwordField.getText().toString();
        //data taken, starting to validate

        boolean test=TestLoginInfo(email,password);

        //end of validation
        try {
            if (test == true) {
                sendLoginRequestToWeb(email,password);
            }
        }
        catch (Exception ex){
            Toast.makeText(this,"Oops, something went wrong. Please try again.",Toast.LENGTH_SHORT).show();
            Intent restart = new Intent(this, Login.class);
            startActivity(restart);
        }
    }

    public boolean TestLoginInfo(String email,String password)
    {
        String errorMessage;
        if(email==null || email.toLowerCase()=="email" ){
            errorMessage="Please fill in E-mail";
            toastText(errorMessage);
            return false;
        }

        boolean testMail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();


        if(testMail == false){
            errorMessage="Not valid E-mail address";
            toastText(errorMessage);
            return false;
        }

        if(password=="password" || password=="Password" || password==null){
            errorMessage="Not valid password";
            toastText(errorMessage);
            return false;
        }

        if(password.length()<6){
            errorMessage="Password should be more than 6 characters";
            toastText(errorMessage);
            return false;
        }

        return true;
    }

    public void Register(View view){
        Intent register=new Intent(this,RegisterForm.class);
        startActivity(register);
    }
}
