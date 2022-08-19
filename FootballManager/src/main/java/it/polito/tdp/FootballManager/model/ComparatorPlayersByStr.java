package it.polito.tdp.FootballManager.model;

import java.util.Comparator;

public class ComparatorPlayersByStr implements Comparator<Footballer> {

	@Override
	public int compare(Footballer o1, Footballer o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o1.getStrength(), o2.getStrength());
	}

}
