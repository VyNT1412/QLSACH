package com.example.qlsach.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qlsach.DTO.DatabaseHelper;
import com.example.qlsach.Model.User;
import com.example.qlsach.R;

public class RegisterActivity extends AppCompatActivity {
    EditText inputNgaySinh, inputHoten, inputUsername, inputMK, inputConfMK, inputEmail, inputSDT, inputDiaChi;
    private int mYear, mMonth, mDay;
    Button btnDangKy, buttonPickDate;
    RadioGroup gioiTinh;
    RadioButton gtNam, gtNu;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    String ht, sdt, email, mk, diachi, un, ns, conf_mk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputNgaySinh = findViewById(R.id.inPutNgaySinhKH);
        inputHoten = findViewById(R.id.inputHoTenUser);
        inputUsername = findViewById(R.id.editUsername_DK);
        inputMK = findViewById(R.id.editMK_DK);
        inputConfMK = findViewById(R.id.editConf_MK);
        inputEmail = findViewById(R.id.inPutEmailKH);
        inputSDT = findViewById(R.id.inPutPhoneKH);
        inputDiaChi = findViewById(R.id.inPutDiaChiKH);

        buttonPickDate = findViewById(R.id.buttonPickDate);
        btnDangKy = findViewById(R.id.buttonRegister);

        gioiTinh = findViewById(R.id.GtUser);
        gtNam = findViewById(R.id.radioButtonNam);
        gtNu = findViewById(R.id.radioButtonNu);
        // Lấy ngày hiện tại để thiết lập cho EditText
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);


        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
//        User user = new User(1, "username", "123456", "email@example.com", "John Doe",
//                "1234567890", "123 Street, City", "2000-01-01", 1, 1);
//        User user1 = new User( 2, "username12", "123456", "email@example.com", "John Doe",
//                "1234567890", "123 Street, City", "2000-01-01", 2, 0);
//        dbHelper.addUser(user);
//        dbHelper.addUser(user1);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = gioiTinh.getCheckedRadioButtonId();
                int dem = dbHelper.getLastUserId();
                Log.d("", Long.toString(dem));
                getData();
                if (ht.isEmpty()) {
                    inputHoten.setError("Vui lòng nhập họ tên");
                    return;
                }
                if (un.isEmpty()) {
                    inputUsername.setError("Vui lòng nhập tên đăng nhập");
                    return;
                }
                if (mk.isEmpty()) {
                    inputMK.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                if (conf_mk.isEmpty()) {
                    inputConfMK.setError("Vui lòng nhập lại mật khẩu");
                    return;
                }
                if (email.isEmpty()) {
                    inputEmail.setError("Vui lòng nhập email");
                    return;
                }
                if (sdt.isEmpty()) {
                    inputSDT.setError("Vui lòng nhập số điện thoại");
                    return;
                }
                if (sdt.length()>10 || sdt.length() < 1)
                {
                    inputSDT.setError("So dien thoai khong chinh xac");
                    return;
                }
                if(!email.contains("@"))
                {
                    inputEmail.setError("Vui lòng nhập dung dinh dang email");
                    return;
                }
                if (ns.isEmpty()) {
                    inputNgaySinh.setError("Vui lòng nhập ngày sinh");
                    return;
                }
                if (diachi.isEmpty()) {
                    inputDiaChi.setError("Vui lòng nhập địa chỉ");
                    return;
                }

                // Tiếp tục xử lý khi tất cả các trường được điền đầy đủ
                // Kiểm tra mật khẩu có khớp không
                if (!mk.equals(conf_mk)) {
                    inputConfMK.setError("Mật khẩu xác nhận không khớp");
                    return;
                }
                if (id == -1) {
                    Toast.makeText(RegisterActivity.this, "Vui long chon gioi tinh", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHelper.checkUsername_Phone_Email(un, sdt, email)) {
                    Toast.makeText(RegisterActivity.this, "ten user hoac sdt hoac email da ton tai", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    User u;
                    if (id == gtNam.getId()) {
                        ht = chuyenTenSv(ht);
                        Log.d("",ht);
                        u = new User(dem + 1, un, mk, email, ht, sdt, diachi, ns, 1, 2);
                        Log.d("", Integer.toString(dem + 1));
                        dbHelper.addUser(u);
                        Toast.makeText(RegisterActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else if (id == gtNu.getId()) {
                        u = new User(dem + 1, un, mk, email, ht, sdt, diachi, ns, 0, 2);
                        dbHelper.addUser(u);
                        Toast.makeText(RegisterActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }

                }

            }
        });
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog và thiết lập ngày ban đầu là ngày hiện tại
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Cập nhật EditText với ngày đã chọn
                        inputNgaySinh.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void getData() {
        ht = inputHoten.getText().toString();
        sdt = inputSDT.getText().toString();
        mk = inputMK.getText().toString();
        diachi = inputDiaChi.getText().toString();
        email = inputEmail.getText().toString();
        un = inputUsername.getText().toString();
        conf_mk = inputConfMK.getText().toString();
        ns = inputNgaySinh.getText().toString();
    }
    public String chuyenTenSv(String hoTen) {
        StringBuilder result = new StringBuilder();

        if (hoTen != null && !hoTen.isEmpty()) {
            String[] parts = hoTen.split("\\s+");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    // Chuyển tất cả chữ cái thành chữ thường
                    String lowercasePart = part.toLowerCase();
                    // Chuyển chữ cái đầu của mỗi từ thành chữ hoa và thêm vào kết quả
                    String formattedPart = Character.toUpperCase(lowercasePart.charAt(0)) + lowercasePart.substring(1);
                    result.append(formattedPart).append(" ");
                }
            }
            // Loại bỏ dấu space cuối cùng nếu có
            if (result.length() > 0) {
                result.setLength(result.length() - 1);
            }
        }
        return result.toString();
    }

}