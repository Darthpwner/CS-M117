package matthewallenlinsoftware.cs_m117_assassins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class login_page extends AppCompatActivity {

    Firebase myFirebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
        myFirebaseRef.child("Count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("FUCKING FIREBASE");
                System.out.println(snapshot.getValue());
                System.out.println("FUCKING FIREBASE");
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

    }

    public void onClick(View v) {
        //Starting a new Intent
        Intent nextScreen = new Intent(getApplicationContext(), lobby_page.class);

        nextScreen.putExtra("username", R.id.username);

        System.out.println("username: " + R.id.username);

        //Sending data to another Activity
        startActivity(nextScreen);
    }
}
