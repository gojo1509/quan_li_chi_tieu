
package com.example.quan_li_chi_tieu;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ExpenseDetailActivity extends AppCompatActivity {

    TextView txtChiTietTen;
    TextView txtChiTietSoTien;
    TextView txtChiTietDanhMuc;
    TextView txtChiTietNgay;
    TextView txtChiTietGhiChu;

    Button btnQuayLai;
    Button btnXoaChiTieu;

    int viTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        txtChiTietTen = findViewById(R.id.txtChiTietTen);
        txtChiTietSoTien = findViewById(R.id.txtChiTietSoTien);
        txtChiTietDanhMuc = findViewById(R.id.txtChiTietDanhMuc);
        txtChiTietNgay = findViewById(R.id.txtChiTietNgay);
        txtChiTietGhiChu = findViewById(R.id.txtChiTietGhiChu);

        btnQuayLai = findViewById(R.id.btnQuayLai);
        btnXoaChiTieu = findViewById(R.id.btnXoaChiTieu);

        Intent intent = getIntent();

        String ten = intent.getStringExtra("ten");
        long soTien = intent.getLongExtra("soTien", 0);
        String danhMuc = intent.getStringExtra("danhMuc");
        String ngay = intent.getStringExtra("ngay");
        String ghiChu = intent.getStringExtra("ghiChu");

        viTri = intent.getIntExtra("viTri", -1);

        txtChiTietTen.setText(ten);
        txtChiTietSoTien.setText(
                NumberFormat.getInstance(new Locale("vi", "VN"))
                        .format(soTien) + " đ"
        );
        txtChiTietDanhMuc.setText(danhMuc);
        txtChiTietNgay.setText(ngay);

        if (ghiChu == null || ghiChu.trim().isEmpty()) {
            txtChiTietGhiChu.setText("Không có ghi chú");
        } else {
            txtChiTietGhiChu.setText(ghiChu);
        }

        btnQuayLai.setOnClickListener(v -> finish());

        btnXoaChiTieu.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("xoa", true);
            data.putExtra("viTri", viTri);
            setResult(RESULT_OK, data);
            finish();
        });
    }
}
