package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.weatherapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(),Animation.AnimationListener {
    private lateinit var binding : ActivitySplashBinding
    private lateinit var animFadeIn : Animation
    private fun loadanim(){
        animFadeIn=AnimationUtils.loadAnimation(this,R.anim.fade_in)
        animFadeIn.duration=2000
        animFadeIn.setAnimationListener(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadanim()
        binding.imageView.startAnimation(animFadeIn)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    override fun onAnimationStart(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {

    }

    override fun onAnimationRepeat(animation: Animation?) {

    }
}