package com.example.smart_campus_app1;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import util.StudentUser;


//class
public class Student extends AppCompatActivity {
    //attribute
    private Button log_in ;
    private Button sign_up;
    private EditText email_et;
    private EditText password_et;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("StudentUsers");

//methods
    protected void onCreate(Bundle savedInst) {
        super.onCreate(savedInst);
        setContentView(R.layout.student_layout);
        log_in = findViewById(R.id.student_layout_Login_btn);
        sign_up = findViewById(R.id.student_layout_Signup_btn);
        email_et = findViewById(R.id.student_layout_Email_et);
        password_et = findViewById(R.id.student_layout_Password_et);
        firebaseAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student.this,Signup.class);
                startActivity(i);
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailPasswordUser(email_et.getText().toString().trim(), password_et.getText().toString().trim());

            }
        });



    }
    // fixed this code in 10 september 2022
    private void LoginEmailPasswordUser(String m_email, String m_password) {
        if(!TextUtils.isEmpty(m_email) && !TextUtils.isEmpty(m_password)){
            firebaseAuth.signInWithEmailAndPassword(m_email,m_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user!= null;
                    Toast.makeText(Student.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    final String currentUserId = user.getUid();
                    collectionReference.whereEqualTo("userid",currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Toast.makeText(Student.this, "Please enter a valid email or password", Toast.LENGTH_SHORT).show();
                            }
                            assert value != null;
                            if(!value.isEmpty()){
                                for (QueryDocumentSnapshot snapshot:value){
                                    StudentUser studentUser = StudentUser.getInstance();
                                    studentUser.setUsername(snapshot.getString("username"));
                                    studentUser.setUserid(snapshot.getString("userid"));

                                    startActivity(new Intent(Student.this,student_point_at_issue.class));
                                }
                            }
                            else{
                                //User is signed out
                            }
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Student.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Please Enter email and password", Toast.LENGTH_SHORT).show();
        }
    }
    // This code hase been fixed
/**
    private void LoginEmailPasswordUser(String m_email, String m_password) {
        if(!TextUtils.isEmpty(m_email) && !TextUtils.isEmpty(m_password)){
            firebaseAuth.signInWithEmailAndPassword(m_email,m_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user!= null;
                            Toast.makeText(Student.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            final String currentUserId = user.getUid();
                            collectionReference.whereEqualTo("userid",currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error != null){
                                        Toast.makeText(Student.this, "Please enter a valid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                    assert value != null;
                                    if(!value.isEmpty()){
                                        for (QueryDocumentSnapshot snapshot:value){
                                            StudentUser studentUser = StudentUser.getInstance();
                                            studentUser.setUsername(snapshot.getString("username"));
                                            studentUser.setUserid(snapshot.getString("userid"));

                                            startActivity(new Intent(Student.this,student_point_at_issue.class));
                                        }
                                    }
                                    else{
                                        //User is signed out
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else{
            Toast.makeText(this, "Please Enter email and password", Toast.LENGTH_SHORT).show();

        }
    }
    **/


}
