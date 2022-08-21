package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model;

import java.util.Comparator;

public class ComparatorPlayersByPos implements Comparator<Footballer> {

	@Override
	public int compare(Footballer o1, Footballer o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o2.getPositioning(), o1.getPositioning());
	}

}
