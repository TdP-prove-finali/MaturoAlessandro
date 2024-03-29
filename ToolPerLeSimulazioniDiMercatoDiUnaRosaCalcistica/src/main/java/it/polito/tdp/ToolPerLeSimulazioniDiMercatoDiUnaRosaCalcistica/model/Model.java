package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.db.ClubDAO;
import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.db.FootballerDAO;

public class Model {
	
	/* Attributo per la squadra selezionata dall'utente */
	private Club selectedTeam;
	
	/* Oggetti del DAO */
	private FootballerDAO footballerDAO;
	private ClubDAO clubDAO;
	
	/* Attributi utili */
	private List<Club> clubs;

	/* Attributi per la parte ricorsiva*/
	private List<Footballer> best;
	private List<Footballer> possible;
	private int totBest;
	private boolean ok;
	
	/* Attributi utili per la parte ricorsiva */
	private int numP;
	private int numD;
	private int numC;
	private int numA;
	private int sizeSelected;
	
	
	
	
	public Model() {
		/* Inizializzo i DAO*/
		this.footballerDAO = new FootballerDAO();
		this.clubDAO = new ClubDAO();
	}
	
	
	
	
	/**
	 * Mi serve in HomeController quando devo caricare i club una
	 * volta selezionato il campionato
	 * @param championship
	 * @return lista dei club di quel campionato
	 */	
	public List<Club> getAllClubsObjectByChampionship(String championship){
		
		List<Club> result = clubDAO.getAllClubsObjectByChampionship(championship);
		
		Collections.sort(result, new ComparatorTeamsByName());
		
		return result;
	}
	
	
	
	
	/**
	 * Mi serve per caricare la tabella dei giocatori
	 * In più ogni volta che lo carico imposto la lista dei giocatori anche nel club
	 * @param club
	 * @return lista dei calciatori di quel club
	 */
	public List<Footballer> getFootballerByTeam(Club club) {

		club.setFootballers(clubDAO.getFootballerByTeam(club));
		
		return this.clubDAO.getFootballerByTeam(club);
	}
	
	
	
	
	/* Metodi per statistiche medie team */
	
	public double totValue(Club club) {
		return this.clubDAO.totValue(club);
	}
	
	public double averageAge(Club club) {
		return this.clubDAO.averageAge(club);		
	}
	
	public int averageWage(Club club) {
		return (int)this.clubDAO.averageWage(club);
	}
	
	public double averageTec(Club club) {
		return this.clubDAO.averageTec(club);
	}
	
	public double averagePas(Club club) {
		return this.clubDAO.averagePas(club);
	}
	
	public double averageMar(Club club) {
		return this.clubDAO.averageMar(club);
	}
	
	public double averagePos(Club club) {
		return this.clubDAO.averagePos(club);
	}
	
	public double averageStr(Club club) {
		return this.clubDAO.averageStr(club);
	}
	
	
	
	
	/**
	 * Mi serve per mettere il testo nella labelStats di MarketController 
	 * @param team
	 * @return Stringa con tutti i valori
	 */
	public String averageStatistics(Club club) {
		
		this.setNumberPlayersPerRole(club.getFootballers());
			
		String res = "Età media: "+this.averageAge(club)+"\n\n"+
					 "Valore totale: "+this.totValue(club)+" M€\n\n"+
					/* "Tecnica: "+this.averageTec(club)+"\n\n"+
					 "Passaggio: "+this.averagePas(club)+"\n\n"+
					 "Marcamento: "+this.averageMar(club)+"\n\n"+
					 "Posizionamento: "+this.averagePos(club)+"\n\n"+
					 "Forza: "+this.averageStr(club)+"\n\n";*/
					 "Stipendio medio: "+this.averageWage(club)+" (€/week)\n\n"+
					 "Numero portieri: "+this.numP+"\n\n"+
					 "Numero difensori: "+this.numD+"\n\n"+
					 "Numero centrocampisti: "+this.numC+"\n\n"+
					 "Numero attaccanti: "+this.numA;
		 
		return res;		
	}
	
	
	
	
	/* Metodi per caricare le varie comboBox */
	
	public List<String> getAllChampionships(){
		
		List<String> result = new LinkedList<>();
		
		result.add("Spain (First Division)");
		result.add("Italy (Serie A)");
		result.add("France (Ligue 1 Conforama)");
		result.add("Germany (Bundesliga)");
		result.add("England (Premier Division)");
		
		return result;
		
	}
	
	public List<String> getAllIndexes() {
		
		List<String> result = new LinkedList<>();
		
		result.add("Techinique");
		result.add("Marking");
		result.add("Passing");
		result.add("Strenght");
		result.add("Positioning");
		
		return result;
	}
	
	public List<String> getAllRoles() {
		
		List<String> result = new LinkedList<>();
		
		result.add("P");
		result.add("D");
		result.add("C");
		result.add("A");
		
		return result;
	}
	
	
	
	
	/**
	 *  Metodo utilizzato nella pagina BuyController.java
	 * @param index indice che si vuole migliorare
	 * @param role ruolo in cui si cerca il giocatore
	 * @param club club per cui si cerca il giocatore
	 * @param maxWage stipendio massimo che si vorrebbe dare al giocatore
	 * @param maxValueMln valore massimo in MLN di euro che si vorrebbe pagare il giocatore
	 * @return la lista dei 5 migliori calciatori in quel ruolo e che migliorano
	 *  	quell'indice ordinati dal migliore al peggiore
	 */
	
	public List<Footballer> getFootballersMaxSalaryAndMaxValueAndBetterIndex(String index, String role, Club club, double maxWage, double maxValueMln) {
		
		double mean = 0.0;
		
		double meanTeam = this.getMediumStatsByClub(club);
		
		switch(index) {
			case "Technique":
				mean = this.getMediumTechniqueByClub(club);
				break;
			case "Marking":
				this.getMediumMarkingByClub(club);
				break;
			case "Passing":
				this.getMediumPassingByClub(club);
				break;
			case "Strenght":
				this.getMediumStrenghtByClub(club);
				break;
			case "Positioning":
				this.getMediumPositioningByClub(club);
				break;	
		}		
		
		double maxValue = maxValueMln*1000000;
		
		List<Footballer> result = footballerDAO.getFootballersMaxSalaryAndMaxValueAndBetterIndex(index, role, club, mean, maxWage, maxValue, meanTeam);
		
		/* Adesso ordino il risultato per l'indice che si desiderava migliorare*/ 	
		
		switch(index) {
		case "Technique":
			Collections.sort(result, new ComparatorPlayersByTec());
			break;
		case "Marking":
			Collections.sort(result, new ComparatorPlayersByMar());
			break;
		case "Passing":
			Collections.sort(result, new ComparatorPlayersByPas());
			break;
		case "Strenght":
			Collections.sort(result, new ComparatorPlayersByStr());
			break;
		case "Positioning":
			Collections.sort(result, new ComparatorPlayersByPos());
			break;	
	}	
		
		/*Una volta riordinati prendo solo i migliori 5 */
		
		List<Footballer> daReturnare = new LinkedList<>();
		
		for(Footballer fi: result) {
			daReturnare.add(fi);
			if(daReturnare.size()>=5) {
				break;
			}
		}
		
		return daReturnare;
	}
	
	/* Metodi per la parte ricorsiva della pagina MarketController.java */
	
	public List<Footballer> init(List<Footballer> selected) {
				
		/* ogni volta che si entra puliamo tutte le liste */
		possible = new LinkedList<>();
		best = new LinkedList<>(); 
		List<Footballer> partial = new LinkedList<>(); 
		
		this.ok=true;
		
		for(Footballer fi: selected) {
			this.getPossibleFootballers(fi);
		}
		
		/* se il giocatore non avrà calciatori possibili usciremo
		 * returnando null e MarketController.java lo comunicherà all'utente */
		if(this.ok==false) {
			return null;
		}
		
		this.numP=0;
		this.numD=0;
		this.numC=0;
		this.numA=0;	
		
		/* carico il numero di giocatori selezionati per ciascun ruolo */
		this.caricaRuoli(selected);

		/* Imposto come totale indici della soluzione migliore il totale degli indici dei giocatori selezionati */
		this.totBest=this.totIndexes(selected);
		int tot=0;		
		
		this.sizeSelected=selected.size();
		
		recursion(partial, tot);
				
		return best;
	}



	/* Metodo ricorsivo */
	
	private void recursion(List<Footballer> partial, int tot) {

		
		if(partial.size()==this.sizeSelected) {
			if(tot>this.totBest) {
				if(checkRuoli(partial)) {
					this.best = new LinkedList<>(partial);
					this.totBest=tot;
				}
			}
			
		} else {
			
			for(Footballer fi: this.possible) {
				if(!partial.contains(fi)) {
					
					partial.add(fi);
					
					int contribute = fi.getMarking()+fi.getPassing()+fi.getPositioning()+fi.getStrength()+fi.getTechnique();
					tot=tot+contribute;
					
					recursion(partial, tot);
					
					tot=tot-contribute;
					partial.remove(partial.size()-1);
					
				}
				
			}
			
		}
		
	}



	/*Dovranno avere lo stesso ruolo, <= salario, <= valore, Media indici maggiore*/
	
	private void getPossibleFootballers(Footballer footballer) {
		
		/* media degli indici del calciatore passato */
		double meanIndexes = this.averageStats(footballer);
				
		List<Footballer> result = footballerDAO.getPossibleFootballers(footballer, meanIndexes);
		
		if(result!=null) {
			for(Footballer fi: result) {
				/* Lo aggiungo solo se non lo contiene già perchè lo stesso calciatore potrebbe andare
				 * bene per più di uno dei giocatori selezionati dall'utente */
				if(!this.possible.contains(fi)) {
					this.possible.add(fi);
				}
			}
		} else {
			/* se non ci sono calciatori possibili imposto ok a false */
			this.ok=false;
		}
		
	}



	/* Metodi che mi servono per la parte della ricorsione */
	
	private double averageStats(Footballer footballer) {
		
		double tot = 0.0;
		double mean = 0.0;
		
		tot = footballer.getMarking()+footballer.getPassing()+footballer.getPositioning()+footballer.getStrength()+footballer.getTechnique();
	
		mean = tot/5.0;
		
		return mean;
	}
	
	/* Se poi vuoi ragionare con medie ricordati di cambiare tutto con double */
	
	private int totIndexes(List<Footballer> selected) {
		
		int tot = 0;
		
		for(Footballer fi: selected) {
			tot += fi.getMarking()+fi.getPassing()+fi.getPositioning()+fi.getStrength()+fi.getTechnique();
		}
			
		return tot;
	}
	
	
	private void caricaRuoli(List<Footballer> selected) {
				
		for(Footballer fi: selected) {
			
			if(fi.getBest_pos().compareTo("P")==0) {
				this.numP++;
			}
			if(fi.getBest_pos().compareTo("D")==0) {
				this.numD++;
			}
			if(fi.getBest_pos().compareTo("C")==0) {
				this.numC++;
			}
			if(fi.getBest_pos().compareTo("A")==0) {
				this.numA++;
			}
		}
				
	}
	
	private void setNumberPlayersPerRole(List<Footballer> selected) {
		
		this.numP=0;
		this.numD=0;
		this.numC=0;
		this.numA=0;
		
		for(Footballer fi: selected) {			
			if(fi.getBest_pos().compareTo("P")==0) {
				this.numP++;
			}
			if(fi.getBest_pos().compareTo("D")==0) {
				this.numD++;
			}
			if(fi.getBest_pos().compareTo("C")==0) {
				this.numC++;
			}
			if(fi.getBest_pos().compareTo("A")==0) {
				this.numA++;
			}
		}
				

	}

	private boolean checkRuoli(List<Footballer> partial) {

		int cntP=0;
		int cntD=0;
		int cntC=0;
		int cntA=0;
		
		for(Footballer fi: partial) {
			if(fi.getBest_pos().compareTo("P")==0) {
				cntP++;
			}
			if(fi.getBest_pos().compareTo("D")==0) {
				cntD++;
			}
			if(fi.getBest_pos().compareTo("C")==0) {
				cntC++;
			}
			if(fi.getBest_pos().compareTo("A")==0) {
				cntA++;
			}
		}
		
		if(cntP!=this.numP) {
			return false;
		}
		if(cntD!=this.numD) {
			return false;
		}
		if(cntC!=this.numC) {
			return false;
		}
		if(cntA!=this.numA) {
			return false;
		}
		
		return true;
	}

	/* Metodi per la parte importante */
	
	public double calculateTotValue(List<Footballer> footballers) {
		
		double totValue = 0.0;
		
		for(Footballer fi: footballers) {
			totValue+=fi.getValue();
		}
		
		return totValue;
		
	}
	
	public double calculateTotWage(List<Footballer> footballers) {
		
		double totWage = 0.0;
		
		for(Footballer fi: footballers) {
			totWage+=fi.getValue();
		}
		
		return totWage;
		
	}
	

	
	
	/* Metodi per calcolare gli indici medi dato un club */
	
	private double getMediumStatsByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		List<Footballer> footballersTeam = this.getFootballerByTeam(club);
		
		for(Footballer fi: footballersTeam) {
			tot += fi.getTechnique()+fi.getPassing()+fi.getMarking()+fi.getPositioning()+fi.getStrength();
		}
				
		mean = tot/(double)(5*footballersTeam.size());
		
		return mean;
		
	}
	
	private double getMediumTechniqueByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getTechnique();
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumPassingByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getPassing();
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumMarkingByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getMarking();
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumPositioningByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getPositioning();
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumStrenghtByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getStrength();
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	/* Getters e setters per l'attributo selectedTeam */
	public Club getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(Club selectedTeam) {
		this.selectedTeam = selectedTeam;	
		/* Ogni volta che chiamo questo metto la lista dei giocatori dentro il club */
	}
	
	/* METODI NON PIU? USATI
	public Footballer getFootballerByName(String name) {
	return this.footballerDAO.getFootballerByName(name);
	}

	public Footballer getFootballerByNumber(int number) {
	return this.footballerDAO.getFootballerByNumber(number);
	} 
		public List<Footballer> getFootballersByMaxWage(int maxWage) {
		return this.footballerDAO.getFootballersByMaxWage(maxWage);
	}
	
	public List<Footballer> getFootballerWithMinMediumAndMaxSalary(Footballer footballer) {
		return this.footballerDAO.getFootballerWithMinMediumAndMaxSalary(footballer);
	}
	
	public List<String> getAllClubs(){
		return this.clubDAO.getAllClubs();
	}		
	
	private List<Club> getAllClubsObject() {
		return this.clubDAO.getAllClubsObject();
	}
	
	public void chargeTeam(String team) {
		
		//COSI' OGNI VOLTA CARICA
		if(this.clubs!=null) {
			for(Club ci: this.clubs) {
				if(ci.getName().compareTo(team)==0) {
					ci.setFootballers((LinkedList<Footballer>) this.clubDAO.getFootballerByTeam(team));
				}
			}
		}

	}
	
	private List<Footballer> getPossiblePlayer(List<Footballer> selected) {
		
		
		List<Footballer> result = new LinkedList<>();
		
		for(Footballer fi: selected) {
			//DEVE AVERE SALARIO INFERIORE MEDIA INDICI SUPERIORI E VALORE MINORE
			if(this.getPossibleFootballers(fi)!=null) {
				for(Footballer fii: this.getPossibleFootballers(fi)) {
					result.add(fii);
				}
			} else {
				System.out.println("Lista Vuota\n");
			}
			
		}
		
		
		return result;
	}
	
	public Footballer bestChoice(double maxWage, double maxValue, Club club) {
		
		Footballer footballer = null;
		
		//Il club mi servirà per calcolare le statistiche
		
			Magari aggiungi ancora il ruolo
		 	poi puoi fare la versione in cui prendi la lista e le migliori 5 soluzioni
		
		club.setFootballers(this.getFootballerByTeam(club.getName()));
		
		double stats = this.getMediumStatsByClub(club);
		
		return this.footballerDAO.getFootballerBestChoice(maxWage, maxValue, stats);
		
	}
	
	public List<Footballer> bestChoices(double maxWage, double maxValue, Club club) {
		
		//Il club mi servirà per calcolare le statistiche
		
			Magari aggiungi ancora il ruolo
		 	poi puoi fare la versione in cui prendi la lista e le migliori 5 soluzioni
		
		//System.out.println(club);
		club.setFootballers(this.getFootballerByTeam(club.getName()));
		double stats = this.getMediumStatsByClub(club); //non ci sono i giocatori del clu
		
		return this.footballerDAO.getFootballerBestChoices(maxWage, maxValue, stats);
		
	}
	
	Metodo utili 
		
	public Club getClubByName(String name) {
		return this.clubDAO.getClubByName(name);
	}
	
	public List<String> getAllClubsByChampionship(String championship){
		return this.clubDAO.getAllClubsByChampionship(championship);
	}
	
		private boolean checkClub(String clubName) {
		
		Club club = null;
		
		for(Club ci: clubs) {
			if(ci.getName().compareTo(clubName)==0) {
				club = ci;
			}
		}
		
		
		if(club.getChampionship().compareTo("Spain (First Division)")==0) {
			return true;
		}
		if(club.getChampionship().compareTo("Italy (Serie A)")==0) {
			return true;
		}
		if(club.getChampionship().compareTo("France (Ligue 1 Conforama)")==0) {
			return true;
		}
		if(club.getChampionship().compareTo("Germany (Bundesliga)")==0) {
			return true;
		}
		if(club.getChampionship().compareTo("England (Premier Division)")==0) {
			return true;
		}
		
		
		return false;
	}
	
	*/

	
}
