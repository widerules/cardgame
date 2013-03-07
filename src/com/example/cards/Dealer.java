package com.example.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;

public class Dealer {
	private List<CardSequence> playerCards;
	private CardSequence deck;
		
	public Dealer(int playerCount, int cardCountPerPlayer, List<Card> cards, Context context){
		Collections.shuffle(cards, new Random());
		
		playerCards = new ArrayList<CardSequence>(playerCount);
		for(int j=0;j<playerCount;j++){
			
			List<Card> card_set = new ArrayList<Card>(cardCountPerPlayer);
			for(int i=0;i<cardCountPerPlayer;i++){
				card_set.add(0,cards.remove(0));
//				player.add_card(cards.remove(0));
			}
			CardSequence player = new CardSequence(card_set,0,50,context);
			playerCards.add(0,player);
		}
//		Log.d("aaa", "msg");
		deck = new CardSequence(cards,275,50,context);
		
	}
	
	public List<CardSequence> getPlayerCards(){
		return playerCards;
	}
	
	public CardSequence getDeck(){
		return deck;
	}
	
	
}
