package com.devarthur.buttonprogress

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan


/**
 * Created by Arthur Gomes at 2019-07-16
 * Contact : arthur.gomes_4718@hotmail.com
 * Copyright : SportsMatch 2019
 * A custom span for a progress button.
 */

class CustomDrawableSpan(drawable: Drawable, verticalAlignment: Int) : ImageSpan(drawable, verticalAlignment) {

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        // get drawable dimensions
        val rect = drawable.bounds
        fm?.let {
            val fontMetrics = paint.fontMetricsInt

            // get a font height
            val lineHeight = fontMetrics.bottom - fontMetrics.top

            //make sure our drawable has height >= font height
            val drHeight = Math.max(lineHeight, rect.bottom - rect.top)

            val centerY = fontMetrics.top + lineHeight / 2

            //adjust font metrics to fit our drawable size
            fm.apply {
                ascent = centerY - drHeight / 2
                descent = centerY + drHeight / 2
                top = ascent
                bottom = descent
            }
        }

        //return drawable width which is in our case = drawable width + margin from text
        return rect.width() + verticalAlignment
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        canvas.save()
        val fontMetrics = paint.fontMetricsInt
        // get font height. in our case now it's drawable height
        val lineHeight = fontMetrics.bottom - fontMetrics.top

        // adjust canvas vertically to draw drawable on text vertical center
        val centerY = y + fontMetrics.bottom - lineHeight / 2
        val transY = centerY - drawable.bounds.height() / 2

        // adjust canvas horizontally to draw drawable with defined margin from text
        canvas.translate(x + verticalAlignment, transY.toFloat())

        drawable.draw(canvas)

        canvas.restore()
    }
}