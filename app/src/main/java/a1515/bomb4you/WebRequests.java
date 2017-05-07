package a1515.bomb4you;


import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

public class WebRequests extends AppCompatActivity{

    private RequestQueue requestQueue;
    public static final String registerURL="http://bomb4you.tk/api/v1/auth/register";
    private StringRequest request;

    public void toastText(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }


    public void sendRequestForUserInfo(final String email, final String password, final String URL,
                                       final String gameMode,final String isGuest,final String name){
        requestQueue = Volley.newRequestQueue(this);

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("user_token")){
                        toastText("Successfully registered");
                        startActivity(new Intent(getApplicationContext(),MainScreen.class));
                    }
                    else{
                        toastText("Wrong registration data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastText("Wrong registration data");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap =new HashMap<String,String>();
                hashMap.put("gamemode",gameMode);
                hashMap.put("is_guest",isGuest);
                hashMap.put("name",name);
                hashMap.put("email",email);
                hashMap.put("password",password);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}