package a1515.bomb4you;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ShopScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);
    }

    public void Button1000Cash(View view){
        int cash=(int)(R.integer.Cash);
        cash=cash+1000;
        Toast.makeText(this, "1000 cash have been added to your account!", Toast.LENGTH_SHORT).show();
    }

}
