package a1515.bomb4you;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaunchScreen extends AppCompatActivity {
    private int backButtonPressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);


    }

    public void toastText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        backButtonPressed++;
        if(backButtonPressed>10){
            toastText("Stop smashing back button!");
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.bomb4you.tk/login"));
        startActivity(intent);
    }
    public void OptionsButtonClick(View view){//options screen

    }




}
