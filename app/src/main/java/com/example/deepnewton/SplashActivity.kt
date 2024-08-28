package com.example.deepnewton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)

        startAnimation()
    }

    private fun startAnimation() {
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 3000
        fadeOut.fillAfter = true

        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 2000
        fadeIn.fillAfter = true

        imageView1.startAnimation(fadeOut)
        imageView2.startAnimation(fadeIn)

        Handler().postDelayed({
            // 다음 액티비티로 전환
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500) // 5초 대기
    }
}
