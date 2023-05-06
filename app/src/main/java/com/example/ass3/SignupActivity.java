package com.example.ass3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;

    private EditText nameEditText;

    private EditText addressEditText;

    private DatePicker datePicker;

    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signupButton);
        nameEditText = findViewById(R.id.name);
        addressEditText = findViewById(R.id.address);
        datePicker = findViewById(R.id.date_picker);
        genderSpinner = findViewById(R.id.gender_spinner);

        // Populate the gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (validateInputs(email, password, confirmPassword)) {
            // Proceed with Firebase Authentication for registration
            // createUserWithEmailAndPassword(email, password);
        }
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            confirmPasswordEditText.setError("Passwords do not match.");
            valid = false;
        } else {
            confirmPasswordEditText.setError(null);
        }

        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Name is required");
            valid = false;
        }

        if (addressEditText.getText().toString().trim().isEmpty()) {
            addressEditText.setError("Address is required");
            valid = false;
        }
        return valid;
    }
}

// Example method for Firebase Authentication
/*
private void createUserWithEmailAndPassword(String email, String password) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success, update UI with the signed-in user's information
                    // Navigate back to LoginActivity or directly to HomeActivity
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(SignupActivity.this, "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
}
*/

