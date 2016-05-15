package matthewallenlinsoftware.cs_m117_assassins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class lobby_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_page);

    }

    public void onClick(View v) {
        //Starting a new Intent
        Intent nextScreen = new Intent(getApplicationContext(), game_page.class);

        //Sending data to another Activity
        startActivity(nextScreen);
    }
}
