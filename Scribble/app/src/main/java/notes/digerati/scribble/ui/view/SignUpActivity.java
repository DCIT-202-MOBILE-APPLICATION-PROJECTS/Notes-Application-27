package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import notes.digerati.scribble.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    private FirebaseAuth auth;

    private ProgressBar bar;

    String email = "";
    String password = "";
    String confirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener( view -> validateData());

        // configure progressbar
        bar = new ProgressBar(this);
        bar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
    }

    private void validateData(){

        // get data
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();
        confirmPassword = binding.etPasswordConfirm.getText().toString().trim();

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            binding.etEmail.setError("Invalid email format");
        } else if (TextUtils.isEmpty(password)) {
            // password isn't entered
            binding.etPassword.setError("Please enter your password");
        } else if (password.length() < 6) {
            binding.etPassword.setError("Please enter at least 6 characters long");
        } else if (TextUtils.isEmpty(confirmPassword)) {
            binding.etPasswordConfirm.setError("Please confirm your password");
        } else if (!Objects.equals(password, confirmPassword)) {
            Toast.makeText(this,"Passwords must be the same", Toast.LENGTH_LONG).show();
        } else firebaseSignUp();
    }

    private void firebaseSignUp(){

        //show progress
        bar.setVisibility(View.VISIBLE);
//        if (binding.etEmail.getText().equals(email)
//                && binding.etPassword.getText().equals(password)
//                && binding.etPasswordConfirm.getText().equals(password)) {
//            startActivity(new Intent(this, WorkSpaceActivity.class));
//        } else {
//            Toast.makeText(this,"Sign Up unsuccessful", Toast.LENGTH_LONG).show();
//        }

        // create account
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> {

                    // dismiss progress
                    bar.setVisibility(View.GONE);

                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;
                    String email = user.getEmail();
                    Toast.makeText(this, "Account created with " + email, Toast.LENGTH_LONG).show();

                    // open profile
                    startActivity(new Intent(this, AddScreenActivity.class));
                    finish();

                })
                .addOnFailureListener( e-> {

                    //signup failed
                    bar.setVisibility(View.GONE);
                    Toast.makeText(this,"Sign up failed due to " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // go back to previous activity, when back button of actionbar clicked
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}