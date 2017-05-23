package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class ShopScreen extends AppCompatActivity {
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    private final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
    private static String leaderboardsURL = "http://bomb4you.tk/api/v1/score/leaderboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);




    }



    public boolean add10Gold(View view){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        try {
            if (!sharedPref.getString("user_token", "").equals("")) {
                String user_token = sharedPref.getString("user_token", "");
                int gold=sharedPref.getInt("Gold",0);
                int score=sharedPref.getInt("Score",0);
                int cash=sharedPref.getInt("Cash",0);
                int dynamite=sharedPref.getInt("Dynamite",0);
                int smallBomb=sharedPref.getInt("SmallBomb",0);
                int bigBomb=sharedPref.getInt("BigBomb",0);
                int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

                gold = gold + 10;
                editor.remove("Gold");
                editor.putInt("Gold", gold);
                editor.apply();
                editor.commit();

                toastText("10 gold was added to your account");

                setValuesInWeb(gold, score, cash,dynamite,smallBomb,bigBomb,nuclearWeapon);

                return true;
            }
            else{
                toastText("Please log in first");
                return false;
            }
        }catch (Exception e){
            toastText("Please log in first.");
            return false;
        }


    }
    public boolean add1000Cash(View view){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        try {
            if (!sharedPref.getString("user_token", "").equals("")) {
                String user_token = sharedPref.getString("user_token", "");
                int gold=sharedPref.getInt("Gold",0);
                int score=sharedPref.getInt("Score",0);
                int cash=sharedPref.getInt("Cash",0);
                int dynamite=sharedPref.getInt("Dynamite",0);
                int smallBomb=sharedPref.getInt("SmallBomb",0);
                int bigBomb=sharedPref.getInt("BigBomb",0);
                int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

                cash = cash + 1000;
                editor.remove("Cash");
                editor.putInt("Cash", cash);
                editor.apply();
                editor.commit();
                toastText("1000 cash was added to your account");
                setValuesInWeb(gold, score, cash,dynamite,smallBomb,bigBomb,nuclearWeapon);

                return true;
            } else {
                toastText("Please log in first");
                return false;
            }
        }catch (Exception e){
            toastText("Please log in first.");
            return false;
        }
    }

    public void setValuesInWeb(final int newGoldAmount, final int newScoreAmount, final int newCashAmount,
                               final int newDynamiteAmount, final int newSmallBombAmount,
                               final int newBigBombAmount, final int newNuclearWeaponAmount){
        final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
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
            protected Map<String,String> getParams() throws AuthFailureError{
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
    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (!sharedPref.getString("user_token", "").equals(""))
        {
            Intent intent = new Intent(this,MainScreen.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this,LaunchScreen.class);
            startActivity(intent);
        }
    }
}
