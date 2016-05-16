package matthewallenlinsoftware.cs_m117_assassins;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class lobby_page extends AppCompatActivity {

    Button startButton;
    TextView User1Textview;
    TextView User2Textview;
    TextView User3Textview;
    TextView User4Textview;
    TextView User5Textview;
    TextView User6Textview;
    TextView subtitle;


    public long Occupied;
    Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_page);
        startButton = (Button) findViewById(R.id.start_game_button);
        User1Textview = (TextView) findViewById(R.id.lobby_user_1);
        User2Textview = (TextView) findViewById(R.id.lobby_user_2);
        User3Textview = (TextView) findViewById(R.id.lobby_user_3);
        User4Textview = (TextView) findViewById(R.id.lobby_user_4);
        User5Textview = (TextView) findViewById(R.id.lobby_user_5);
        User6Textview = (TextView) findViewById(R.id.lobby_user_6);
        subtitle = (TextView) findViewById(R.id.lobby_page_user_number);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
        myFirebaseRef.child("Lobby").child("Occupied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Occupied = (long) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        myFirebaseRef.child("Lobby").child("User1").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User1Textview.setText((String) snapshot.getValue());
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        myFirebaseRef.child("Lobby").child("User2").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User2Textview.setText((String) snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });

        myFirebaseRef.child("Lobby").child("User3").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User3Textview.setText((String) snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });

        myFirebaseRef.child("Lobby").child("User4").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User4Textview.setText((String) snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        myFirebaseRef.child("Lobby").child("User5").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User5Textview.setText((String) snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        myFirebaseRef.child("Lobby").child("User6").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User6Textview.setText((String) snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });
        myFirebaseRef.child("Lobby").child("Occupied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Occupied = (long) snapshot.getValue();
                subtitle.setText(Occupied + " of 6 people have joined");
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });
        myFirebaseRef.child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long game_started = (long) snapshot.getValue();
                if (game_started == 1){
                    int usernumber = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("UserNumber", -1);
                    //Starting a new Intent
                    Intent nextScreen = new Intent(getApplicationContext(), game_page.class);
                    startActivity(nextScreen);
                }
                subtitle.setText(Occupied + " of 6 people have joined");
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });
    }

    public void onClick(View v) {

        if (Occupied < 2) {
            showAlertDialogue("Can't have game with less than two players!");
        }
        else {
            //retrieve usernumber
            int usernumber = PreferenceManager.getDefaultSharedPreferences(this).getInt("UserNumber", -1);
            if(usernumber == 1){
                myFirebaseRef.child("Active").setValue(1);
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), game_page.class);
                startActivity(nextScreen);
            }
            else {
                showAlertDialogue("You did not create the Lobby. Wait for the owner to start the game");
            }
        }
    }

    public void showAlertDialogue(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Invalid");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
