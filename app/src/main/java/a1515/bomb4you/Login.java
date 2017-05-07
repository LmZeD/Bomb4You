package a1515.bomb4you;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


import static a1515.bomb4you.R.id.CRemeberMyChoice;
import static a1515.bomb4you.R.id.TEmail;
import static a1515.bomb4you.R.id.TErrorOutput;
import static a1515.bomb4you.R.id.TPassword;
import static a1515.bomb4you.R.id.checkbox;
import static a1515.bomb4you.R.id.info;
import static android.app.PendingIntent.getActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private RequestQueue requestQueue;
    public static final String loginURL="http://bomb4you.tk/api/v1/auth/login";
    private StringRequest request;
    private int backButtonPressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {//if it's first run there won't be any intent
            Intent errorLaunch=getIntent();
            if(errorLaunch.getExtras().getString("error")!=null){
                setUpErrorMsg(errorLaunch.getExtras().getString("error"));
            }
            }
        catch (Exception ex){//just continue
        }
        try{//if 'remember me'
            SharedPreferences preferences=getPreferences(Context.MODE_PRIVATE);
            int a = preferences.getInt("RememberMe", 0);
            if(a==1){

                Intent intent=new Intent(this,MainScreen.class);
                Toast.makeText(this,"Welcome back",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }
        catch (Exception ex){

        }


    }

    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public void sendLoginRequestToWeb(final String email, final String password){
        requestQueue = Volley.newRequestQueue(this);

        request = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("user_token")){
                        toastText("Welcome");
                        startActivity(new Intent(getApplicationContext(),MainScreen.class));
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


    public void setUpErrorMsg(String msg){
        TextView errOutput=(TextView)(findViewById(TErrorOutput));
        errOutput.setText(msg);
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

    public void Register(View view){//needs modifications
        Intent register=new Intent(this,RegisterForm.class);
        startActivity(register);
    }

}
