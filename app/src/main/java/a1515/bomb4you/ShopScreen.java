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
        addCash(1000);
        Toast.makeText(this, "1000 cash have been added to your account!", Toast.LENGTH_SHORT).show();
    }
    public void Button10Gold(View view){
        addGold(10);
        Toast.makeText(this, "10 gold have been added to your account!", Toast.LENGTH_SHORT).show();
    }


    public void addGold(int amount){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int prevAmount = sharedPref.getInt("Gold",0);
        prevAmount=prevAmount+amount;//new value to put in
        editor.putInt("Gold",prevAmount);
        editor.commit();
    }

    public void addCash(int amount){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int prevAmount = sharedPref.getInt("Cash",0);
        prevAmount=prevAmount+amount;//new value to put in
        editor.putInt("Cash",prevAmount);
        editor.commit();
    }

}
