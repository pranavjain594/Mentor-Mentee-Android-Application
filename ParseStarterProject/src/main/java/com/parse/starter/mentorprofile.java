package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class mentorprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorprofile);
        setTitle("Mentor Details");
        ParseQuery<ParseUser> q=ParseUser.getQuery();
        q.whereEqualTo("name",ParseUser.getCurrentUser().getString("mentor"));
        q.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null) {
                    TextView roll = findViewById(R.id.roll);
                    TextView name = findViewById(R.id.name);
                    TextView phn = findViewById(R.id.textView7);
                    TextView email = findViewById(R.id.textView8);
                    TextView dep = findViewById(R.id.textView4);
                    roll.setText("ID - "+objects.get(0).getUsername());
                    email.setText("Email Id - "+objects.get(0).get("mentoremail"));
                    name.setText("Name - "+objects.get(0).getString("name"));
                    phn.setText("Ph.No. - "+objects.get(0).getString("phone"));
                    dep.setText("Department - "+objects.get(0).getString("department"));
                }else{
                    Toast.makeText(mentorprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
