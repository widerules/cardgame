package com.example.cards;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


public class Card extends Object{
	public String card_name;
	
	private Bitmap card_pic;
	private Context context;
	public Boolean is_moving = false;
	
	public int visibility;
		
	public static final int VISIBLE_DEFAULT=1;
	public static final int VISIBLE_FOCUSED=2;
	
	public int X;
	public int Y;
	
	public int X1;
	public int Y1;
	

	public Card(String name ,int card_resource_id, Context ctx){
		card_name = name;
		visibility = VISIBLE_DEFAULT;
		context=ctx;
		Resources res = context.getResources();
		card_pic = BitmapFactory.decodeResource(res, card_resource_id);
		Matrix matrix = new Matrix();
		matrix.preRotate(90);
		card_pic = Bitmap.createBitmap(card_pic, 0, 0, card_pic.getWidth(), card_pic.getHeight(), matrix, false);
	}
	
	public int getHeight(){
		return card_pic.getHeight();
	}
	
	public int getWidth(){
		return card_pic.getWidth();
	}
	
	public void scaleBy(float perc) { 
		int width = this.getWidth(); 
		int height = this.getHeight(); 
		Matrix matrix = new Matrix(); 
		matrix.postScale((float) (0.01*perc), (float) (0.01*perc)); 
		card_pic = Bitmap.createBitmap(card_pic, 0, 0, width, height, matrix, false); 
	}
	
	public Bitmap getBitmap(){
		return card_pic;
	}
	
	public Boolean isPointOnCard(float x, float y){
		if(is_moving){
			if(X<=x && x<X+getWidth() && Y<=y &&y<Y+getHeight()) {
				return true;
			}else return false;
		}else{
			if(visibility==VISIBLE_FOCUSED){
				if(X<=x && x<X+getWidth() && Y<=y &&y<Y+getHeight()) {
					return true;
				}else return false;
			}else{
				if(X<=x && x<X+getWidth() && Y<=y &&y<Y+getHeight()/4) {
					return true;
				}else return false;
			}
		}
	}
	
	public Boolean equals(Card card){
		return card_name==card.card_name;
	}
	
	public void swap(Card card){
		
		
		
		int tempX1=X1;
		int tempY1=Y1;
		X1=card.X1;
		Y1=card.Y1;
		card.X1=tempX1;
		card.Y1=tempY1;
		
		X=X1;
		Y=Y1;
		card.X=card.X1;
		card.Y=card.Y1;
		
//		boolean t = is_moving;
		is_moving=false;
		card.is_moving=false;
	}
}
