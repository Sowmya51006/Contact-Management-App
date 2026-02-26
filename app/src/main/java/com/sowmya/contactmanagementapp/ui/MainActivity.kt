package com.sowmya.contactmanagementapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sowmya.contactmanagementapp.R
import com.sowmya.contactmanagementapp.databinding.ActivityMainBinding
import com.sowmya.contactmanagementapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}
