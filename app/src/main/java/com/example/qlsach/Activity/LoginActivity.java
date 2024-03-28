package com.example.qlsach.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qlsach.DTO.DatabaseHelper;
import com.example.qlsach.Model.User;
import com.example.qlsach.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonSignUp;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        editTextUsername = findViewById(R.id.editTextUsernameLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

//        User user = new User(3, "username", "123456", "email@example.com", "John Doe",
//                "1234567890", "123 Street, City", "2000-01-01", 1, 1);
//        User user1 = new User(4, "username12", "123456", "email@example.com", "John Doe",
//                "1234567890", "123 Street, City", "2000-01-01", 2, 0);
//        dbHelper.addUser(user);
//        dbHelper.addUser(user1);

        //
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if(checkInput(username,password))
                {
                  // String name =  chinhChuoi(username);
                    if (dbHelper.checkUserKH(username, password)) {
                        // Đăng nhập thành công
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        int dem = dbHelper.countUser();
                        Log.d("",Integer.toString(dem));
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại, vui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                // Kiểm tra đăng nhập

            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean checkInput(String username, String pass)
    {
        if(username.equals("")||pass.equals(""))
        {

            return false;
        }
        return true;
    }


}