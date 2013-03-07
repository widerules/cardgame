package com.example.cardgame;

import android.app.Application;

public class App extends Application {

	  private float yOffset=0;

	  public float getyOffset(){
	    return yOffset;
	  }
	  
	  public void setyOffset(float s){
		  yOffset = s;
	  }
	}

