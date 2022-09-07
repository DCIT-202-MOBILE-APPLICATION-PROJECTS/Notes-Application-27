package notes.digerati.scribble.ui.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;


import notes.digerati.scribble.R;
import notes.digerati.scribble.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(binding.getRoot());

        binding.welcomeBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
    });

    }
}