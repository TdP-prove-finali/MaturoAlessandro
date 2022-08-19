package it.polito.tdp.FootballManager.model;

import java.util.Comparator;

public class ComparatorPlayersByTec implements Comparator<Footballer> {

	@Override
	public int compare(Footballer o1, Footballer o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o2.getTechnique(), o1.getTechnique());
	}

}
