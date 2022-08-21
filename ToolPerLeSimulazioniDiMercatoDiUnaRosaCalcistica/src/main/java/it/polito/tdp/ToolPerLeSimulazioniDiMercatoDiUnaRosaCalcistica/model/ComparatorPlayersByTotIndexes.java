package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model;

import java.util.Comparator;

public class ComparatorPlayersByTotIndexes implements Comparator<Footballer> {

	@Override
	public int compare(Footballer o1, Footballer o2) {
		// TODO Auto-generated method stub
		
		int tot1 = o1.getMarking()+o1.getPassing()+o1.getPositioning()+o1.getTechnique()+o1.getStrength();
		int tot2 = o2.getMarking()+o2.getPassing()+o2.getPositioning()+o2.getTechnique()+o2.getStrength();
		
		return Integer.compare(tot2, tot1);
	}

}
