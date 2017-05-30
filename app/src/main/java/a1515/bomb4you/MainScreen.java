package a1515.bomb4you;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainScreen extends AppCompatActivity {
    int backButtonCount=0;
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    private final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
    private static String leaderboardsURL = "http://bomb4you.tk/api/v1/score/leaderboard";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        backButtonCount=0;
        try {
            super.onCreate(savedInstanceState);
            getUserInfoFromWeb();
            setContentView(R.layout.activity_main_screen);
            final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();


            //if(gameMode.equals("counter_terrorist")) {
            //    setBackgroundCt();
            //}
            SetDisplayValues();
            //----------------------------------------------------------


            optMenuController();
            bombShopScreen();

        }
        catch (Exception e){
            toastText(e.getMessage().toString());
        }


    }


    public void redirectToPopUpShop(View view){
        startActivity(new Intent(MainScreen.this,PopUpShop.class));
    }


    public void setBackgroundCt(){
        ConstraintLayout constraintLayout;
        constraintLayout = (ConstraintLayout) findViewById(R.id.MainScreenLayout);
        //constraintLayout.setBackgroundResource(R.drawable.background_ct);
        constraintLayout.setBackground( getResources().getDrawable(R.drawable.background_ct));
    }

    public void bombShopScreen(){
        final ImageButton popUpMenuButton=(ImageButton)(findViewById(R.id.BBombList));
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        popUpMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainScreen.this,ShopScreen.class));

            };
        });
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
            request = new StringRequest(Request.Method.POST, userInfoURL, new Response.Listener<String>() {
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

                        SetDisplayValues();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toastText("Error occurred...");
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


    public void SetDisplayValues(){

        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        TextView goldDispl=(TextView)findViewById(R.id.TGoldDisplay);
        TextView cashDispl=(TextView)findViewById(R.id.TCashDisplay);
        TextView scoreDispl=(TextView)findViewById(R.id.TScoreDisplay);

        int gold=sharedPref.getInt("Gold",0);
        int score=sharedPref.getInt("Score",0);
        int cash=sharedPref.getInt("Cash",0);
//        int dynamite=sharedPref.getInt("Dynamite",0);
//        int smallBomb=sharedPref.getInt("SmallBomb",0);
//        int bigBomb=sharedPref.getInt("BigBomb",0);
//        int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);
//
        goldDispl.setText(Integer.toString(gold));
        scoreDispl.setText(Integer.toString(score));
        cashDispl.setText(Integer.toString(cash));
//        dynamiteDisplay.setText(Integer.toString(dynamite));
//        smallBombDisplay.setText(Integer.toString(smallBomb));
//        bigBombDisplay.setText(Integer.toString(bigBomb));
//        nuclearWeaponDisplay.setText(Integer.toString(nuclearWeapon));
    }

    public void attackButtonController(View view){
        startActivity(new Intent(MainScreen.this,AttackPopUp.class));
    }

    public void optMenuController(){
        final ImageButton popUpMenuButton=(ImageButton)(findViewById(R.id.BOptMainScreen));
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        popUpMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu=new PopupMenu(MainScreen.this,popUpMenuButton);

                popupMenu.inflate(R.menu.main_screen_opts_menu);

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.toString().equals("Leaderboards")){
                            startActivity(new Intent(MainScreen.this,LeaderboardDisplay.class));
                            return true;
                        }
                        if(item.toString().equals("FB page")){
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("http://www.facebook.com/bomb4you"));
                            startActivity(intent);
                            return true;
                        }
                        if(item.toString().equals("Logout")){
                            if(item.toString().equals("Logout")){
                                if(sharedPref.getString("Name","")=="guest" || sharedPref.getString("Name","").contains("guest")){
                                    editor.clear();
                                    editor.putInt("RememberMe",0);
                                    editor.commit();
                                    toastText("Logged out");
                                    startActivity(new Intent(MainScreen.this,LaunchScreen.class));
                                }else
                                {
                                    final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = sharedPref.edit();

                                    int gold=sharedPref.getInt("Gold",0);
                                    int score=sharedPref.getInt("Score",0);
                                    int cash=sharedPref.getInt("Cash",0);
                                    int dynamite=sharedPref.getInt("Dynamite",0);
                                    int smallBomb=sharedPref.getInt("SmallBomb",0);
                                    int bigBomb=sharedPref.getInt("BigBomb",0);
                                    int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

                                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                                    editor.clear();
                                    editor.putInt("RememberMe",0);
                                    editor.commit();

                                    toastText("Logged out");
                                    startActivity(new Intent(MainScreen.this,LaunchScreen.class));
                                }
                                return true;
                            }
                            return true;
                        }

                        return false;
                    }
                });

            };
        });
    }












    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(MainScreen.this,LaunchScreen.class);
            startActivity(intent);
        }
        else
        {
            SetDisplayValues();
            Toast.makeText(this, "Press the back button once again to return to launch screen.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
