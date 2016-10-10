package com.example.mymarqueeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import java.lang.ref.WeakReference;

/**
 */
public abstract class AnimView extends View {
  private static final String TAG = "AnimView";
  protected Marquee2 marquee2;

  public AnimView(Context context) {
    super(context);
    init();
  }

  public AnimView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    marquee2 = new Marquee2(this);
  }

  public abstract boolean draw(int delta);

  public void stop() {
    marquee2.stop();
  }

  public class Marquee2 {
    private final Choreographer mChoreographer;
    private int mLastAnimationMs = 0;
    private final WeakReference<AnimView> mView;

    Marquee2(AnimView animView) {
      mChoreographer = Choreographer.getInstance();
      mView = new WeakReference<AnimView>(animView);
    }

    private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback() {
      @Override public void doFrame(long frameTimeNanos) {
        tick(frameTimeNanos);
      }
    };

    void tick(long frameTimeNanos) {
      mChoreographer.removeFrameCallback(mTickCallback);
      int currentMs = (int) (frameTimeNanos / 1000000);
      Log.e(TAG, "time:" + currentMs);
      AnimView animView = mView.get();
      boolean ret = false;
      if (mLastAnimationMs == 0) {
        if (animView != null) {
          ret = animView.draw(0);
        }
      } else {
        if (animView != null) {
          ret = animView.draw(currentMs - mLastAnimationMs);
        }
      }
      if (ret) {
        mLastAnimationMs = currentMs;
        mChoreographer.postFrameCallback(mTickCallback);
      }
    }

    public void stop() {
      mChoreographer.removeFrameCallback(mTickCallback);
    }

    public void start() {
      mChoreographer.postFrameCallback(mTickCallback);
    }
  }
}
