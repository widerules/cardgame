package com.example.drawing;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.cardgame.App;
import com.example.cardgame.MyGestureDetector;
import com.example.cardgame.R;
import com.example.cards.Card;
import com.example.cards.CardSequence;
import com.example.cards.Dealer;

public class Table extends View{
	
	private Paint p;
	private Context context;
	
	private CardSequence player_cards;
	private CardSequence discard_pile;
	
	private CardSequence deck;

	
	private float mLastTouchX;
	private float mLastTouchY;
	
	private GestureDetector mGestureDetector;

	
	public Table(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		context = ctx;
		p = new Paint();
		Resources res = getResources();
		TypedArray club_cards = res.obtainTypedArray(R.array.clubs_cards);
	
		deck = new CardSequence(context);
		for (int i = 0; i < club_cards.length(); i++) {
			Card new_card = new Card("clubs"+String.valueOf(i),club_cards.getResourceId(i,0),context);
			deck.add_card(new_card);
		}
		club_cards.recycle();
		
		TypedArray hearts_cards = res.obtainTypedArray(R.array.hearts_cards);
		
		for (int i = 0; i < hearts_cards.length(); i++) {
			Card new_card = new Card("hearts"+String.valueOf(i),hearts_cards.getResourceId(i,0),context);
			deck.add_card(new_card);
		}
		hearts_cards.recycle();
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(context, new MyGestureDetector());
		
		TypedArray diamonds_cards = res.obtainTypedArray(R.array.diamonds_cards);
		
		for (int i = 0; i < diamonds_cards.length(); i++) {
			Card new_card = new Card("diamonds"+String.valueOf(i),diamonds_cards.getResourceId(i,0),context);
			deck.add_card(new_card);
		}
		diamonds_cards.recycle();
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(context, new MyGestureDetector());
		
		TypedArray spades_cards = res.obtainTypedArray(R.array.spades_cards);
		
		for (int i = 0; i < spades_cards.length(); i++) {
			Card new_card = new Card("spades"+String.valueOf(i),spades_cards.getResourceId(i,0),context);
			deck.add_card(new_card);
		}
		spades_cards.recycle();
		
		Dealer dealer = new Dealer(4, 7, deck.sequence,ctx);
		player_cards = dealer.getPlayerCards().get(0);
		discard_pile = dealer.getDeck();
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(context, new MyGestureDetector());
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas){
		//create a black canvas
		p.setColor(Color.GRAY);
		p.setAlpha(255);
		p.setStrokeWidth(1);
		canvas.drawRect(0,0,getWidth(),getHeight(),p);
		discard_pile.display(canvas);
		player_cards.display(canvas);
		
	
	}
	
	public 	boolean onTouchEvent(MotionEvent ev) {  
		final int action = ev.getAction();    
		final float x = ev.getX();
		final float y = ev.getY();
		if(mGestureDetector.onTouchEvent(ev)) {
			mLastTouchX = x;
			mLastTouchY = y;
			invalidate();
			return true;
		}
		switch (action) {
			case MotionEvent.ACTION_DOWN: {    
				
				// Remember where we started
				
				mLastTouchX = x;
				mLastTouchY = y;
			}            
			case MotionEvent.ACTION_MOVE: {
				int numPointers = ev.getPointerCount();
				// Calculate the distance moved
				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;
				mLastTouchX = x;
				mLastTouchY = y;
				if(numPointers==1){
					if(player_cards.moveAtPoint(x,y,dx,dy)){
						invalidate();
					}
				}else if(numPointers==2){
					((App)context.getApplicationContext()).setyOffset(dy);
					player_cards.moveScreen();
					discard_pile.moveScreen();
					invalidate();
				}

				break;   
			}
			case MotionEvent.ACTION_UP: {
				if(player_cards.rearrangeCard(player_cards.curr_moving)){
					
				}else {
					if(discard_pile.rearrangeCard(player_cards.curr_moving)){
						player_cards.remove_card(player_cards.curr_moving);
					}
				}
				player_cards.resetCards();
				discard_pile.resetCards();
				
				invalidate();
				break;

			}
			case MotionEvent.ACTION_CANCEL: {
					player_cards.resetCards();
					invalidate();
			}
		}        
		return true;
	}
	
}
