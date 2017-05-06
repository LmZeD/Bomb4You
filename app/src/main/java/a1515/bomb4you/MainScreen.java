package a1515.bomb4you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static a1515.bomb4you.R.id.TEmail;
import static a1515.bomb4you.R.id.TGoldDisplayMainScreen;


public class MainScreen extends AppCompatActivity {
    int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        backButtonCount=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //----------------------------------------------------------
        Intent intent =getIntent();

        Player player=new Player();

        SetDisplayValues();

    }

    public void SetDisplayValues(){


        TextView goldDisplay=(TextView)(findViewById(TGoldDisplayMainScreen));



        //goldDisplay.setText(Integer.toString(gold));

    }

    @Override
    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("RememberMe");
            editor.putInt("RememberMe",0);
            editor.commit();
            Intent intent = new Intent(this,LaunchScreen.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to return to launch screen.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public void RedirectToShop(View view){
        backButtonCount=0;
        Intent intent=new Intent(this,ShopScreen.class);
        startActivity(intent);
    }
}
