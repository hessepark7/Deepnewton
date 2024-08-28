package com.example.deepnewton

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deepnewton.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 인스턴스 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewBinding을 사용하여 BottomNavigationView에 접근
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Home 화면으로 이동
                    true
                }
                R.id.nav_search -> {
                    // Search 화면으로 이동
                    true
                }
                R.id.nav_notifications -> {
                    // Notifications 화면으로 이동
                    true
                }
                R.id.nav_profile -> {
                    // Profile 화면으로 이동
                    true
                }
                else -> false
            }
        }
    }
}
