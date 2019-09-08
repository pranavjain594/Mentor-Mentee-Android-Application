package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class editprofile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name,phone,email,opswrd,npswrd,cnpswrd;
    ArrayList<String> m;
    String mentor;
    Spinner s;
    boolean x,f;
    public void nextact(){
        if(ParseUser.getCurrentUser().getString("studentorfaculty").matches("dean")){
            Intent i = new Intent(getApplicationContext(), dean.class);
            startActivity(i);
        }
        else {
            if (f) {
                Intent i = new Intent(getApplicationContext(), mentor.class);
                startActivity(i);
            } else {
                Intent i = new Intent(getApplicationContext(), student.class);
                startActivity(i);
            }
        }
    }
    public void update(View view) {
        if (name.getText().toString().matches("") || phone.getText().toString().matches("") || email.getText().toString().matches("") || opswrd.getText().toString().matches("")) {
            Toast.makeText(this, "Fill the necessary details", Toast.LENGTH_SHORT).show();
        }
        else{
            if(ParseUser.getCurrentUser().getString("currentpassword").matches(opswrd.getText().toString())) {
                ParseUser.getCurrentUser().put("name", name.getText().toString());
                ParseUser.getCurrentUser().put("phone", phone.getText().toString());
                ParseUser.getCurrentUser().put("mentor", mentor);
                ParseUser.getCurrentUser().setEmail(email.getText().toString());
                if (!npswrd.getText().toString().matches("") || !cnpswrd.getText().toString().matches("")) {
                    if (npswrd.getText().toString().matches(cnpswrd.getText().toString())) {
                        x = true;
                        ParseUser.getCurrentUser().setPassword(npswrd.getText().toString());
                        ParseUser.getCurrentUser().put("currentpassword", npswrd.getText().toString());
                    } else {
                        x = false;
                        Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                if (x) {
                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(editprofile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                nextact();
                            } else {
                                Toast.makeText(editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            else{
                Toast.makeText(this, "Current password is wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        setTitle("Edit Your Profile");
        name=findViewById(R.id.editText10);
        phone=findViewById(R.id.editText12);
        email=findViewById(R.id.editText13);
        opswrd=findViewById(R.id.editText14);
        npswrd=findViewById(R.id.editText15);
        cnpswrd=findViewById(R.id.editText16);
        s=findViewById(R.id.spinner);
        x=true;

        if(!ParseUser.getCurrentUser().getString("studentorfaculty").matches("student")){
            mentor="none";
            f=true;
            name.setEnabled(false);
            s.setVisibility(View.INVISIBLE);
        }

        name.setText(ParseUser.getCurrentUser().getString("name"));
        phone.setText(ParseUser.getCurrentUser().getString("phone"));
        email.setText(ParseUser.getCurrentUser().getString("email"));

        m=new ArrayList();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(editprofile.this,
                android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereEqualTo("studentorfaculty","faculty");
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        String mntr=ParseUser.getCurrentUser().getString("mentor");
                        m.clear();
                        m.add(mntr);
                        for(ParseUser user:objects){
                            if(!user.getString("name").matches(mntr)) {
                                m.add(user.getString("name"));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        s.setSelection(-1);
                    }
                }
                else{
                    Toast.makeText(editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(!f) {
            mentor = m.get(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(editprofile.this,"Select Your Mentor", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        nextact();
        super.onBackPressed();
    }

}
