package com.example.mymarqueeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 */
public class MarqueeTextView extends AnimView {

  private static final String TAG = "MarqueeTextView";
  private static final int MARQUEE_DP_PER_SECOND = 200;
  private CharSequence text;
  private float textSize = getResources().getDisplayMetrics().scaledDensity * 60.0f;
  private TextPaint textPaint;
  private int textColor = Color.BLACK;
  private Rect textBounds;
  private int topOffset;
  private int xOffset = 0;
  private boolean marqueeEnabled = true;
  private static float mPixelsPerSecond;

  private void init2() {
    final float density = getContext().getResources().getDisplayMetrics().density;
    mPixelsPerSecond = MARQUEE_DP_PER_SECOND * density;
    textBounds = new Rect();
  }

  public MarqueeTextView(Context context) {
    super(context);
    init2();
  }

  public MarqueeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init2();
  }

  public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init2();
  }

  @Override public boolean draw(int delta) {
    if (delta == 0) {
      xOffset = 0;
      invalidate();
      return true;
    } else {
      if (marqueeEnabled) {
        xOffset = (int) (xOffset - mPixelsPerSecond * delta / 1000);
        invalidate();
        return true;
      } else {
        stop();
        return false;
      }
    }
  }

  public void setText(CharSequence text) {
    this.text = text;
    renewPaint();
  }

  @Override public void stop() {
    super.stop();
    xOffset = 0;
    invalidate();
  }

  private int widthSize;
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
    widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
    int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

    int width;
    int height;
    if (widthMode == View.MeasureSpec.EXACTLY) {
      width = widthSize;
    } else {
      width = this.getWidth();
    }
    if (heightMode == View.MeasureSpec.EXACTLY) {
      height = heightSize;
    } else {
      TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
      paint.density = getResources().getDisplayMetrics().density;
      paint.setTextSize(textSize);
      height = (int) (Math.abs(paint.ascent()) + Math.abs(paint.descent()));
    }
    setMeasuredDimension(width, height);
    renewPaint();
  }

  public void setTextColor(int textColor) {
    this.textColor = textColor;
    renewPaint();
  }

  void renewPaint() {
    if (text != null) {
      textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
      textPaint.setDither(true);
      textPaint.setAntiAlias(true);
      textPaint.density = getResources().getDisplayMetrics().density;
      textPaint.setTextSize(textSize);
      textPaint.setColor(textColor);
      textPaint.getTextBounds(text.toString(), 0, text.length(), textBounds);

      int viewheight = getMeasuredHeight();
      topOffset = (int) (viewheight / 2 - ((textPaint.descent() + textPaint.ascent()) / 2));
      if (marqueeEnabled) {
        marquee2.start();
      } else {
        xOffset = 0;
        invalidate();
      }
    }
  }

  public void setTextSize(float textSize) {
    this.textSize = getResources().getDisplayMetrics().scaledDensity * textSize;
    renewPaint();
  }

  public boolean isMarqueeEnabled() {
    return marqueeEnabled;
  }

  public void setMarqueeEnabled(boolean marqueeEnabled) {
    this.marqueeEnabled = marqueeEnabled;
    if (marqueeEnabled) {
      marquee2.start();
    } else {
      xOffset = 0;
      invalidate();
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Log.d(TAG, " xOffset = " + xOffset + " ; topOffset = " + topOffset);
    if (text != null) {
      float viewWidth = this.getWidth();
      int textWidth = textBounds.width();
      if (textWidth < viewWidth) {
        float leftMargin = (viewWidth - textWidth) / 2;
        canvas.drawText(text.toString(), leftMargin, topOffset, textPaint);
        stop();
      } else if (!marqueeEnabled) {
        canvas.drawText(text.toString(), 0, topOffset, textPaint);
      } else {
        if (Math.abs(xOffset) >= textWidth) {
          xOffset = widthSize;
          //setMarqueeEnabled(false);
        } else {
          canvas.drawText(text.toString(), xOffset, topOffset, textPaint);
        }
      }
    }
  }
}
