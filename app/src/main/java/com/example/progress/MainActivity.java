package com.example.progress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Contact, Emessage;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.Send);
        Contact = findViewById(R.id.Contact);
        Emessage = findViewById(R.id.EMessage);
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(i, 1);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager smg = SmsManager.getDefault();

                    smg.sendTextMessage(Contact.getText().toString(), null, Emessage.getText().toString(), null, null);

                    Toast.makeText(MainActivity.this, "Message Send", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("Message", e.toString());
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri contactData = data.getData();

                    Cursor cursor = managedQuery(contactData, null, null, null, null);

                    cursor.moveToFirst();

                    String number = "Contact Numbere";
                    int xz = cursor.getColumnIndex("data1");

                    number = cursor.getString(xz);

                    Contact.setText(number);
                } catch (Exception e) {
                    Contact.setText(e.toString());
                }
            }
        }
    }
}