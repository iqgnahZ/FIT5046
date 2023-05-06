package com.example.ass3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DataEntryActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText addressEditText;
    private DatePicker datePicker;
    private Spinner genderSpinner;
    private Button saveButton;
    private Button editButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        datePicker = findViewById(R.id.datepicker);
        genderSpinner = findViewById(R.id.gender_spinner);
        saveButton = findViewById(R.id.save_button);
        editButton = findViewById(R.id.edit_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Populate the gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // Save data, implement your data storage logic here
                    Toast.makeText(DataEntryActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Edit data, implement your edit logic here
                Toast.makeText(DataEntryActivity.this, "Edit data", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields
                nameEditText.setText("");
                addressEditText.setText("");
            }
        });
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Name is required");
            isValid = false;
        }

        if (addressEditText.getText().toString().trim().isEmpty()) {
            addressEditText.setError("Address is required");
            isValid = false;
        }

        return isValid;
    }
}
