package com.example.thongtintaikhoan.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;

import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ChangePassWordWithMailActivity extends AppCompatActivity {
    private SQLiteOpenHelper hospitalDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    int code;
    private boolean isValid;

    String PTO;
    Button btn_ChangePassMails,getBtn_ChangePassMails2,btn_ChangePassMail3;
    EditText tv_inputEmail_ChangePassWords,tv_inputCode_ChangePassWords,
            tv_inputPassword_ChangePassWord,tv_inputAgainPassword_ChangePassWord;
    ImageView btn_back_forgotpassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tv_inputEmail_ChangePassWords = findViewById(R.id.tv_inputEmail_ChangePassWord);
        tv_inputCode_ChangePassWords = findViewById(R.id.tv_inputCode_ChangePassWord);
        tv_inputPassword_ChangePassWord = findViewById(R.id.tv_inputPassword_ChangePassWord);
        tv_inputAgainPassword_ChangePassWord = findViewById(R.id.tv_inputAgainPassword_ChangePassWord);
        PTO = String.valueOf(OTP_Generator(1000,9999));

        findViewById(R.id.box1).setVisibility(View.VISIBLE);
        findViewById(R.id.box3).setVisibility(View.GONE);
        btn_ChangePassMails = findViewById(R.id.btn_ChangePassMail);
        btn_ChangePassMails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidEmailInput();
                if(isValid){
                    checkMailwithOTP();
                    findViewById(R.id.box1).setVisibility(View.GONE);
                    findViewById(R.id.box2).setVisibility(View.VISIBLE);
                }
            }
        });
        btn_back_forgotpassword = findViewById(R.id.btn_back_forgotpassword);
        btn_back_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getBtn_ChangePassMails2 = findViewById(R.id.btn_ChangePassMail2);
        getBtn_ChangePassMails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               isValid = isValidOTP();
               if(isValid){
                   checkCode(view);
               }

            }
        });
        btn_ChangePassMail3 = findViewById(R.id.btn_ChangePassMail3);
        btn_ChangePassMail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = isValidUserInput();
                if(isValid){
                    ChangePassWordFromEmail();
                }
            }
        });

    }
    int OTP_Generator(int min_value, int max_value){
        int result = 0;
        Random Rn = new Random();
        do{
            result = Rn.nextInt(max_value);
        }while (result <= min_value);
        return result;
    }
  public void checkMailwithOTP(){
      try {
          String stringSenderEmail = "tonytqt99@gmail.com";
          String stringReceiverEmail = tv_inputEmail_ChangePassWords.getText().toString();
          String stringPasswordSenderEmail = "jzxzlffokxytbjsq";

          String stringHost = "smtp.gmail.com";

          Properties properties = System.getProperties();

          properties.put("mail.smtp.host", stringHost);
          properties.put("mail.smtp.port", "465");
          properties.put("mail.smtp.ssl.enable", "true");
          properties.put("mail.smtp.auth", "true");

          javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
              }
          });

          MimeMessage mimeMessage = new MimeMessage(session);
          mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

          mimeMessage.setSubject("Send OTP From 2TAPQ");
          mimeMessage.setText("chào "+ tv_inputEmail_ChangePassWords +", \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World \n OTP = "+ PTO);

          Thread thread = new Thread(new Runnable() {
              @Override
              public void run() {
                  try {
                      Transport.send(mimeMessage);
                  } catch (MessagingException e) {
                      e.printStackTrace();
                  }
              }
          });
          thread.start();

      } catch (AddressException e) {
          e.printStackTrace();
      } catch (MessagingException e) {
          e.printStackTrace();
      }
  }
        public void checkCode(View view){
        String inputCode;
        inputCode= tv_inputCode_ChangePassWords.getText().toString();
        if(inputCode.equals(String.valueOf(PTO))){
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            findViewById(R.id.box3).setVisibility(View.VISIBLE);
            findViewById(R.id.box1).setVisibility(View.GONE);
            findViewById(R.id.box2).setVisibility(View.GONE);
        }else{
            Toast.makeText(this,"Vui lòng nhập OTP",Toast.LENGTH_SHORT).show();
        }
    }



    public void ChangePassWordFromEmail(){
        hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
        db = hospitalDatabaseHelper.getWritableDatabase();
        isValid = isValidUserInput();
        if(isValid){
            String newPasswordss = getMd5Hash(tv_inputAgainPassword_ChangePassWord.getText().toString());
            DatabaseHelper.updatePasswordFromEmail(db,newPasswordss,
                    tv_inputEmail_ChangePassWords.getText().toString());

            changePasswordDialog().show();
        }


    }
    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }

    public boolean isValidUserInput() {
        if(HelperUtilities.isEmptyOrNull(tv_inputPassword_ChangePassWord.getText().toString())){
            tv_inputPassword_ChangePassWord.setError("Vui lòng nhập mật khẩu");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(tv_inputAgainPassword_ChangePassWord.getText().toString())){
            tv_inputAgainPassword_ChangePassWord.setError("Vui lòng nhập lại mật khẩu");
            return false;
        }else if(!tv_inputPassword_ChangePassWord.getText().toString().equals(tv_inputAgainPassword_ChangePassWord.getText().toString())){
            tv_inputAgainPassword_ChangePassWord.setError("Mật Khẩu không giống nhau");
            return false;
        }
        return true;

    }
    public boolean isValidEmailInput() {

        if(HelperUtilities.isEmptyOrNull(tv_inputEmail_ChangePassWords.getText().toString())){
            tv_inputEmail_ChangePassWords.setError("Vui lòng nhập Email");
            return false;
        }else if(!HelperUtilities.isValidEmail(tv_inputEmail_ChangePassWords.getText().toString())){
            tv_inputEmail_ChangePassWords.setError("Vui lòng nhập đúng định dạng Email");
            return false;
        }
        return true;

    }
    public boolean isValidOTP(){
        if(HelperUtilities.isEmptyOrNull(tv_inputCode_ChangePassWords.getText().toString())){
            tv_inputCode_ChangePassWords.setError("Vui lòng nhập OTP");
            return false;
        }else if(!tv_inputCode_ChangePassWords.getText().toString().equals(PTO)){
            tv_inputCode_ChangePassWords.setError("Vui lòng nhập đúng OTP");
            return false;
        }
        return true;
    }
    public Dialog changePasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Mật khẩu đã thay đổi thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        return builder.create();
    }


}
