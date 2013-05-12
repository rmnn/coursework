package ru.spbu.ageevd;

import java.util.Random;

import android.util.Log;

public class GamePlay {
	private static final String[] mAnswers = {"Moscow", "New-york", "Budapesht", "Sydney", "Venetia", "Paris", "Manchester", "Petrozavodsk", "Peterhof"};
	private static final int[] mPathsToImages = {R.drawable.moscow, R.drawable.newyork, R.drawable.budapesht, R.drawable.sydney, R.drawable.venus };
	private static Random random = new Random();
	private int round;
	private int numberOfTrueAnswer;
	
	public GamePlay() {	
	}
	
	public void generateNextRound() {
		round = random.nextInt(50000) % 5;	
	}
	
	public String[] getVariants() {
		String[] var = new String[4];
		int a = 0;
		for (int i = 0; i <= 3; i++) {
			a = random.nextInt(5000) % 9;
			if (a == round) {
				a = (a + 1) % 9;
			}
			var[i] = mAnswers[a];
		}
		numberOfTrueAnswer = random.nextInt(5000) % 4;
		var[numberOfTrueAnswer] = mAnswers[round];
		return var;
	}
	
	public int getPathToImage() {
		return mPathsToImages[round];
	}
	
	public int getAnswer() {
		return numberOfTrueAnswer;
	}
}
