package com.example.assignment_and103_ph33001;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordText, createAccountLinkText;
    private Button signInButton;
    private ImageView googleLoginButton, facebookLoginButton, appleLoginButton;
    private FirebaseAuth mAuth;
    String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Ẩn ActionBar nếu bạn đang sử dụng Theme có ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        emailEditText = findViewById(R.id.login_email_edittext);
        passwordEditText = findViewById(R.id.login_password_edittext);
        rememberMeCheckBox = findViewById(R.id.login_remember_me_checkbox);
        forgotPasswordText = findViewById(R.id.login_forgot_password_text);
        signInButton = findViewById(R.id.login_signin_button);
        createAccountLinkText = findViewById(R.id.login_create_account_link);
        googleLoginButton = findViewById(R.id.login_google_button);
        facebookLoginButton = findViewById(R.id.login_facebook_button);
        appleLoginButton = findViewById(R.id.login_apple_button);
        mAuth = FirebaseAuth.getInstance();

        // Thêm gạch chân cho chữ "Tạo tài khoản mới"
        SpannableString content = new SpannableString(getString(R.string.create_new_account_link));
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        createAccountLinkText.setText(content);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                email = emailEditText.getText().toString().trim();
//                password = passwordEditText.getText().toString().trim();
//                if (email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mAuth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Login successfull!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class); // Giả sử bạn có HomeActivity
                                    startActivity(intent);
//                                } else {
//                                    String errorMessage = task.getException().getMessage();
//                                    Toast.makeText(getApplicationContext(), "Login fail: " + errorMessage, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });

        createAccountLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString().trim();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Please check your mail.", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "Recover password fail" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        View.OnClickListener socialLoginClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String platform = "";
                int id = v.getId();
                if (id == R.id.login_google_button) {
                    platform = "Google";
                } else if (id == R.id.login_facebook_button) {
                    platform = "Facebook";
                } else if (id == R.id.login_apple_button) {
                    platform = "Apple";
                }
                Toast.makeText(LoginActivity.this, "Đăng nhập bằng " + platform, Toast.LENGTH_SHORT).show();
                // Xử lý logic đăng nhập bằng social media ở đây
            }
        };

        googleLoginButton.setOnClickListener(socialLoginClickListener);
        facebookLoginButton.setOnClickListener(socialLoginClickListener);
        appleLoginButton.setOnClickListener(socialLoginClickListener);
    }
}