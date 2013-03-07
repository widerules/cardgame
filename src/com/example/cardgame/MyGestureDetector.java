package com.example.cardgame;

//import android.util.Log;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureDetector extends SimpleOnGestureListener {
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return super.onDoubleTap(e);
	}
 
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
//		return super.onDown(e);
	}
 
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		super.onLongPress(e);
//		Log.e("aaa", "Longpress detected");
	}
 
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return super.onFling(e1, e2, velocityX, velocityY);
	}
 
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

}
