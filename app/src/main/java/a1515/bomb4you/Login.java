package a1515.bomb4you;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import java.util.Map;
import java.util.Set;

import static a1515.bomb4you.R.id.CRemeberMyChoice;
import static a1515.bomb4you.R.id.TEmail;
import static a1515.bomb4you.R.id.TErrorOutput;
import static a1515.bomb4you.R.id.TPassword;
import static a1515.bomb4you.R.id.checkbox;
import static a1515.bomb4you.R.id.info;
import static android.app.PendingIntent.getActivity;

public class Login extends AppCompatActivity {


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

        if(test==true) {
            Intent loginInfo = new Intent(this, MainScreen.class);

            loginInfo.putExtra("EMail", email.toString());
            loginInfo.putExtra("Password", password.toString());

            CheckBox rememberMe=(CheckBox)(findViewById(CRemeberMyChoice));
            if(rememberMe.isChecked()){
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("RememberMe", 1);
                editor.commit();
            }

            startActivity(loginInfo);
        }
    }

    public boolean TestLoginInfo(String email,String password)
    {
        Intent LoginFailure=new Intent(this,Login.class);//in case of failed login, return to login screen
        String errorMessage=null;
        TextView errMsgOutput=(TextView)(findViewById(TErrorOutput));

        if(email==null || email.toLowerCase()=="email"){
            errorMessage="Please fill in E-mail or continue without registration";
            LoginFailure.putExtra("error",errorMessage);
            startActivity(LoginFailure);
            return false;
        }

        if(!email.contains("@") || !email.contains(".")){
            errorMessage="Not valid E-mail address";
            LoginFailure.putExtra("error",errorMessage);
            startActivity(LoginFailure);
            return false;
        }

        if(password=="password" || password=="Password"){
            errorMessage="Not valid password";
            LoginFailure.putExtra("error",errorMessage);
            startActivity(LoginFailure);
            return false;
        }

        return true;
    }

    public void WithoutRegistration(View view){

        Intent login=new Intent(this,MainScreen.class);
        login.putExtra("EMail","");
        login.putExtra("Password","");

        startActivity(login);
    }

}
