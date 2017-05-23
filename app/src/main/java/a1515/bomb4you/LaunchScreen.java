package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class LaunchScreen extends AppCompatActivity {
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    private final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
    private static String leaderboardsURL = "http://bomb4you.tk/api/v1/score/leaderboard";
    private int backButtonPressed=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        try {
            menuButtonController();
        }catch (Exception ex){
            toastText("oops, crashed");
        }



    }



    public void menuButtonController(){
        final ImageButton popUpMenuButton=(ImageButton)(findViewById(R.id.BOptions));
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        popUpMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(LaunchScreen.this,popUpMenuButton);

                popupMenu.inflate(R.menu.menu_for_launch_screen);

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.toString().equals("Logout")){
                            if(sharedPref.getString("Name","")=="guest" || sharedPref.getString("Name","").contains("guest")){
                                editor.clear();
                                editor.putInt("RememberMe",0);
                                editor.commit();
                                toastText("Logged out");
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
                            }
                            return true;
                        }
                        if(item.toString().equals("Like On Facebook")){
                            toastText("Not implemented yet");
                            return true;
                        }
                        if(item.toString().equals("Google+")){
                            toastText("Not implemented yet");
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


    @Override
    public void onBackPressed(){//easter egg
        backButtonPressed++;
        if(backButtonPressed>5){
            toastText("Stop smashing back button!");
            backButtonPressed=0;
        }
    }

    public void PlayButtonClick(View view){//hop to login screen
        Intent intent =new Intent(this,Login.class);
        startActivity(intent);
    }

    public void ShopButtonClick(View view){//hop to shop page
        Intent intent =new Intent(this,ShopScreen.class);
        startActivity(intent);
    }
    public void FBButtonClick(View view){//opens facebook page
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.facebook.com/bomb4you"));
        startActivity(intent);
    }
    public void LeaderboardsButtonClick(View view){//opens leaderboards
        Intent intent = new Intent(this,LeaderboardDisplay.class);
        startActivity(intent);
    }

    public void OptionsButtonClick(View view){//options screen

    }




}
