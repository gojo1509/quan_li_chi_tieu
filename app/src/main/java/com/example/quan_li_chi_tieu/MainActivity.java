 package com.example.quan_li_chi_tieu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtTongChiTieu;
    private Button btnThemChiTieu;
    private LinearLayout layoutDanhSachChiTieu;

    private final ArrayList<ChiTieu> danhSachChiTieu = new ArrayList<>();

    private final NumberFormat dinhDangTien =
            NumberFormat.getInstance(new Locale("vi", "VN"));

    private static final int REQUEST_THEM_CHI_TIEU = 100;
    private static final int REQUEST_CHI_TIET = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTongChiTieu = findViewById(R.id.txtTongChiTieu);
        btnThemChiTieu = findViewById(R.id.btnThemChiTieu);
        layoutDanhSachChiTieu = findViewById(R.id.layoutDanhSachChiTieu);

        khoiTaoDuLieuMau();
        hienThiDanhSach();

        btnThemChiTieu.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddExpenseActivity.class
            );

            startActivityForResult(intent, REQUEST_THEM_CHI_TIEU);
        });
    }

    private void khoiTaoDuLieuMau() {
        danhSachChiTieu.clear();

        danhSachChiTieu.add(
                new ChiTieu("Ăn sáng", 30000, "Ăn uống", "14/10/2026", "")
        );


        danhSachChiTieu.add(
                new ChiTieu("Mua sách", 120000, "Học tập", "14/10/2026", "")
        );
    }

    private void hienThiDanhSach() {
        layoutDanhSachChiTieu.removeAllViews();

        long tongTien = 0;

        for (int i = 0; i < danhSachChiTieu.size(); i++) {
            ChiTieu chiTieu = danhSachChiTieu.get(i);
            tongTien += chiTieu.soTien;

            LinearLayout theChiTieu = new LinearLayout(this);
            theChiTieu.setOrientation(LinearLayout.VERTICAL);
            theChiTieu.setPadding(16, 14, 16, 14);
            theChiTieu.setBackgroundColor(Color.WHITE);

            LinearLayout.LayoutParams thamSoThe =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            thamSoThe.setMargins(0, 0, 0, 12);
            theChiTieu.setLayoutParams(thamSoThe);

            TextView txtTen = new TextView(this);
            txtTen.setText(chiTieu.ten);
            txtTen.setTextSize(18);
            txtTen.setTextColor(Color.BLACK);
            txtTen.setTypeface(null, Typeface.BOLD);

            TextView txtSoTien = new TextView(this);
            txtSoTien.setText(dinhDangTien.format(chiTieu.soTien) + " đ");
            txtSoTien.setTextSize(20);
            txtSoTien.setTextColor(Color.rgb(25, 118, 210));
            txtSoTien.setTypeface(null, Typeface.BOLD);

            TextView txtThongTin = new TextView(this);
            txtThongTin.setText(
                    "Danh mục: " + chiTieu.danhMuc
                            + "\nNgày chi: " + chiTieu.ngay
            );
            txtThongTin.setTextSize(14);
            txtThongTin.setTextColor(Color.DKGRAY);

            theChiTieu.addView(txtTen);
            theChiTieu.addView(txtSoTien);
            theChiTieu.addView(txtThongTin);

            theChiTieu.setOnClickListener(v -> {
                int viTri = danhSachChiTieu.indexOf(chiTieu);

                if (viTri == -1) {
                    return;
                }

                Intent intent = new Intent(
                        MainActivity.this,
                        ExpenseDetailActivity.class
                );

                intent.putExtra("ten", chiTieu.ten);
                intent.putExtra("soTien", chiTieu.soTien);
                intent.putExtra("danhMuc", chiTieu.danhMuc);
                intent.putExtra("ngay", chiTieu.ngay);
                intent.putExtra("ghiChu", chiTieu.ghiChu);
                intent.putExtra("viTri", viTri);

                startActivityForResult(intent, REQUEST_CHI_TIET);
            });

            layoutDanhSachChiTieu.addView(theChiTieu);
        }

        txtTongChiTieu.setText(dinhDangTien.format(tongTien) + " đ");
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == REQUEST_THEM_CHI_TIEU) {
            String ten = data.getStringExtra("ten");
            long soTien = data.getLongExtra("soTien", 0);
            String danhMuc = data.getStringExtra("danhMuc");
            String ngay = data.getStringExtra("ngay");
            String ghiChu = data.getStringExtra("ghiChu");

            if (ten != null
                    && danhMuc != null
                    && ngay != null
                    && soTien > 0) {

                ChiTieu chiTieuMoi = new ChiTieu(
                        ten,
                        soTien,
                        danhMuc,
                        ngay,
                        ghiChu == null ? "" : ghiChu
                );

                danhSachChiTieu.add(chiTieuMoi);
                hienThiDanhSach();

                Toast.makeText(
                        MainActivity.this,
                        "Đã thêm khoản chi thành công",
                        Toast.LENGTH_SHORT
                ).show();
            }
        } else if (requestCode == REQUEST_CHI_TIET) {
            boolean xoa = data.getBooleanExtra("xoa", false);

            if (xoa) {
                int viTri = data.getIntExtra("viTri", -1);

                if (viTri >= 0 && viTri < danhSachChiTieu.size()) {
                    danhSachChiTieu.remove(viTri);
                    hienThiDanhSach();

                    Toast.makeText(
                            MainActivity.this,
                            "Đã xóa khoản chi",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }
    }

    static class ChiTieu {
        String ten;
        long soTien;
        String danhMuc;
        String ngay;
        String ghiChu;

        ChiTieu(
                String ten,
                long soTien,
                String danhMuc,
                String ngay,
                String ghiChu
        ) {
            this.ten = ten;
            this.soTien = soTien;
            this.danhMuc = danhMuc;
            this.ngay = ngay;
            this.ghiChu = ghiChu;
        }
    }
}
