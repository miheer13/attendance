package com.miheer.gmeetnoti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherNewNoticeActivity extends AppCompatActivity {

    private Button btnSendNotice;
    private EditText editText;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_new_notice);

        editText = findViewById(R.id.editText);
        btnSendNotice = findViewById(R.id.btnSendNotice);

//        reference = FirebaseDatabase.getInstance().getReference().child("notices");

        btnSendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNotice = editText.getText().toString();

                if (txtNotice.isEmpty()) {
                    Toast.makeText(TeacherNewNoticeActivity.this, "PLEASE ENTER THE NOTICE", Toast.LENGTH_SHORT).show();
                } else {
//                    FirebaseDatabase.getInstance().getReference().child("notice no").setValue(FirebaseDatabase.getInstance().getReference().child("notice no").v);
                    FirebaseDatabase.getInstance().getReference().child("notices").push().setValue(txtNotice);
                    editText.setText("");
                    Toast.makeText(TeacherNewNoticeActivity.this, "Notice has been sent to the students", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}