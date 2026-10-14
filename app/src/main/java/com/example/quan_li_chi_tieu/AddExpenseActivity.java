
package com.example.quan_li_chi_tieu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    EditText edtTenKhoanChi, edtSoTien, edtGhiChu;
    Spinner spnDanhMuc;
    Button btnChonNgay, btnLuuChiTieu;

    String ngayChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTenKhoanChi = findViewById(R.id.edtTenKhoanChi);
        edtSoTien = findViewById(R.id.edtSoTien);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        spnDanhMuc = findViewById(R.id.spnDanhMuc);
        btnChonNgay = findViewById(R.id.btnChonNgay);
        btnLuuChiTieu = findViewById(R.id.btnLuuChiTieu);

        String[] danhMuc = {
                "Ăn uống",
                "Đi lại",
                "Học tập",
                "Giải trí",
                "Khác"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                danhMuc
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnDanhMuc.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        capNhatNgay(calendar);

        btnChonNgay.setOnClickListener(v -> {
            Calendar hienTai = Calendar.getInstance();

            DatePickerDialog datePicker = new DatePickerDialog(
                    AddExpenseActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar ngayDuocChon = Calendar.getInstance();
                        ngayDuocChon.set(year, month, dayOfMonth);
                        capNhatNgay(ngayDuocChon);
                    },
                    hienTai.get(Calendar.YEAR),
                    hienTai.get(Calendar.MONTH),
                    hienTai.get(Calendar.DAY_OF_MONTH)
            );

            datePicker.show();
        });

        btnLuuChiTieu.setOnClickListener(v -> luuChiTieu());
    }

    private void capNhatNgay(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        ngayChi = sdf.format(calendar.getTime());
        btnChonNgay.setText(ngayChi);
    }

    private void luuChiTieu() {
        String ten = edtTenKhoanChi.getText().toString().trim();
        String soTienText = edtSoTien.getText().toString().trim();
        String danhMuc = spnDanhMuc.getSelectedItem().toString();
        String ghiChu = edtGhiChu.getText().toString().trim();

        if (ten.isEmpty()) {
            edtTenKhoanChi.setError("Vui lòng nhập tên khoản chi");
            edtTenKhoanChi.requestFocus();
            return;
        }

        if (soTienText.isEmpty()) {
            edtSoTien.setError("Vui lòng nhập số tiền");
            edtSoTien.requestFocus();
            return;
        }

        long soTien;

        try {
            soTien = Long.parseLong(soTienText);
        } catch (NumberFormatException e) {
            edtSoTien.setError("Số tiền không hợp lệ");
            edtSoTien.requestFocus();
            return;
        }

        if (soTien <= 0) {
            edtSoTien.setError("Số tiền phải lớn hơn 0");
            edtSoTien.requestFocus();
            return;
        }

        Intent data = new Intent();
        data.putExtra("ten", ten);
        data.putExtra("soTien", soTien);
        data.putExtra("danhMuc", danhMuc);
        data.putExtra("ngay", ngayChi);
        data.putExtra("ghiChu", ghiChu);

        setResult(RESULT_OK, data);
        finish();
    }
}
