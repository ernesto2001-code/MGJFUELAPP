package com.example.mgj;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encuentra el ImageView
        ImageView logo = findViewById(R.id.logo);

        // Carga la animación combinada
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);

        // Inicia la animación
        logo.startAnimation(splashAnimation);

        // Redirige al LoginActivity al terminar la animación
        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Redirige al LoginActivity



                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish(); // Finaliza el splash screen
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}