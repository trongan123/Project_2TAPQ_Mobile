package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.R;

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

public class CheckMailOTPActivity extends AppCompatActivity {
    Button btn_CheckMail_OTPs,btn_CheckMail2_OTPs;
    EditText tv_inputCode_Mail,tv_inputEmail_checkMail;
    ImageView btn_back_CheckMailOTPs;
    String PTO;
    String mail;
    private boolean isValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmail_otp);

        btn_CheckMail_OTPs =findViewById(R.id.btn_CheckMail_OTP);
        btn_CheckMail2_OTPs = findViewById(R.id.btn_CheckMail2_OTP);
        tv_inputCode_Mail = findViewById(R.id.tv_inputCode_Mail);
        tv_inputEmail_checkMail = findViewById(R.id.tv_inputEmail_checkMail);
        PTO = String.valueOf(OTP_Generator(1000,9999));

        findViewById(R.id.box1).setVisibility(View.VISIBLE);
        findViewById(R.id.box2).setVisibility(View.GONE);

        btn_CheckMail_OTPs = findViewById(R.id.btn_CheckMail_OTP);
        btn_CheckMail_OTPs.setOnClickListener(new View.OnClickListener() {
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
        btn_CheckMail2_OTPs = findViewById(R.id.btn_CheckMail2_OTP);
        btn_CheckMail2_OTPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidOTP();
                if(isValid){
                    checkCode(view);
                }

            }
        });
        btn_back_CheckMailOTPs = findViewById(R.id.btn_back_CheckMailOTP);
        btn_back_CheckMailOTPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntroAcitvity.class);
                startActivity(intent);
                finish();
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
    public void checkCode(View view){
        String inputCode;
        inputCode= tv_inputCode_Mail.getText().toString();
        mail = tv_inputEmail_checkMail.getText().toString();
        if(inputCode.equals(String.valueOf(PTO))){
            Toast.makeText(this, "Nhập mã thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            intent.putExtra("MAIL_CHECKOTP",mail);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Vui lòng nhập OTP",Toast.LENGTH_SHORT).show();
        }
    }
    public void checkMailwithOTP(){
        try {
            String stringSenderEmail = "tonytqt99@gmail.com";
            String stringReceiverEmail = tv_inputEmail_checkMail.getText().toString();
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
            mimeMessage.setText("chào Programmer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World \n OTP = "+ PTO);

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
    public boolean isValidEmailInput() {

        if(HelperUtilities.isEmptyOrNull(tv_inputEmail_checkMail.getText().toString())){
            tv_inputEmail_checkMail.setError("Vui lòng nhập Email");
            return false;
        }else if(!HelperUtilities.isValidEmail(tv_inputEmail_checkMail.getText().toString())){
            tv_inputEmail_checkMail.setError("Vui lòng nhập đúng định dạng Email");
            return false;
        }
        return true;

    }
    public boolean isValidOTP(){
        if(HelperUtilities.isEmptyOrNull(tv_inputCode_Mail.getText().toString())){
            tv_inputCode_Mail.setError("Vui lòng nhập OTP");
            return false;
        }else if(!tv_inputCode_Mail.getText().toString().equals(PTO)){
            tv_inputCode_Mail.setError("Vui lòng nhập đúng OTP");
            return false;
        }
        return true;
    }
}