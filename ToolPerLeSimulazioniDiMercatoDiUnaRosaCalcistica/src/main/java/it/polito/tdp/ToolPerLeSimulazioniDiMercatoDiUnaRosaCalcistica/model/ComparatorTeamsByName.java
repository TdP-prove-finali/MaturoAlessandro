package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model;

import java.util.Comparator;

public class ComparatorTeamsByName implements Comparator<Club> {

	@Override
	public int compare(Club club1, Club club2) {
		// TODO Auto-generated method stub
		return club1.getName().compareTo(club2.getName());
	}

}
