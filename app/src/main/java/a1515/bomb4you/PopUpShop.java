package a1515.bomb4you;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
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


public class PopUpShop extends AppCompatActivity implements View.OnClickListener{
    private RequestQueue requestQueue;
    private StringRequest request;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.pop_up_shop);
            buttonController();
    }
    public void buttonController(){
        ImageButton gold=(ImageButton)findViewById(R.id.BBuy10000Gold);
        ImageButton cash=(ImageButton)findViewById(R.id.BBuy1000cash);
        gold.setOnClickListener(this);
        cash.setOnClickListener(this);
    }

    public void exitShopButton(View view){
        startActivity(new Intent(PopUpShop.this,MainScreen.class));
    }

    public void setValuesInWeb(final int newGoldAmount, final int newScoreAmount, final int newCashAmount,
                               final int newDynamiteAmount, final int newSmallBombAmount,
                               final int newBigBombAmount, final int newNuclearWeaponAmount){
        final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
        requestQueue = Volley.newRequestQueue(this);

        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final String user_token=sharedPref.getString("user_token","");

        request = new StringRequest(Request.Method.POST, scoreSetURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("Value setting error");
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap =new HashMap<String,String>();
                hashMap.put("user-token",user_token);
                hashMap.put("score",Integer.toString(newScoreAmount));
                hashMap.put("money",Integer.toString(newCashAmount));
                hashMap.put("gold",Integer.toString(newGoldAmount));
                hashMap.put("dynamite",Integer.toString(newDynamiteAmount));
                hashMap.put("small_bomb",Integer.toString(newSmallBombAmount));
                hashMap.put("big_bomb",Integer.toString(newBigBombAmount));
                hashMap.put("nuclear_weapon",Integer.toString(newNuclearWeaponAmount));
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
        if(user_token.equals("")){//security check
            toastText("Login info corrupted. Please log in again");
            editor.clear();
            Intent intent =new Intent(this,Login.class);
            startActivity(intent);
        }
        else {
            request = new StringRequest(Request.Method.POST, "http://bomb4you.tk/api/v1/user/info", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        editor.putInt("Score",jsonObject.getInt("score"));
                        editor.putInt("Cash",jsonObject.getInt("money"));
                        editor.putInt("Gold",jsonObject.getInt("gold"));
                        editor.putInt("Dynamite",jsonObject.getInt("dynamite"));
                        editor.putInt("SmallBomb",jsonObject.getInt("small_bomb"));
                        editor.putInt("BigBomb",jsonObject.getInt("big_bomb"));
                        editor.putInt("NuclearWeapon",jsonObject.getInt("nuclear_weapon"));
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
                    toastText("Error occurred. Lol.");
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
    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(PopUpShop.this,MainScreen.class));
    }

    @Override
    public void onClick(View v) {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final int gold=sharedPref.getInt("Gold",0);
        final int score=sharedPref.getInt("Score",0);
        final int cash=sharedPref.getInt("Cash",0);
        final int dynamite=sharedPref.getInt("Dynamite",0);
        final int smallBomb=sharedPref.getInt("SmallBomb",0);
        final int bigBomb=sharedPref.getInt("BigBomb",0);
        final int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);
        switch (v.getId()) {
            case R.id.BBuy1000cash:
                editor.putInt("Cash",cash+1000);
                editor.apply();
                editor.commit();
                setValuesInWeb(gold,score,cash+1000,dynamite,smallBomb,bigBomb,nuclearWeapon);
                toastText("Added 1000 cash");
                break;
            case R.id.BBuy10000Gold:
                editor.putInt("Gold",gold+10000);
                editor.apply();
                editor.commit();
                setValuesInWeb(gold+10000,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                toastText("Added 10000 gold");
                break;
            default:
                break;
        }
    }
}
