package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKy extends AppCompatActivity {
    private EditText edtemail_dk, edtPass_dk,edtConfilm;
    private Button btnDangKy_dk;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        edtemail_dk = findViewById(R.id.txtEmail_dk);
        edtPass_dk = findViewById(R.id.txtPass_dk);
        edtConfilm = findViewById(R.id.txtConfilm_dk);
        btnDangKy_dk = findViewById(R.id.btnDangKyMoi);
        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnDangKy_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail_dk.getText().toString();
                String pass = edtPass_dk.getText().toString();
                String confilm = edtConfilm.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DangKy.this, "Bạn chưa nhập email !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(DangKy.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                // pass
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(DangKy.this, "Bạn chưa nhập pass !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(DangKy.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.matches(".*[A-Z].*") || !pass.matches(".*[a-z].*")) {
                    Toast.makeText(DangKy.this, "Mật khẩu phải chứa ít nhất một chữ cái viết hoa và một chữ cái viết thường", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(confilm)) {
                    Toast.makeText(DangKy.this, "Bạn chưa nhập confilm !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(confilm)){
                    Toast.makeText(DangKy.this, "Sai pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Dang ky khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}