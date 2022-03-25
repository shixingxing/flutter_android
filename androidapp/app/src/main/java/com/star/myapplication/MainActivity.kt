package com.star.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.star.myapplication.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.preCachedFlutter.setOnClickListener {
            startActivity(
                FlutterActivity
                    .withCachedEngine("flutter_engine")
                    .build(this)
            )
        }

        binding.routerFlutter.setOnClickListener {
            var routerName = binding.routerName.text.toString()
            if (routerName.isEmpty()) {
                routerName = "home"
            }
            startActivity(
                FlutterActivity
                    .withNewEngine()
                    .initialRoute(routerName)
                    .build(this)
            )
        }
    }

}