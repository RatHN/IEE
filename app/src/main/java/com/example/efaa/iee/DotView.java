package com.example.efaa.iee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Neri Ortez on 28/02/2017.
 */

public class DotView extends View {
    private int mColor;
    private Paint mPaint;


    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DotView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mColor);
        int i = getHeight() / 2;
        canvas.drawCircle(i, i, i,mPaint);
    }

    public void setColor(int mColor) {
        if(mColor == this.mColor){
            return;
        }
        this.mColor = mColor;
        invalidate();
    }
}
