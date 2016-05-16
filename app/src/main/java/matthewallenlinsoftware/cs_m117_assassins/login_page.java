package matthewallenlinsoftware.cs_m117_assassins;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class login_page extends AppCompatActivity {

    Firebase myFirebaseRef;
    EditText editText;
    public long LobbySize;
    public long [] active_users = {-1,-1,-1,-1,-1,-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
        myFirebaseRef.child("Lobby").child("User1").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[0] = (long) snapshot.getValue();
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        myFirebaseRef.child("Lobby").child("User2").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[1] = (long) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });

        myFirebaseRef.child("Lobby").child("User3").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[2] = (long) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });

        myFirebaseRef.child("Lobby").child("User4").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[3] = (long) snapshot.getValue();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        myFirebaseRef.child("Lobby").child("User5").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[4] = (long) snapshot.getValue();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        myFirebaseRef.child("Lobby").child("User6").child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[5] = (long) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError error) {}
        });

        myFirebaseRef.child("Lobby").child("Size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LobbySize = (long) snapshot.getValue();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        editText = (EditText) findViewById(R.id.username);
    }

    public void onClick(View v) {
        //figure out your user number
        int your_user = -1;
        for (int i = 0; i < active_users.length; i++) {
            if (active_users[i] == 0) {
                your_user = i + 1;
                break;
            }
        }
        if (your_user == -1) {
            showAlertDialogue("Sorry the Lobby is currently full!");
        } else if (editText.getText().toString().isEmpty() || editText.getText().toString().length() > 12) {
            showAlertDialogue("Your Username is too short or too long!");
        } else {
            //set User Active and name
            myFirebaseRef.child("Lobby").child("User" + your_user).child("Active").setValue(1);
            myFirebaseRef.child("Lobby").child("User" + your_user).child("Name").setValue(editText.getText().toString());
            myFirebaseRef.child("Lobby").child("Occupied").setValue(your_user);

            // store username and number
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UserName", editText.getText().toString());
            editor.putInt("UserNumber", your_user);
            editor.commit();

            //Starting a new Intent
            Intent nextScreen = new Intent(getApplicationContext(), lobby_page.class);
            startActivity(nextScreen);
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
