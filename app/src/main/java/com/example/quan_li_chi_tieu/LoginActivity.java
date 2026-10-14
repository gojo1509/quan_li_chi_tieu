package com.example.quan_li_chi_tieu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtTenDangNhap, edtMatKhau;
    TextView txtThongBaoLoi;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        txtThongBaoLoi = findViewById(R.id.txtThongBaoLoi);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        btnDangNhap.setOnClickListener(v -> xuLyDangNhap());
    }

    private void xuLyDangNhap() {
        String tenDangNhap = edtTenDangNhap.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString();

        if (tenDangNhap.isEmpty() || matKhau.trim().isEmpty()) {
            txtThongBaoLoi.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
            txtThongBaoLoi.setVisibility(View.VISIBLE);
            return;
        }

        if (tenDangNhap.equals("admin") && matKhau.equals("123456")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            txtThongBaoLoi.setText("Sai tên đăng nhập hoặc mật khẩu");
            txtThongBaoLoi.setVisibility(View.VISIBLE);
        }
    }
}
