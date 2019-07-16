package com.devarthur.buttonprogress

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create progress drawable
        val progressDrawable = CircularProgressDrawable(this).apply {
            // let's use large style just to better see one issue
            setStyle(CircularProgressDrawable.LARGE)
            setColorSchemeColors(Color.WHITE)

            //bounds definition is required to show drawable correctly
            val size = (centerRadius + strokeWidth).toInt() * 2
            setBounds(0, 0, size, size)
        }

        // create a drawable span using our progress drawable
        val drawableSpan = object : DynamicDrawableSpan() {
            override fun getDrawable() = progressDrawable
        }
        var marginStart = 20
        val drawableSpanCustom = CustomDrawableSpan(progressDrawable, marginStart)

        // create a SpannableString like "Loading [our_progress_bar]"
        val spannableString = SpannableString("Loading ").apply {
            setSpan(drawableSpanCustom, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        //start progress drawable animation
        progressDrawable.start()


        val callback = object : Drawable.Callback {
            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            }

            override fun invalidateDrawable(who: Drawable) {
                button.invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            }
        }
        progressDrawable.callback = callback

        button.text = spannableString

        button.setOnClickListener {
            progressDrawable.stop()
        }
    }


}
