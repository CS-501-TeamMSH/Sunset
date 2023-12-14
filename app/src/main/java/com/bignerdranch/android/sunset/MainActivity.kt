package com.bignerdranch.android.sunset

import android.animation.Animator.DURATION_INFINITE
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bignerdranch.android.sunset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isSunriseRunning = false

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scene.setOnClickListener {

            if (isSunriseRunning) {
                sunrise()
            } else {
                sunset()
            }
        }
    }





    private fun sunset() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(binding.sun, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(binding.sky, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(binding.sky, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        // Pulsing Sun
        val scalex = ObjectAnimator.ofFloat(binding.sun, "scaleX", 1.0f, 1.2f)
        val scaley = ObjectAnimator.ofFloat(binding.sun, "scaleY", 1.0f, 1.2f)
        scalex.repeatMode = ObjectAnimator.REVERSE
        scalex.repeatCount = 10
        scaley.repeatMode = ObjectAnimator.REVERSE
        scaley.repeatCount = 10

        // Animation Set 1
        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(scalex)
            .with(scaley)
            .with(sunsetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()

        isSunriseRunning = true
    }

    fun sunrise() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimatorReverse = ObjectAnimator
            .ofFloat(binding.sun, "y", sunYEnd, sunYStart)
            .setDuration(3000)
        heightAnimatorReverse.interpolator = AccelerateInterpolator()

        val sunriseSkyAnimator = ObjectAnimator
            .ofInt(binding.sky, "backgroundColor", nightSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunriseSkyAnimator.setEvaluator(ArgbEvaluator())

        val daylightSkyAnimator = ObjectAnimator
            .ofInt(binding.sky, "backgroundColor", sunsetSkyColor, blueSkyColor)
            .setDuration(1500)
        daylightSkyAnimator.setEvaluator(ArgbEvaluator())


        // Pulsing Sun
        val scalex = ObjectAnimator.ofFloat(binding.sun, "scaleX", 1.0f, 1.2f)
        val scaley = ObjectAnimator.ofFloat(binding.sun, "scaleY", 1.0f, 1.2f)
        scalex.repeatMode = ObjectAnimator.REVERSE
        scalex.repeatCount = 10
        scaley.repeatMode = ObjectAnimator.REVERSE
        scaley.repeatCount = 10

        // Animation Set 2
        val animatorSetReverse = AnimatorSet()
        animatorSetReverse.play(heightAnimatorReverse)
            .with(scalex)
            .with(scaley)
            .with(sunriseSkyAnimator)
            .before(daylightSkyAnimator)
        animatorSetReverse.start()

        isSunriseRunning = false
    }


}
