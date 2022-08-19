package it.polito.tdp.FootballManager.model;

import java.util.Comparator;

public class ComparatorPlayersByPas implements Comparator<Footballer> {

	@Override
	public int compare(Footballer o1, Footballer o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o1.getPassing(), o2.getPassing());
	}

	
}
