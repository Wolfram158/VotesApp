package ru.wolfram.votes_app.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import kotlin.random.Random
import kotlin.random.nextInt

internal class CircularDiagram @JvmOverloads constructor(
    context: Context,
    private val counts: List<Float> = listOf()
) : View(context) {
    private val paints = mutableListOf<Paint>()
    private val total = counts.sum()
    private val rect = RectF()

    private fun randomByte() = Random.nextInt(0..255)
    private fun randomColour() = Color.rgb(randomByte(), randomByte(), randomByte())

    init {
        counts.forEach { _ ->
            paints.add(Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = randomColour()
            })
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val padding = 20f
        val size = minOf(w, h) - 2 * padding
        val centerX = w / 2f
        val centerY = h / 2f
        val radius = size / 2

        rect.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startAngle = -90f

        counts.forEachIndexed { i, count ->
            val sweepAngle = 360 * count / total

            canvas.drawArc(
                rect,
                startAngle,
                sweepAngle,
                true,
                paints[i]
            )

            startAngle += sweepAngle
        }

    }
}