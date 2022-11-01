package com.example.smart_campus_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart_campus_app1.model.student_model;
import com.example.smart_campus_app1.ui.IssueRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import util.StudentUser;

public class Save_Issue extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private List<student_model> studentList;
    private RecyclerView recyclerView;
    private IssueRecyclerAdapter issueRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("Management");
    private TextView noPostsEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_issues);
        noPostsEntry = findViewById(R.id.list_of_issues);
        recyclerView = findViewById(R.id.recyclerView_issues);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //posts ArrayList
        studentList = new ArrayList<>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                if(user != null && firebaseAuth != null){
                    startActivity(new Intent(
                            Save_Issue.this,Management_Issue.class
                    ));
                }
                else{
                    Toast.makeText(this, "user is null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_signout:
                if(user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(Save_Issue.this,MainActivity.class));
                    Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("userId", StudentUser.getInstance().getUserid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        studentList.clear();
                        if(!queryDocumentSnapshots.isEmpty()){
                            for (QueryDocumentSnapshot students: queryDocumentSnapshots){
                                student_model mod  = students.toObject(student_model.class);
                                studentList.add(mod);
                            }
                            //Recyclerview
                            issueRecyclerAdapter = new
                                    IssueRecyclerAdapter(Save_Issue.this,studentList);
                            recyclerView.setAdapter(issueRecyclerAdapter);

                            issueRecyclerAdapter.notifyDataSetChanged();

                        }
                        else{
                            noPostsEntry.setVisibility(View.VISIBLE);
                            Toast.makeText(Save_Issue.this, "Hey bro there's no data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Any failure
                        Toast.makeText(Save_Issue.this, "Opps! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}