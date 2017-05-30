package a1515.bomb4you;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AttackPopUp extends AppCompatActivity implements View.OnClickListener{
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String leaderboardsURL="http://bomb4you.tk/api/v1/score/leaderboard";
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.attack_pop_up);

        prepareValues();
        buttonController();
    }


    public int calculateEstimatedPower(){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        int estimatedPower=0;

        int gold=sharedPref.getInt("Gold",0);
        int score=sharedPref.getInt("Score",0);
        int cash=sharedPref.getInt("Cash",0);
        int dynamite=sharedPref.getInt("Dynamite",0);
        int smallBomb=sharedPref.getInt("SmallBomb",0);
        int bigBomb=sharedPref.getInt("BigBomb",0);
        int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

        Random rnd = new Random();
        int randomNumber=rnd.nextInt(100);
        estimatedPower=(score+7)*randomNumber;

        return estimatedPower;
    }

    public void getEnemy() {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final String user_token=sharedPref.getString("user_token","");


        requestQueue = Volley.newRequestQueue(this);

        request = new StringRequest(Request.Method.POST, "http://bomb4you.tk/api/v1/score/leaderboard", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("list");

                    int length=array.length();
                    Random rnd=new Random();

                    JSONObject objFromArr=array.getJSONObject(rnd.nextInt(length));
                    if(objFromArr.getString("name").equals(sharedPref.getString("Name",""))){
                        editor.putString("Enemy","Boss");
                        editor.apply();
                        editor.commit();
                    }
                    else
                    {
                        editor.putString("Enemy",objFromArr.getString("name"));
                        editor.apply();
                        editor.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("Error occurred");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("user-token", user_token);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public void toastText(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void prepareValues() {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        getEnemy();

        String enemy=sharedPref.getString("Enemy","");
        int estimatedPower=calculateEstimatedPower();

        TextView tv2 =(TextView)findViewById(R.id.textView2);
        TextView tv3 =(TextView)findViewById(R.id.textView3);

        tv2.setText(enemy);
        tv3.setText(Integer.toString(estimatedPower));

    }

    public void buttonController(){
        ImageButton smallBombPlus = (ImageButton)findViewById(R.id.BPlusSmallBombAttack);
        ImageButton smallBombMinus = (ImageButton)findViewById(R.id.BMinusSmallBombAttack);
        ImageButton dynamitePlus = (ImageButton)findViewById(R.id.BPlusDynamiteAttack);
        ImageButton dynamiteMinus = (ImageButton)findViewById(R.id.BMinusDynamiteAttack);
        ImageButton bigBombPlus = (ImageButton)findViewById(R.id.BPlusBigBombAttack);
        ImageButton bigBombMinus = (ImageButton)findViewById(R.id.BMinusBigBombAttack);
        ImageButton nuclearWeaponPlus = (ImageButton)findViewById(R.id.BPlusNuclearWeaponAttack);
        ImageButton nuclearWeaponMinus = (ImageButton)findViewById(R.id.BMinusNuclearWeaponAttack);
        ImageButton attack=(ImageButton)findViewById(R.id.BAttackAttackScreen);

        smallBombPlus.setOnClickListener(this);
        smallBombMinus.setOnClickListener(this);
        dynamitePlus.setOnClickListener(this);
        dynamiteMinus.setOnClickListener(this);
        bigBombPlus.setOnClickListener(this);
        bigBombMinus.setOnClickListener(this);
        nuclearWeaponPlus.setOnClickListener(this);
        nuclearWeaponMinus.setOnClickListener(this);
        attack.setOnClickListener(this);
    }

    public int calculatePower(){

        TextView itemCountSmallBomb = (TextView)findViewById(R.id.TSmallBombCount);
        TextView itemCountDynamite = (TextView) findViewById(R.id.TDynamiteCount);
        TextView itemCountBigBomb = (TextView)findViewById(R.id.TBigBombCount);
        TextView itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
        TextView powerOutput = (TextView)findViewById(R.id.textView3);

        int amountSmallBomb=Integer.parseInt(itemCountSmallBomb.getText().toString());
        int amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
        int amountBigBomb=Integer.parseInt(itemCountBigBomb.getText().toString());
        int amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
        int totalPower;

        totalPower=amountSmallBomb*25+amountDynamite*375+amountBigBomb*3795+amountNuclearWeapon*725500;


        return totalPower;
    }

    @Override
    public void onClick(View v) {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        String enemy=sharedPref.getString("Enemy","");
        int gold=sharedPref.getInt("Gold",0);
        int score=sharedPref.getInt("Score",0);
        int cash=sharedPref.getInt("Cash",0);
        int dynamite=sharedPref.getInt("Dynamite",0);
        int smallBomb=sharedPref.getInt("SmallBomb",0);
        int bigBomb=sharedPref.getInt("BigBomb",0);
        int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

        switch (v.getId()) {


            case R.id.BAttackAttackScreen:
                TextView powerOutput = (TextView)findViewById(R.id.textView3);
                TextView itemCountSmallBomb = (TextView)findViewById(R.id.TSmallBombCount);
                TextView itemCountDynamite = (TextView) findViewById(R.id.TDynamiteCount);
                TextView itemCountBigBomb = (TextView)findViewById(R.id.TBigBombCount);
                TextView itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                int amountSmallBomb=Integer.parseInt(itemCountSmallBomb.getText().toString());
                int amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                int amountBigBomb=Integer.parseInt(itemCountBigBomb.getText().toString());
                int amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());


                Random rnd=new Random();

                int scoreEarned=0;
                int goldEarned=0;
                int estimatedPower=calculateEstimatedPower();
                int currentPower = Integer.parseInt(powerOutput.getText().toString());
if(currentPower!=0) {
    if (estimatedPower < currentPower) {
        int random = rnd.nextInt(currentPower - estimatedPower);
        if (random == 1) {
            toastText("You lost...");
        } else {
            toastText("You won!");
            scoreEarned = (int) ((currentPower - estimatedPower + 2) / 125 + 7);
            goldEarned = (int) (scoreEarned * 0.9);
            if (enemy.equals("Boss")) {
                goldEarned = goldEarned * 10;
                cash = cash + 1000;
                toastText("Boss defeated 1000 cash prize!");
                toastText("Score earned: " + scoreEarned + " , gold earned: " + goldEarned);
            } else {
                toastText("Score earned: " + scoreEarned + " , gold earned: " + goldEarned);
            }
        }
    } else {
        int random = rnd.nextInt(estimatedPower - currentPower);
        if (random == 1) {
            toastText("You won!");
            scoreEarned = (int) ((currentPower - estimatedPower + 2) / 12575 + 7);
            goldEarned = (int) (scoreEarned * 0.9);
            if (enemy.equals("Boss")) {
                goldEarned = goldEarned * 10;
                cash = cash + 1000;
                toastText("Boss defeated 1000 cash prize!");
                toastText("Score earned: " + scoreEarned + " , gold earned: " + goldEarned);
            } else {
                toastText("Score earned: " + scoreEarned + " , gold earned: " + goldEarned);
            }
        } else {
            toastText("You lost...");
        }
    }
    setValuesInWeb(gold+goldEarned, score + scoreEarned, cash, dynamite - amountDynamite, smallBomb - amountSmallBomb, bigBomb - amountBigBomb, nuclearWeapon - amountNuclearWeapon);
    getUserInfoFromWeb();
    itemCountSmallBomb.setText("0");
    itemCountDynamite.setText("0");
    itemCountBigBomb.setText("0");
    itemCountNuclearWeapon.setText("0");
    powerOutput.setText("0");
    startActivity(new Intent(AttackPopUp.this,MainScreen.class));
}else {
    toastText("Select some bombs!");
}

                break;

            case R.id.BPlusSmallBombAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountSmallBomb = (TextView)findViewById(R.id.TSmallBombCount);
                amountSmallBomb=Integer.parseInt(itemCountSmallBomb.getText().toString());
                if(amountSmallBomb<smallBomb) {
                    amountSmallBomb++;
                }
                else {
                    toastText("At this moment you have "+smallBomb+" small bombs");
                }
                itemCountSmallBomb.setText(Integer.toString(amountSmallBomb));
                powerOutput.setText(Integer.toString(calculatePower()));
                break;

            case R.id.BMinusSmallBombAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountSmallBomb = (TextView) findViewById(R.id.TSmallBombCount);


                amountSmallBomb = Integer.parseInt(itemCountSmallBomb.getText().toString());

                if(amountSmallBomb>0) {
                    amountSmallBomb--;
                    itemCountSmallBomb.setText(Integer.toString(amountSmallBomb));
                }
                else {
                    toastText("Just don't...");
                }
                powerOutput.setText(Integer.toString(calculatePower()));
                break;
            case R.id.BPlusDynamiteAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountDynamite = (TextView)findViewById(R.id.TDynamiteCount);
                amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                if(amountDynamite<dynamite) {
                    amountDynamite++;
                }else {
                    toastText("At this moment you have "+dynamite+" dynamite");
                }
                itemCountDynamite.setText(Integer.toString(amountDynamite));
                powerOutput.setText(Integer.toString(calculatePower()));
                break;

            case R.id.BMinusDynamiteAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountDynamite = (TextView) findViewById(R.id.TDynamiteCount);


                amountDynamite = Integer.parseInt(itemCountDynamite.getText().toString());

                if(amountDynamite>0) {
                    amountDynamite--;
                    itemCountDynamite.setText(Integer.toString(amountDynamite));
                }
                else {
                    toastText("Just don't...");
                }
                powerOutput.setText(Integer.toString(calculatePower()));
                break;
            case R.id.BPlusBigBombAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountBigBomb = (TextView)findViewById(R.id.TBigBombCount);
                amountBigBomb=Integer.parseInt(itemCountBigBomb.getText().toString());
                if(amountBigBomb<bigBomb) {
                    amountBigBomb++;
                }else
                {
                    toastText("At this moment you have "+bigBomb+" big bombs");
                }
                itemCountBigBomb.setText(Integer.toString(amountBigBomb));
                powerOutput.setText(Integer.toString(calculatePower()));
                break;

            case R.id.BMinusBigBombAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountBigBomb = (TextView) findViewById(R.id.TBigBombCount);


                amountBigBomb = Integer.parseInt(itemCountBigBomb.getText().toString());

                if(amountBigBomb>0) {
                    amountBigBomb--;
                    itemCountBigBomb.setText(Integer.toString(amountBigBomb));
                }
                else {
                    toastText("Just don't...");
                }
                powerOutput.setText(Integer.toString(calculatePower()));
                break;
            case R.id.BPlusNuclearWeaponAttack:
                powerOutput=(TextView) findViewById(R.id.TTotalPower);
                itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
                if(amountNuclearWeapon<nuclearWeapon) {
                    amountNuclearWeapon++;
                }else {
                    toastText("At this moment you have "+nuclearWeapon+" nuclear weapons");
                }
                itemCountNuclearWeapon.setText(Integer.toString(amountNuclearWeapon));
                powerOutput.setText(Integer.toString(calculatePower()));
                break;

            case R.id.BMinusNuclearWeaponAttack:
                itemCountNuclearWeapon = (TextView) findViewById(R.id.TNuclearWeaponCount);
                powerOutput=(TextView) findViewById(R.id.TTotalPower);

                amountNuclearWeapon = Integer.parseInt(itemCountNuclearWeapon.getText().toString());

                if(amountNuclearWeapon>0) {
                    amountNuclearWeapon--;
                    itemCountNuclearWeapon.setText(Integer.toString(amountNuclearWeapon));
                }
                else {
                    toastText("Just don't...");
                }
                powerOutput.setText(Integer.toString(calculatePower()));
                break;

            default:
                break;
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
}
