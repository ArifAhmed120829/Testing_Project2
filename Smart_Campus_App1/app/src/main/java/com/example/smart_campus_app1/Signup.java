package com.example.smart_campus_app1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import util.StudentUser;

//class
public class Signup extends AppCompatActivity {
    //Attributes
    private Button sign_up ;
    private EditText email ;
    private EditText password;
    private EditText username ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore firestore_database = FirebaseFirestore.getInstance();
    private CollectionReference firestore_collectionreference = firestore_database.collection("StudentUsers");
    //Methods
    protected void onCreate(Bundle savedInst) {
        super.onCreate(savedInst);
        setContentView(R.layout.signup_layout);
        sign_up = findViewById(R.id.signup_layout_Signup_btn);
        email = findViewById(R.id.signup_layout_Email_et);
        password = findViewById(R.id.signup_layout_Password_et);
        username = findViewById(R.id.signup_layout_Username_et);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();
                if(currentuser!= null){
                    //User already logged in
                }
                else{

                }

            }
        };
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())
                        && !TextUtils.isEmpty(username.getText().toString())){
                    String email_s = email.getText().toString().trim();
                    String password_s = password.getText().toString().trim();
                    String username_s = username.getText().toString().trim();
                    Createuseremailaccount(email_s,password_s,username_s);
                    Toast.makeText(Signup.this, "Thank you for registering", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Signup.this, "Email,Username or Password can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Createuseremailaccount(String email_s, String password_s,final String username_s) {
        if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())
                && !TextUtils.isEmpty(username.getText().toString())){
            firebaseAuth.createUserWithEmailAndPassword(email_s,password_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//EMAIL,PASSWORD
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //We take user to the next page
                        currentuser = firebaseAuth.getCurrentUser();
                        assert currentuser!=null;
                        final String currentuserid = currentuser.getUid();
                        HashMap<String,String> userobj = new HashMap<>();
                        userobj.put("userid",currentuserid);// ID
                        userobj.put("username",username_s); //NAME

                        //Adding users to Firestore
                        firestore_collectionreference.add(userobj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(Objects.requireNonNull(task.getResult()).exists()){
                                            String name = task.getResult().getString("username");//document
                                            // if the user is registered succesfully
                                            //then move to student_point_at_issue

                                            //Getting use of global StudentUser
                                            StudentUser studentUser = StudentUser.getInstance();
                                            studentUser.setUserid(currentuserid);
                                            studentUser.setUsername(name);

                                            Intent i = new Intent(Signup.this,Student.class);
                                            i.putExtra("username",name);//field
                                            i.putExtra("userid",currentuserid);//field
                                            startActivity(i);
                                        }
                                        else{

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Signup.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                    }
                }
            });

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentuser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
