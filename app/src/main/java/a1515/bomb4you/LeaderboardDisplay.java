package a1515.bomb4you;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardDisplay extends AppCompatActivity{
    private RequestQueue requestQueue;
    private StringRequest request;
    private final String leaderboardsURL="http://bomb4you.tk/api/v1/score/leaderboard";
    private final String userInfoURL="http://bomb4you.tk/api/v1/user/info";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.leaderboard_display);




        }
        catch (Exception ex){
            //toastText("Crashed...");
            toastText(ex.getMessage().toString());
            startActivity(new Intent(LeaderboardDisplay.this,LaunchScreen.class));
        }


    }



    public void getLeaderboards(final int buttonInd){

        final TextView element1=(TextView)findViewById(R.id.TLeaderboardElement1);
        final TextView element2=(TextView)findViewById(R.id.TLeaderboardElement2);
        final TextView element3=(TextView)findViewById(R.id.TLeaderboardElement3);
        final TextView element4=(TextView)findViewById(R.id.TLeaderboardElement4);
        final TextView element5=(TextView)findViewById(R.id.TLeaderboardElement5);
        final TextView element6=(TextView)findViewById(R.id.TLeaderboardElement6);
        final TextView element7=(TextView)findViewById(R.id.TLeaderboardElement7);
        final TextView element8=(TextView)findViewById(R.id.TLeaderboardElement8);
        final TextView element9=(TextView)findViewById(R.id.TLeaderboardElement9);
        final TextView element10=(TextView)findViewById(R.id.TLeaderboardElement10);
        final TextView[] allViews=new TextView[10];
        allViews[0]=element1;
        allViews[1]=element2;
        allViews[2]=element3;
        allViews[3]=element4;
        allViews[4]=element5;
        allViews[5]=element6;
        allViews[6]=element7;
        allViews[7]=element8;
        allViews[8]=element9;
        allViews[9]=element10;


        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        requestQueue = Volley.newRequestQueue(this);
        final String user_token=sharedPref.getString("user_token","");



        if(user_token.equals("")){//security check
            toastText("Login info corrupted. Please log in again");
            editor.clear();
            Intent intent =new Intent(this,Login.class);
            startActivity(intent);
        }
        else {
            request = new StringRequest(Request.Method.POST, leaderboardsURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray array=jsonObject.getJSONArray("list");

                        if(buttonInd==2){//top10
                            clearFields();
                            for (int i=0;i<10;i++){
                                JSONObject objFromArr=array.getJSONObject(i);
                                allViews[i].setText((i+1)+". "+objFromArr.getString("name").toString()+" - "+objFromArr.getString("score").toString() + " points");
                            }
                        }
                        if(buttonInd==1){//nearMe
                            clearFields();
                            int position=0;
                            for (int i=0;i<array.length();i++){
                                JSONObject objFromArr=array.getJSONObject(i);
                                if(objFromArr.getString("id").equals(jsonObject.getString("current_user_id"))){
                                    position=i;
                                }
                            }
                            if(position<=4) {
                                int k=0;
                                    for (int i = 0; i < 10; i++) {
                                        JSONObject objFromArr=array.getJSONObject(i);
                                        allViews[k].setText((i+1)+". "+objFromArr.getString("name").toString()+" - "+objFromArr.getString("score").toString() + " points");
                                        k++;
                                    }
                            }
                            else if(position>4 && position<array.length()-5){
                                for (int i=position-4;i<position+5;i++){
                                    JSONObject objFromArr=array.getJSONObject(i);
                                    allViews[i].setText((i+1)+". "+objFromArr.getString("name").toString()+" - "+objFromArr.getString("score").toString() + " points");
                                }
                            }
                            else{
                                int k=0;
                                for (int i=array.length()-10;i<array.length();i++){
                                    JSONObject objFromArr=array.getJSONObject(i);
                                    allViews[k++].setText((i+1)+". "+objFromArr.getString("name").toString()+" - "+objFromArr.getString("score").toString() + " points");
                                }
                            }
                        }
                        if(buttonInd==3){//me
                            clearFields();
                            for (int i=0;i<array.length();i++){
                                JSONObject objFromArr=array.getJSONObject(i);
                                if(objFromArr.getString("id").equals(jsonObject.getString("current_user_id"))){
                                    element1.setText((i+1)+". "+objFromArr.getString("name").toString()+" - "+objFromArr.getString("score").toString() + " points");
                                }
                            }
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
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap =new HashMap<String,String>();
                    hashMap.put("user-token",user_token);
                    return hashMap;
                }
            };
            requestQueue.add(request);
        }

    }
    public void clearFields(){
        final TextView element1=(TextView)findViewById(R.id.TLeaderboardElement1);
        final TextView element2=(TextView)findViewById(R.id.TLeaderboardElement2);
        final TextView element3=(TextView)findViewById(R.id.TLeaderboardElement3);
        final TextView element4=(TextView)findViewById(R.id.TLeaderboardElement4);
        final TextView element5=(TextView)findViewById(R.id.TLeaderboardElement5);
        final TextView element6=(TextView)findViewById(R.id.TLeaderboardElement6);
        final TextView element7=(TextView)findViewById(R.id.TLeaderboardElement7);
        final TextView element8=(TextView)findViewById(R.id.TLeaderboardElement8);
        final TextView element9=(TextView)findViewById(R.id.TLeaderboardElement9);
        final TextView element10=(TextView)findViewById(R.id.TLeaderboardElement10);
        final TextView[] allViews=new TextView[10];
        allViews[0]=element1;
        allViews[1]=element2;
        allViews[2]=element3;
        allViews[3]=element4;
        allViews[4]=element5;
        allViews[5]=element6;
        allViews[6]=element7;
        allViews[7]=element8;
        allViews[8]=element9;
        allViews[9]=element10;
        for (int i=0;i<10;i++){
            allViews[i].setText(" ");
        }
    }

    public void showTop10(View view){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(!sharedPref.getString("user_token","").equals("")){
            getLeaderboards(2);
        }
        else toastText("Log in first!");
    }

    public void showMe(View view){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(!sharedPref.getString("user_token","").equals("")){
            getLeaderboards(3);
        }
        else toastText("Log in first!");
    }

    public void showNearMe(View view){
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(!sharedPref.getString("user_token","").equals("")){
            getLeaderboards(1);
        }
        else toastText("Log in first!");
    }

    @Override
    public void onBackPressed() {
        final SharedPreferences sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(!sharedPref.getString("user_token","").equals("")) {
            startActivity(new Intent(LeaderboardDisplay.this, MainScreen.class));
        }
        else startActivity(new Intent(LeaderboardDisplay.this,LaunchScreen.class));
    }

    public void toastText(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }


}

