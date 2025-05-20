package com.example.assignment_and103_ph33001;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button SignUpButton;
    private TextView loginLinkText;
    private ImageView googleLoginButton, facebookLoginButton, appleLoginButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        emailEditText = findViewById(R.id.signup_email_edittext);
        passwordEditText = findViewById(R.id.signup_password_edittext);
        SignUpButton = findViewById(R.id.signup_create_account_button);
        loginLinkText = findViewById(R.id.signup_login_link);
        googleLoginButton = findViewById(R.id.google_login_button);
        facebookLoginButton = findViewById(R.id.facebook_login_button);
        appleLoginButton = findViewById(R.id.apple_login_button);

        mAuth = FirebaseAuth.getInstance();

        // Thêm gạch chân cho chữ "Đăng nhập"
        SpannableString content = new SpannableString(getString(R.string.login_link));
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginLinkText.setText(content);


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Sign up successfull!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), "Sign up fail: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener socialLoginClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String platform = "";
                int id = v.getId();
                if (id == R.id.google_login_button) {
                    platform = "Google";
                } else if (id == R.id.facebook_login_button) {
                    platform = "Facebook";
                } else if (id == R.id.apple_login_button) {
                    platform = "Apple";
                }
                Toast.makeText(SignUpActivity.this, "Đăng nhập bằng " + platform, Toast.LENGTH_SHORT).show();
                // Xử lý logic đăng nhập bằng social media ở đây
            }
        };

        googleLoginButton.setOnClickListener(socialLoginClickListener);
        facebookLoginButton.setOnClickListener(socialLoginClickListener);
        appleLoginButton.setOnClickListener(socialLoginClickListener);
    }
}
