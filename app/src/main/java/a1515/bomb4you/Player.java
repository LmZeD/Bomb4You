package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Player extends AppCompatActivity{

    public static String Name;
    public static int Gold;
    public static int Cash;
    public static int Score;
    public static String ClanName;
    public static String GameMode;
    public static String user_token;
    public static int id;



    public void saveAllInSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("Name", Name);
        editor.putInt("Gold", Gold);
        editor.putInt("Cash", Cash);
        editor.putInt("Score", Score);
        editor.putString("ClanName", ClanName);
        editor.putString("GameMode", GameMode);
        editor.putString("user_token", user_token);
        editor.putInt("id", id);
        editor.commit();
    }

    public void getAllFromSharedPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        Name=sharedPref.getString("Name","");
        Gold=sharedPref.getInt("Gold",0);
        Cash=sharedPref.getInt("Cash",0);
        Score=sharedPref.getInt("Score",0);
        ClanName=sharedPref.getString("ClanName","");
        GameMode=sharedPref.getString("GameMode","");
        user_token=sharedPref.getString("user_token","");
    }

















    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
    //--------------------------------------------------------------------------
    //getters and setters
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public String getUser_token() {
        return user_token;
    }
    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
    public void setGameMode(String mode){
        GameMode=mode;
    }
    public void setName(String name){
        Name=name;
    }
    public void AddCash(int amount){
        Cash=Cash+amount;
    }
    public void AddGold(int amount){
        Gold=Gold+amount;
    }
    public void RemoveCash(int amount){
        Cash=Cash-amount;
    }
    public void RemoveGold(int amount){
        Gold=Gold-amount;
    }
    public void SetClan(String name){
        ClanName=name;
    }
    public void RemoveClan(){
        ClanName=null;
    }
    public void setGold(int amount){
        Gold=amount;
    }
    public void setCash(int amount){
        Cash=amount;
    }
    public void setScore(int amount){
        Score=amount;
    }
    public int getGold(){
        return Gold;
    }
    public int getCash(){
        return Cash;
    }
    public int getScore(){
        return Score;
    }
    //-------------------------------------------------------------------------
}
