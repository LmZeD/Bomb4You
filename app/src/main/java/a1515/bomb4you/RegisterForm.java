package a1515.bomb4you;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterForm extends AppCompatActivity {

    //name
    //email
    //game mode
    //password
    //confrimPassword

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        WebRequests webRequests=new WebRequests();


    }

    public void sendRegistrationReq(){

    }
}
