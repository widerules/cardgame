package com.example.cards;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;

import com.example.cardgame.App;

public class CardSequence {
	public List<Card> sequence;
	private Context context;
	public Card curr_moving=null;
	public CardSequence(Context ctx){
		sequence = new ArrayList<Card>();
		context = ctx;
	}
	
	public CardSequence(List<Card> cards,int x, int y, Context ctx){
		context = ctx;
		sequence = new ArrayList<Card>();
		for(Card card:cards){
			add_card(card);
			card.X=x;
			card.Y=y;
			card.X1=x;
			card.Y1=y;
			y+=card.getHeight()/4;
			if(cards.indexOf(card)==cards.size()-1){
				card.visibility=Card.VISIBLE_FOCUSED;
			}else card.visibility=Card.VISIBLE_DEFAULT;
		}
	}
	
	public Boolean add_card(Card card, int position){
		if(!sequence.contains(card)&&card!=null){
			sequence.add(position, card);
			return true;
		}else return false;
	}
	
	public Boolean add_card(Card card){
		return add_card(card,sequence.size());
	}
	
	public void remove_card(Card card){
		
		if(sequence.indexOf(card)<sequence.size()-1){
			for(int i =sequence.size()-1;i>sequence.indexOf(card);i--){
				sequence.get(i).X1=sequence.get(i-1).X1;
				sequence.get(i).Y1=sequence.get(i-1).Y1;
				sequence.get(i).X=sequence.get(i).X1;
				sequence.get(i).Y=sequence.get(i).Y1;
			}
		}
		sequence.remove(card);
	}
	
	public int getHeight(){
		int height=0;
		for (Card curr_card : sequence) {
			if(curr_card.visibility==Card.VISIBLE_DEFAULT&&sequence.indexOf(curr_card)!=sequence.size()-1){
				height+=curr_card.getHeight()/4;
			}else{
				height+=curr_card.getHeight();
			}
		}
		return height;
	}
	
	public int getWidth(){
		if(sequence.size()>0){
			return sequence.get(0).getWidth();
		}else{
			return 0;
		}
	}
	
	public void display(Canvas canvas){
		for (Card curr_card : sequence) {
			if(!curr_card.is_moving){
				canvas.drawBitmap(curr_card.getBitmap(),curr_card.X,curr_card.Y,null);
			}
		}
		for (Card curr_card : sequence) {
			if(curr_card.is_moving){
				canvas.drawBitmap(curr_card.getBitmap(),curr_card.X,curr_card.Y,null);
			}
		}
		
	}
	
	public Boolean moveAtPoint(float x,float y, float dx, float dy){
		
		Boolean on_card=false;

		if(curr_moving==null){
			for (Card curr_card : sequence) {
				if(curr_card.isPointOnCard(x, y)){
					curr_card.is_moving=true;
					curr_card.X1=curr_card.X;
					curr_card.Y1=curr_card.Y;
					curr_card.X+=dx;
					curr_card.Y+=dy;
					curr_moving=curr_card;
					
					on_card=true;
					break;				
				}
			}
		}else{
			curr_moving.X+=dx;
			curr_moving.Y+=dy;
			on_card=true;
		}
		return on_card;
	}
	
	public void moveScreen(){
		for (Card curr_card : sequence) {
			float offset = ((App)context.getApplicationContext()).getyOffset();
			curr_card.Y=(int)((float)curr_card.Y+offset);
			curr_card.Y1=(int)((float)curr_card.Y1+offset);
		}
			
				
				
			
	}
	
	public void resetCards(){
		for (Card curr_card : sequence) {
			if(curr_card.is_moving){
				curr_card.is_moving=false;
				curr_card.X=curr_card.X1;
				curr_card.Y=curr_card.Y1;
			}else{
				curr_card.X1=curr_card.X;
				curr_card.Y1=curr_card.Y;
				if(sequence.indexOf(curr_card)==sequence.size()-1){
					curr_card.visibility=Card.VISIBLE_FOCUSED;
				}else curr_card.visibility=Card.VISIBLE_DEFAULT;
			}
			
		}
		curr_moving=null;
//		Log.d("RST","reset");
	}
	
	public Boolean rearrangeCard(Card card_to_move){
		Boolean on_card=false;
		
		if(card_to_move!=null && sequence.contains(card_to_move)){
			for (Card curr_card : sequence) {
				if((curr_card.isPointOnCard(card_to_move.X, card_to_move.Y)||curr_card.isPointOnCard(card_to_move.X+card_to_move.getWidth(), card_to_move.Y)) && !curr_card.equals(card_to_move)){
					int old_index = sequence.indexOf(card_to_move);
					int new_index = sequence.indexOf(curr_card);
					Card replaced_card = card_to_move;
					if(new_index<old_index){
						for (int i = old_index; i > new_index+1;i--) {
							sequence.get(i).swap(sequence.get(i-1));
							replaced_card = sequence.set(i-1,sequence.get(i));
							sequence.set(i,replaced_card);
							
						}
					}else{
						if(old_index<new_index){
							for (int i = old_index; i < new_index;i++) {
								sequence.get(i).swap(sequence.get(i+1));
								replaced_card = sequence.set(i+1,sequence.get(i));
								sequence.set(i,replaced_card);
//								card_to_move.is_moving=false;
							}
						}
					}
					on_card=true;
					break;
				}
				
			}	
		}else {
			if(card_to_move!=null ){
				for (Card curr_card : sequence) {
					if((curr_card.isPointOnCard(card_to_move.X, card_to_move.Y)||
							curr_card.isPointOnCard(card_to_move.X+card_to_move.getWidth(), card_to_move.Y)) ){
						add_card(card_to_move);
						card_to_move.X=sequence.get(sequence.size()-2).X;
						card_to_move.Y=sequence.get(sequence.size()-2).Y;
						card_to_move.Y+=card_to_move.getHeight()/4;
						card_to_move.is_moving=false;
						on_card=true;
						break;
					}
				}
			}
		}
		return on_card;
	}
}
