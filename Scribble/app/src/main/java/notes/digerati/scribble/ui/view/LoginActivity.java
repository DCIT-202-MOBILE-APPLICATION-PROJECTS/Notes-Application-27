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

import notes.digerati.scribble.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private ProgressBar bar;

    private FirebaseAuth auth;

    private String email = "";

    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // configure progressbar
        bar = new ProgressBar(this);
        bar.setVisibility(View.INVISIBLE);

        // init FirebaseAuth
        auth = FirebaseAuth.getInstance();
        checkUser();

        // handle click, open SignUpActivity
        binding.tvAccount.setOnClickListener( view -> {
            startActivity( new Intent(this, SignUpActivity.class));
        });

        // handle click , begin login
        binding.btnSignUp.setOnClickListener( view -> {

            // before logging in, validate data
            validateData();
        });
    }

    private void validateData() {

        // get data
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            // invalid email format
            binding.etEmail.setError("Invalid email format");
        } else if (TextUtils.isEmpty(password)){

            // no password entered
            binding.etPassword.setError("no password entered");
        } else {

            // data is validated, begin login
            firebaseLogin();
        }
    }

    private void firebaseLogin() {

        // show progress
        bar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener( task -> {

                    // login successful
                    bar.setVisibility(View.GONE);

                    // get user info
                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;
                    String email = user.getEmail();
                    Toast.makeText(this,"logged in as " + email,
                            Toast.LENGTH_LONG).show();

                    // open profile
                    Intent intent = new Intent(this, AddScreenActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener( e -> {

                    // login failed
                    bar.setVisibility(View.GONE);
                    Toast.makeText(this,"Login failed due to " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }

    private void checkUser() {

        // if user is already logged in go to profile activity
        // get current user
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {

            // user is already logged in
            startActivity(new Intent(this, AddScreenActivity.class));
            finish();
        }
    }
}