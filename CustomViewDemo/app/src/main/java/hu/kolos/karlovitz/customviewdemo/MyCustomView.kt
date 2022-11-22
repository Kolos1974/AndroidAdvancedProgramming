package hu.kolos.karlovitz.customviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button

//class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
// Button-ból való leszármazás
class MyCustomView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatButton(
    context, attrs) {

    // private  var paintBg: Paint = Paint()
    private  var paintText: Paint = Paint()

    private var click = 0

    init {
        // paintBg.setColor(Color.BLUE)
        // paintBg.style = Paint.Style.FILL

        paintText.setColor(Color.RED)
        paintText.color=Color.RED

        paintText.style = Paint.Style.FILL_AND_STROKE
        paintText.textSize = 60f

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CountColor,
            0, 0
        )

        var color = Color.RED
        try {
            color = attr.getColor(R.styleable.CountColor_countcolor, Color.RED)
            paintText.color = color
        } finally {
            // release the TypedArray so that it can be reused.
            attr.recycle()
        }

    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // canvas?.drawRect(0f,0f,width.toFloat(), height.toFloat(), paintBg)
        canvas?.drawText("$click", width-60f, 70f, paintText)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action== MotionEvent.ACTION_DOWN){
            click++
            // Újrarajzolja a gombot.
            invalidate()
        }

        return super.onTouchEvent(event)
    }


}