package a1515.bomb4you;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShopScreen extends AppCompatActivity implements View.OnClickListener{
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";
    private final String scoreSetURL="http://bomb4you.tk/api/v1/score/set";
    private static String leaderboardsURL = "http://bomb4you.tk/api/v1/score/leaderboard_background";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_screen);

        smallBombController();
        bigBombController();
        dynamiteController();
        nuclearWeaponController();
        plusButtonController();

    }




    public void plusButtonController(){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        final int gold=sharedPref.getInt("Gold",0);
        final int score=sharedPref.getInt("Score",0);
        final int cash=sharedPref.getInt("Cash",0);
        final int dynamite=sharedPref.getInt("Dynamite",0);
        final int smallBomb=sharedPref.getInt("SmallBomb",0);
        final int bigBomb=sharedPref.getInt("BigBomb",0);
        final int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

        ImageButton button = (ImageButton)findViewById(R.id.BPlusShopScreen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopScreen.this);
                View view = getLayoutInflater().inflate(R.layout.pop_up_shop,null);
                ImageButton buyCash = (ImageButton) view.findViewById(R.id.BBuy1000cash);
                ImageButton buyGold = (ImageButton) view.findViewById(R.id.BBuy10000Gold);
                ImageButton exit=(ImageButton) view.findViewById(R.id.BExitPopUpShop);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                buyCash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setValuesInWeb(gold,score,cash+1000,dynamite,smallBomb,bigBomb,nuclearWeapon);
                        toastText("1000 cash added!");
                    }
                });

                buyGold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setValuesInWeb(gold+10000,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                        toastText("10000 gold added!");
                    }
                });

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ShopScreen.this,ShopScreen.class));
                    }
                });
            };
        });

    }




    public void nuclearWeaponController(){
        final ImageButton plusOne=(ImageButton)(findViewById(R.id.BPlus1NuclearWeapon));
        final ImageButton minusOne=(ImageButton)(findViewById(R.id.BMinus1NuclearWeapon));
        final ImageButton buyCash=(ImageButton)(findViewById(R.id.BBuyNuclearWeaponCash));
        final ImageButton buyGold=(ImageButton)(findViewById(R.id.BBuyNuclearWeaponGold));

        plusOne.setOnClickListener(this);
        minusOne.setOnClickListener(this);
        buyCash.setOnClickListener(this);
        buyGold.setOnClickListener(this);
    }
    public void bigBombController(){
        final ImageButton plusOne=(ImageButton)(findViewById(R.id.BPlus1BigBomb));
        final ImageButton minusOne=(ImageButton)(findViewById(R.id.BMinus1BigBomb));
        final ImageButton buyCash=(ImageButton)(findViewById(R.id.BBuyBigBombCash));
        final ImageButton buyGold=(ImageButton)(findViewById(R.id.BBuyBigBombGold));

        plusOne.setOnClickListener(this);
        minusOne.setOnClickListener(this);
        buyCash.setOnClickListener(this);
        buyGold.setOnClickListener(this);
    }
    public void dynamiteController(){
        final ImageButton plusOne=(ImageButton)(findViewById(R.id.BPlus1Dynamite));
        final ImageButton minusOne=(ImageButton)(findViewById(R.id.BMinus1Dynamite));
        final ImageButton buyCash=(ImageButton)(findViewById(R.id.BBuyDynamiteCash));
        final ImageButton buyGold=(ImageButton)(findViewById(R.id.BBuyDynamiteGold));

        plusOne.setOnClickListener(this);
        minusOne.setOnClickListener(this);
        buyCash.setOnClickListener(this);
        buyGold.setOnClickListener(this);
    }
    public void smallBombController(){
        final ImageButton plusOne=(ImageButton)(findViewById(R.id.BPlus1SmallBomb));
        final ImageButton minusOne=(ImageButton)(findViewById(R.id.BMinus1SmallBomb));
        final ImageButton buyCash=(ImageButton)(findViewById(R.id.BBuySmallBombCash));
        final ImageButton buyGold=(ImageButton)(findViewById(R.id.BBuySmallBombGold));

        plusOne.setOnClickListener(this);
        minusOne.setOnClickListener(this);
        buyCash.setOnClickListener(this);
        buyGold.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        int gold=sharedPref.getInt("Gold",0);
        int score=sharedPref.getInt("Score",0);
        int cash=sharedPref.getInt("Cash",0);
        int dynamite=sharedPref.getInt("Dynamite",0);
        int smallBomb=sharedPref.getInt("SmallBomb",0);
        int bigBomb=sharedPref.getInt("BigBomb",0);
        int nuclearWeapon=sharedPref.getInt("NuclearWeapon",0);

        switch (v.getId()) {
            //---------------------------------------------------------------------------
            case R.id.BPlus1SmallBomb:
                TextView itemCountSmallBomb = (TextView)findViewById(R.id.TSmallBombCount);
                TextView cashSmallBomb = (TextView)findViewById(R.id.TSmallBombCash);
                TextView goldSmallBomb = (TextView)findViewById(R.id.TSmallBombGold);
                int amountSmallBomb=Integer.parseInt(itemCountSmallBomb.getText().toString());
                int amountGoldSmallBomb=Integer.parseInt(goldSmallBomb.getText().toString());
                int amountCashSmallBomb=Integer.parseInt(cashSmallBomb.getText().toString());

                amountSmallBomb++;
                amountGoldSmallBomb=amountSmallBomb*3;
                amountCashSmallBomb=amountSmallBomb;
                itemCountSmallBomb.setText(Integer.toString(amountSmallBomb));
                cashSmallBomb.setText(Integer.toString(amountCashSmallBomb));
                goldSmallBomb.setText(Integer.toString(amountGoldSmallBomb));
                break;

            case R.id.BMinus1SmallBomb:
                itemCountSmallBomb = (TextView) findViewById(R.id.TSmallBombCount);
                cashSmallBomb = (TextView)findViewById(R.id.TSmallBombCash);
                goldSmallBomb = (TextView)findViewById(R.id.TSmallBombGold);
                amountSmallBomb = Integer.parseInt(itemCountSmallBomb.getText().toString());
                if(amountSmallBomb>0) {
                    amountSmallBomb--;
                    amountGoldSmallBomb=amountSmallBomb*3;
                    amountCashSmallBomb=amountSmallBomb;
                    cashSmallBomb.setText(Integer.toString(amountCashSmallBomb));
                    goldSmallBomb.setText(Integer.toString(amountGoldSmallBomb));
                    itemCountSmallBomb.setText(Integer.toString(amountSmallBomb));
                }
                else {
                    toastText("You cannot sell bombs!");
                }
                break;

            case R.id.BBuySmallBombCash:
                itemCountSmallBomb = (TextView) findViewById(R.id.TSmallBombCount);
                cashSmallBomb = (TextView)findViewById(R.id.TSmallBombCash);
                goldSmallBomb = (TextView)findViewById(R.id.TSmallBombGold);
                amountSmallBomb = Integer.parseInt(itemCountSmallBomb.getText().toString());
                amountCashSmallBomb=amountSmallBomb;

                if (amountCashSmallBomb>cash) {
                    toastText("You only have " + cash +" cash");
                }
                else if(amountSmallBomb==0){

                }
                else {
                    cash=cash-amountCashSmallBomb;
                    smallBomb=smallBomb+amountSmallBomb;
                    editor.remove("Cash");
                    editor.remove("SmallBomb");
                    editor.putInt("Cash",cash);
                    editor.putInt("SmallBomb",smallBomb);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountSmallBomb.setText("0");
                    cashSmallBomb.setText("0");
                    goldSmallBomb.setText("0");
                    toastText("Done!");
                    toastText("You now have "+smallBomb+" small bombs");
                }
                break;

            case R.id.BBuySmallBombGold:
                itemCountSmallBomb = (TextView) findViewById(R.id.TSmallBombCount);
                cashSmallBomb = (TextView)findViewById(R.id.TSmallBombCash);
                goldSmallBomb = (TextView)findViewById(R.id.TSmallBombGold);
                amountSmallBomb = Integer.parseInt(itemCountSmallBomb.getText().toString());
                amountGoldSmallBomb=amountSmallBomb*3;

                if (amountGoldSmallBomb>gold) {
                    toastText("You only have " + gold +" gold");
                }
                else if(amountSmallBomb==0){

                }
                else {
                    gold=gold-amountGoldSmallBomb;
                    smallBomb=smallBomb+amountSmallBomb;
                    editor.remove("Gold");
                    editor.remove("SmallBomb");
                    editor.putInt("Gold",gold);
                    editor.putInt("SmallBomb",smallBomb);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountSmallBomb.setText("0");
                    cashSmallBomb.setText("0");
                    goldSmallBomb.setText("0");
                    toastText("Done!");
                    toastText("You now have "+smallBomb+" small bombs");
                }

                break;
            //---------------------------------------------------------------------------
            case R.id.BPlus1Dynamite:
                TextView itemCountDynamite = (TextView)findViewById(R.id.TDynamiteCount);
                TextView cashDynamite = (TextView)findViewById(R.id.TDynamiteCash);
                TextView goldDynamite = (TextView)findViewById(R.id.TDynamiteGold);
                int amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                int amountDynamiteGold=Integer.parseInt(goldDynamite.getText().toString());
                int amountDynamiteCash=Integer.parseInt(cashDynamite.getText().toString());

                amountDynamite++;
                amountDynamiteGold=amountDynamite*9;
                amountDynamiteCash=amountDynamite*3;
                itemCountDynamite.setText(Integer.toString(amountDynamite));
                cashDynamite.setText(Integer.toString(amountDynamiteCash));
                goldDynamite.setText(Integer.toString(amountDynamiteGold));
                break;

            case R.id.BMinus1Dynamite:
                itemCountDynamite = (TextView)findViewById(R.id.TDynamiteCount);
                cashDynamite = (TextView)findViewById(R.id.TDynamiteCash);
                goldDynamite = (TextView)findViewById(R.id.TDynamiteGold);
                amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                amountDynamiteGold=Integer.parseInt(goldDynamite.getText().toString());
                amountDynamiteCash=Integer.parseInt(cashDynamite.getText().toString());
                if(amountDynamite>0) {
                    amountDynamite--;
                    amountDynamiteGold = amountDynamite * 9;
                    amountDynamiteCash = amountDynamite * 3;
                    itemCountDynamite.setText(Integer.toString(amountDynamite));
                    cashDynamite.setText(Integer.toString(amountDynamiteCash));
                    goldDynamite.setText(Integer.toString(amountDynamiteGold));
                }
                else {
                    toastText("You cannot sell bombs!");
                }
                break;

            case R.id.BBuyDynamiteCash:
                itemCountDynamite = (TextView)findViewById(R.id.TDynamiteCount);
                cashDynamite = (TextView)findViewById(R.id.TDynamiteCash);
                goldDynamite = (TextView)findViewById(R.id.TDynamiteGold);
                amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                amountDynamiteCash=amountDynamite*3;
                if(amountDynamiteCash>cash){
                    toastText("You only have " + cash +" cash");
                }
                else if(amountDynamite==0){

                }else {
                    cash=cash-amountDynamiteCash;
                    dynamite=dynamite+amountDynamite;
                    editor.remove("Cash");
                    editor.remove("Dynamite");
                    editor.putInt("Cash",cash);
                    editor.putInt("Dynamite",dynamite);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountDynamite.setText("0");
                    cashDynamite.setText("0");
                    goldDynamite.setText("0");
                    toastText("Done!");
                    toastText("You now have "+dynamite+" dynamite");
                }
                break;

            case R.id.BBuyDynamiteGold:
                itemCountDynamite = (TextView)findViewById(R.id.TDynamiteCount);
                cashDynamite = (TextView)findViewById(R.id.TDynamiteCash);
                goldDynamite = (TextView)findViewById(R.id.TDynamiteGold);
                amountDynamite=Integer.parseInt(itemCountDynamite.getText().toString());
                amountDynamiteGold=amountDynamite*9;
                if(amountDynamiteGold>gold){
                    toastText("You only have " + gold +" gold");
                }
                else if(amountDynamite==0){

                }else {
                    gold=gold-amountDynamiteGold;
                    dynamite=dynamite+amountDynamite;
                    editor.remove("Gold");
                    editor.remove("Dynamite");
                    editor.putInt("Gold",gold);
                    editor.putInt("Dynamite",dynamite);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountDynamite.setText("0");
                    cashDynamite.setText("0");
                    goldDynamite.setText("0");
                    toastText("Done!");
                    toastText("You now have "+dynamite+" dynamite");
                }
                break;
            //---------------------------------------------------------------------------
            case R.id.BPlus1BigBomb:
                TextView itemCountBigBomb = (TextView)findViewById(R.id.TBigBombCount);
                TextView cashBigBomb = (TextView)findViewById(R.id.TBigBombCash);
                TextView goldBigBomb = (TextView)findViewById(R.id.TBigBombGold);
                int amountBigBomb=Integer.parseInt(itemCountBigBomb.getText().toString());
                int amountGoldBigBomb=Integer.parseInt(goldBigBomb.getText().toString());
                int amountCashBigBomb=Integer.parseInt(cashBigBomb.getText().toString());

                amountBigBomb++;
                amountGoldBigBomb=amountBigBomb*27;
                amountCashBigBomb=amountBigBomb*9;
                itemCountBigBomb.setText(Integer.toString(amountBigBomb));
                cashBigBomb.setText(Integer.toString(amountCashBigBomb));
                goldBigBomb.setText(Integer.toString(amountGoldBigBomb));
                break;

            case R.id.BMinus1BigBomb:
                itemCountBigBomb = (TextView) findViewById(R.id.TBigBombCount);
                cashBigBomb = (TextView)findViewById(R.id.TBigBombCash);
                goldBigBomb = (TextView)findViewById(R.id.TBigBombGold);
                amountBigBomb = Integer.parseInt(itemCountBigBomb.getText().toString());
                if(amountBigBomb>0) {
                    amountBigBomb--;
                    amountGoldBigBomb=amountBigBomb*27;
                    amountCashBigBomb=amountBigBomb*9;
                    cashBigBomb.setText(Integer.toString(amountCashBigBomb));
                    goldBigBomb.setText(Integer.toString(amountGoldBigBomb));
                    itemCountBigBomb.setText(Integer.toString(amountBigBomb));
                } else {
                    toastText("You cannot sell bombs!");
                }
                break;

            case R.id.BBuyBigBombCash:
                itemCountBigBomb = (TextView) findViewById(R.id.TBigBombCount);
                cashBigBomb = (TextView)findViewById(R.id.TBigBombCash);
                goldBigBomb = (TextView)findViewById(R.id.TBigBombGold);
                amountBigBomb = Integer.parseInt(itemCountBigBomb.getText().toString());
                amountCashBigBomb=amountBigBomb*9;
                if (amountCashBigBomb>cash) {
                    toastText("You only have " + cash +" cash");
                }else if (amountBigBomb==0) {
                }else {
                    cash=cash-amountCashBigBomb;
                    bigBomb=bigBomb+amountBigBomb;
                    editor.remove("Cash");
                    editor.remove("BigBomb");
                    editor.putInt("Cash",cash);
                    editor.putInt("BigBomb",bigBomb);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountBigBomb.setText("0");
                    cashBigBomb.setText("0");
                    goldBigBomb.setText("0");
                    toastText("Done!");
                    toastText("You now have "+bigBomb+" big bombs");
                }
                break;

            case R.id.BBuyBigBombGold:
                itemCountBigBomb = (TextView) findViewById(R.id.TBigBombCount);
                cashBigBomb = (TextView)findViewById(R.id.TBigBombCash);
                goldBigBomb = (TextView)findViewById(R.id.TBigBombGold);
                amountBigBomb = Integer.parseInt(itemCountBigBomb.getText().toString());
                amountGoldBigBomb=amountBigBomb*27;
                if (amountGoldBigBomb>gold) {
                    toastText("You only have " + gold +" gold");
                }else if (amountBigBomb==0) {
                }else {
                    gold=gold-amountGoldBigBomb;
                    bigBomb=bigBomb+amountBigBomb;
                    editor.remove("Gold");
                    editor.remove("BigBomb");
                    editor.putInt("Gold",gold);
                    editor.putInt("BigBomb",bigBomb);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountBigBomb.setText("0");
                    cashBigBomb.setText("0");
                    goldBigBomb.setText("0");
                    toastText("Done!");
                    toastText("You now have "+bigBomb+" big bombs");
                }
                break;

            //---------------------------------------------------------------------------
            case R.id.BPlus1NuclearWeapon:
                TextView itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                TextView cashNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCash);
                TextView goldNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponGold);
                int amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
                int amountGoldNuclearWeapon=Integer.parseInt(goldNuclearWeapon.getText().toString());
                int amountCashNuclearWeapon=Integer.parseInt(cashNuclearWeapon.getText().toString());

                amountNuclearWeapon++;
                amountGoldNuclearWeapon=amountNuclearWeapon*3000;
                amountCashNuclearWeapon=amountNuclearWeapon*1000;
                itemCountNuclearWeapon.setText(Integer.toString(amountNuclearWeapon));
                cashNuclearWeapon.setText(Integer.toString(amountCashNuclearWeapon));
                goldNuclearWeapon.setText(Integer.toString(amountGoldNuclearWeapon));
                break;

            case R.id.BMinus1NuclearWeapon:
                itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                cashNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCash);
                goldNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponGold);
                amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
                amountGoldNuclearWeapon=Integer.parseInt(goldNuclearWeapon.getText().toString());
                amountCashNuclearWeapon=Integer.parseInt(cashNuclearWeapon.getText().toString());
                if(amountNuclearWeapon>0) {
                    amountNuclearWeapon--;
                    amountGoldNuclearWeapon=amountNuclearWeapon*3000;
                    amountCashNuclearWeapon=amountNuclearWeapon*1000;
                    itemCountNuclearWeapon.setText(Integer.toString(amountNuclearWeapon));
                    cashNuclearWeapon.setText(Integer.toString(amountCashNuclearWeapon));
                    goldNuclearWeapon.setText(Integer.toString(amountGoldNuclearWeapon));
                }
                else {
                    toastText("You cannot sell bombs!");
                }
                break;

            case R.id.BBuyNuclearWeaponCash:
                itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                cashNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCash);
                goldNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponGold);
                amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
                amountCashNuclearWeapon=amountNuclearWeapon*1000;
                if (amountCashNuclearWeapon>cash) {
                    toastText("You only have " + cash +" cash");
                }else if(amountNuclearWeapon==0) {
                }
                else
                {
                    cash=cash-amountCashNuclearWeapon;
                    nuclearWeapon=nuclearWeapon+amountNuclearWeapon;
                    editor.remove("Cash");
                    editor.remove("NuclearWeapon");
                    editor.putInt("Cash",cash);
                    editor.putInt("NuclearWeapon",nuclearWeapon);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountNuclearWeapon.setText("0");
                    cashNuclearWeapon.setText("0");
                    goldNuclearWeapon.setText("0");
                    toastText("Done!");
                    toastText("You now have "+nuclearWeapon+" nuclear weapons");
                }
                break;

            case R.id.BBuyNuclearWeaponGold:
                itemCountNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCount);
                cashNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponCash);
                goldNuclearWeapon = (TextView)findViewById(R.id.TNuclearWeaponGold);
                amountNuclearWeapon=Integer.parseInt(itemCountNuclearWeapon.getText().toString());
                amountGoldNuclearWeapon=amountNuclearWeapon*3000;
                if (amountGoldNuclearWeapon>gold) {
                    toastText("You only have " + gold +" gold");
                }
                else if(amountNuclearWeapon==0) {
                }
                else {
                    gold=gold-amountGoldNuclearWeapon;
                    nuclearWeapon=nuclearWeapon+amountNuclearWeapon;
                    editor.remove("Gold");
                    editor.remove("NuclearWeapon");
                    editor.putInt("Gold",gold);
                    editor.putInt("NuclearWeapon",nuclearWeapon);
                    editor.apply();
                    editor.commit();
                    setValuesInWeb(gold,score,cash,dynamite,smallBomb,bigBomb,nuclearWeapon);
                    itemCountNuclearWeapon.setText("0");
                    cashNuclearWeapon.setText("0");
                    goldNuclearWeapon.setText("0");
                    toastText("Done!");
                    toastText("You now have "+nuclearWeapon+" nuclear weapons");
                }
                break;

            //---------------------------------------------------------------------------
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
