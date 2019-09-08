package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class mentor extends AppCompatActivity {
    public void leave(View view){
        Intent i=new Intent(getApplicationContext(),leaves.class);
        startActivity(i);
    }
    public void complaint(View view){
        Intent i=new Intent(getApplicationContext(),complains.class);
        startActivity(i);
    }
    public void edit(View view){
        Intent i=new Intent(getApplicationContext(),editprofile.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.logout, menu);
        MenuItem item = menu.findItem(R.id.mentorprofile);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.editprofile);
        item2.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Are You Sure?");
            builder.setPositiveButton("logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ParseUser.logOut();
                    Intent k=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(k);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        setTitle("Hey "+ ParseUser.getCurrentUser().get("name")+"!");

    }
    public void onBackPressed() {
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }
}
