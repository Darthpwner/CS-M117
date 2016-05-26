package matthewallenlinsoftware.cs_m117_assassins;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class selection_page extends AppCompatActivity {

    Button startExistingGame;
    Button startNewGame;
    public boolean GameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);
        startExistingGame = (Button) findViewById(R.id.join_existing_game);
        startNewGame = (Button) findViewById(R.id.join_new_game_button);

        //Firebase Content
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
        myFirebaseRef.child("Firebase").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("FUCKING FIREBASE IS");
                System.out.println(snapshot.getValue());
                System.out.println("HELL YEAH!!!");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
        myFirebaseRef.child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long value = (long) snapshot.getValue();
                if (value == 0){
                    GameActive = false;
                    Firebase.setAndroidContext(selection_page.this);
                    Firebase myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
                    myFirebaseRef.child("Lobby").child("User1").child("Name").setValue("");
                    myFirebaseRef.child("Lobby").child("User2").child("Name").setValue("");
                    myFirebaseRef.child("Lobby").child("User3").child("Name").setValue("");
                    myFirebaseRef.child("Lobby").child("User4").child("Name").setValue("");
                    myFirebaseRef.child("Lobby").child("User5").child("Name").setValue("");
                    myFirebaseRef.child("Lobby").child("User6").child("Name").setValue("");
                }
                else {GameActive = true;}
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        //button listeners
        startExistingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if game active
                if (GameActive) {
                    //Starting a new Intent
                    Intent nextScreen = new Intent(getApplicationContext(), game_page.class);
                    //Sending data to another Activity
                    startActivity(nextScreen);
                } else {
                    showAlertDialogue("Sorry no game currently available");
                }
            }
        });
        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameActive) {
                    showAlertDialogue("Sorry a game is currently running");
                } else {
                    //Starting a new Intent
                    Intent nextScreen = new Intent(getApplicationContext(), login_page.class);
                    //Sending data to another Activity
                    startActivity(nextScreen);
                }
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
