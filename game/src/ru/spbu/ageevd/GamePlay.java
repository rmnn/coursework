package ru.spbu.ageevd;

import java.util.Random;

import android.util.Log;

public class GamePlay {
	private static final String[] mAnswers = { "Moscow", "New-york",
			"Budapesht", "Sydney", "Venetia", "Boston", "Dallas", "HongKong",
			"Las-Vegas", "London", "Los-Angeles", "Miami", "Moscow",
			"New-York", "Paris", "Rio de Janeiro", "Sandiego", "Sydney",
			"Manchester", };
	private static final int[] mPathsToImages = { R.drawable.moscow,
			R.drawable.newyork, R.drawable.budapesht, R.drawable.sydney,
			R.drawable.venus, R.drawable.boston, R.drawable.dallas,
			R.drawable.hongkong1, R.drawable.lasvegas, R.drawable.london1,
			R.drawable.losangeles, R.drawable.miami, R.drawable.moscow1,
			R.drawable.newyork1, R.drawable.paris, R.drawable.riodejaneiro,
			R.drawable.sandiego, R.drawable.sydney1 };
	private static Random random = new Random();
	private int round;
	private int numberOfTrueAnswer;

	public GamePlay() {
	}

	public void generateNextRound() {
		round = random.nextInt(50000) % mPathsToImages.length;
	}

	public String[] getVariants() {
		String[] var = new String[4];
		int a = 0;
		for (int i = 0; i <= 3; i++) {
			a = random.nextInt(5000) % mAnswers.length;
			if (a == round) {
				a = (a + 1) % mAnswers.length;
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
