package com.example.smart_campus_app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class student_point_at_issue  extends AppCompatActivity implements View.OnClickListener {
    private CardView card1,card2,card3,card4,card5;


    protected void onCreate(Bundle savedInst) {
        super.onCreate(savedInst);
        setContentView(R.layout.student_issue);

        card1 = findViewById(R.id.Management_issue);
        card2 = findViewById(R.id.Save_Issue);
        card3 = findViewById(R.id.Recommended_Book);
        card4 = findViewById(R.id.Book_issue);
        card5 = findViewById(R.id.Return_Book);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.Management_issue:
                i = new Intent(this,Management_Issue.class);
                startActivity(i);
                break;
            case R.id.Save_Issue:
                i = new Intent(this, Save_Issue.class);
                startActivity(i);
                break;
            case R.id.Recommended_Book:
                i = new Intent(this,Recommended_Book.class);
                startActivity(i);
                break;
            case R.id.Book_issue:
                i = new Intent(this,Book_Issue.class);
                startActivity(i);
                break;
            case R.id.Return_Book:
                i = new Intent(this,Return_Book.class);
                startActivity(i);
                break;
        }

    }
}
