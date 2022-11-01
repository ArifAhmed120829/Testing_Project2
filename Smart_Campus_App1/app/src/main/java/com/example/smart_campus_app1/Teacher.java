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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
public class Teacher extends AppCompatActivity {
    //attributes
    private Button signup,login;
    private EditText email_et, password_et;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("teachers");


    //method
    protected void onCreate(Bundle savedinst) {

        super.onCreate(savedinst);
        setContentView(R.layout.teacher_layout);
        signup = findViewById(R.id.teacher_layout_Signup_btn);
        login = findViewById(R.id.teacher_layout_Login_btn);
        email_et = findViewById(R.id.teacher_layout_Email_et);
        password_et = findViewById(R.id.teacher_layout_Password_et);
        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Teacher.this,Teacher_Signup.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailPasswordUser(email_et.getText().toString().trim(), password_et.getText().toString().trim());

            }
        });


    }
    private void LoginEmailPasswordUser(String m_email, String m_password) {
        if(!TextUtils.isEmpty(m_email) && !TextUtils.isEmpty(m_password)){
            firebaseAuth.signInWithEmailAndPassword(m_email,m_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user!= null;
                    final String currentUserId = user.getUid();

                    collectionReference.whereEqualTo("userid",currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Toast.makeText(Teacher.this, "Please enter a valid email or password", Toast.LENGTH_SHORT).show();
                            }
                            assert value != null;
                            if(!value.isEmpty()){
                                Toast.makeText(Teacher.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                for (QueryDocumentSnapshot snapshot:value){
                                    StudentUser studentUser = StudentUser.getInstance();
                                    studentUser.setUsername(snapshot.getString("username"));
                                    studentUser.setUserid(snapshot.getString("userid"));

                                    startActivity(new Intent(Teacher.this,list_of_issues.class));
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
                    Toast.makeText(Teacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Please Enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

}
