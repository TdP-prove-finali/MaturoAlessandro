package it.polito.tdp.FootballManager.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.FootballManager.db.ClubDAO;
import it.polito.tdp.FootballManager.db.FootballerDAO;

public class Model {
	
	private FootballerDAO footballerDAO;
	private ClubDAO clubDAO;
	
	//UTILY
	private List<Club> clubs;
	private List<String> championships;
	private List<String> ruoliUtilizzati;
	private int numP;
	private int numD;
	private int numC;
	private int numA;
	private int sizeSelected;
	
	//PARTE RICORSIVA
	private List<Footballer> best;
	private List<Footballer> possible;
	private int totBest;
	private int size;
	
	public Model() {
		this.footballerDAO = new FootballerDAO();
		this.clubDAO = new ClubDAO();
		
		clubs = this.getAllClubsObject();
		
		//DEVO CARICARE SOLO I 5 CAMPIONATI PIU' IMportanti
		//ADESSO STO CARICANDO NEL MODEL
		/*for(Club ci: clubs) {
				this.chargeTeam(ci.getName());
		}*/
		
	}
	
	

	private List<Club> getAllClubsObject() {
		return this.clubDAO.getAllClubsObject();
	}

	public Footballer getFootballerByName(String name) {
		return this.footballerDAO.getFootballerByName(name);
	}
	
	public Footballer getFootballerByNumber(int number) {
		return this.footballerDAO.getFootballerByNumber(number);
	}
	
	public List<Footballer> getFootballerByTeam(String team) {
		
		//COSI' OGNI VOLTA CARICA
		if(this.clubs!=null) {
			for(Club ci: this.clubs) {
				if(ci.getName().compareTo(team)==0) {
					ci.setFootballers((LinkedList<Footballer>) this.clubDAO.getFootballerByTeam(team));
				}
			}
		}

		
		return this.clubDAO.getFootballerByTeam(team);
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
	
	public List<Footballer> getFootballersByMaxWage(int maxWage) {
		return this.footballerDAO.getFootballersByMaxWage(maxWage);
	}
	
	public List<Footballer> getFootballerWithMinMediumAndMaxSalary(Footballer footballer) {
		return this.footballerDAO.getFootballerWithMinMediumAndMaxSalary(footballer);
	}
	
	public List<String> getAllClubs(){
		return this.clubDAO.getAllClubs();
	}	
	
	public List<String> getAllClubsByChampionship(String championship){
		return this.clubDAO.getAllClubsByChampionship(championship);
	}
	
	public Club getClubByName(String name) {
		return this.clubDAO.getClubByName(name);
	}
	
	//AVERAGE E SUM
	
	public double averageAge(String team) {
		return this.clubDAO.averageAge(team);		
	}
	
	public double averageWage(String team) {
		return this.clubDAO.averageWage(team);
	}
	
	public double totValue(String team) {
		return this.clubDAO.totValue(team);
	}
	
	public double averageTec(String team) {
		return this.clubDAO.averageTec(team);
	}
	
	public double averagePas(String team) {
		return this.clubDAO.averagePas(team);
	}
	
	public double averageMar(String team) {
		return this.clubDAO.averageMar(team);
	}
	
	public double averagePos(String team) {
		return this.clubDAO.averagePos(team);
	}
	
	public double averageStr(String team) {
		return this.clubDAO.averageStr(team);
	}
	
	public String averageStatistics(String team) {
		
		String res = "";
		
		// Tolto statistiche medie a inizio stringa e stipendio medio 
		
		 res+=  "Età media: "+this.averageAge(team)+"\n\n"+
				"Valore totale: "+this.totValue(team)+" M€\n\n"+
				"Tecnica: "+this.averageTec(team)+"\n\n"+
				"Passaggio: "+this.averagePas(team)+"\n\n"+
				"Marcamento: "+this.averageMar(team)+"\n\n"+
				"Posizionamento: "+this.averagePos(team)+"\n\n"+
				"Forza: "+this.averageStr(team)+"\n\n";
		
		return res;
		
	}
	
	//PER MENU
	
	public List<String> getAllChampionships(){
		List<String> result = new LinkedList<>();
		
		result.add("Spain (First Division)");
		result.add("Italy (Serie A)");
		result.add("France (Ligue 1 Conforama)");
		result.add("Germany (Bundesliga)");
		result.add("England (Premier Division)");
		
		return result;
		
	}
	
	//PARTE 1
	//OK DOVREBBE ANDARE
	public List<Footballer> getFootballersMaxSalaryAndMaxValueAndBetterIndex(String index, String clubStr, int maxWage, int maxValue) {
		
		double mean = 0.0;
		
		Club club = this.getClubByName(clubStr);
		
		//meanTeam non va
		//double meanTeam = this.getMediumStatsByClub(club);
		//DEVO METTERE CLUB COME OGGETTO
		
		this.chargeTeam(clubStr);
		
		switch(index) {
			case "Techinque":
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
		
		
		return this.footballerDAO.getFootballersMaxSalaryAndMaxValueAndBetterIndex(index, club, mean, maxWage, maxValue, 10.0);
		
		
	}
	
	//PARTE RICORSIVA VERSIONE LISTA
	public List<Footballer> init(List<Footballer> selected) {
		
		this.size=selected.size();
		
		possible = new LinkedList<>();
		best = new LinkedList<>();
		List<Footballer> partial = new LinkedList<>();
		
		/* ME LI PRENDE TUTTI E DUE
		 * for(Footballer fi: selected) {
			System.out.println(fi.getName()+"\n");
		}*/
		
		for(Footballer fi: selected) {
			//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+fi.getName()+"\n");

			this.getPossibleFootballers(fi);
		}
		
		for(Footballer fi: possible) {
			System.out.println(fi.getName()+"\n");
		}
		
		// FINO A QUA VA TUTTO

		
		this.numP=0;
		this.numD=0;
		this.numC=0;
		this.numA=0;	
		
		ruoliUtilizzati = this.caricaRuoli(selected);

		
		this.totBest=this.totIndexes(selected);
		int tot=0;
		
		//CAPIRE SE RAGIONARE CON MEDIA O TOTALE
		
		this.sizeSelected = selected.size();
		
		recursion(partial, tot);
		
		System.out.println("RICORSIONE FINITA");
		
		return best;
	}



	/* Controllo che non sia lo stesso calciatore già fatto devo ancora controllare che non ci sia nei possibili perchè magari x è
	 * la soluzione migliore di y, il fatto che x sia la soluzione migliore di x lo controllo già e quindi non ci potrà essere
	 * questa seconda cosa la devo controllare qua dentro*/
	
	private void recursion(List<Footballer> partial, int tot) {

		// SE E' TROPPO LUNGO PIUTTOSTO PRENDI LE 10/15 MIGLIORI SOLUZIONI PER OGNI GIOCATORE
		
		if(partial.size()==this.sizeSelected) {
			
			// Qua devi controllare che ci siano tutti i ruoli
			
			// FATTO NEL CHECK RUOLI!!!!!!!!!
			
			// fai tipo un for con i ruoli e dei boolean
			
			
			if(tot>this.totBest) {
				System.out.println("Ho superato un tot>totBets\n");
				if(checkRuoli(partial)) {
					System.out.println("Ho superato un check ruoli\n");
					this.best = new LinkedList<>(partial);
					this.totBest=tot;
				}
				
			}
		
		} else {
			
			for(Footballer fi: this.possible) {
				//if(!partial.contains(fi) && checkClub(fi.getClub())) {
				//!possible.contains(fi) questo per quanto scritto sopra
				
				//Footballer ha equals ho già controllato
				
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

	/*private List<Footballer> getPossiblePlayer(List<Footballer> selected) {
		
		
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
	}*/



	/*Dovranno avere lo stesso ruolo, <= salario, <= valore, Media indici maggiore*/
	
	private void getPossibleFootballers(Footballer footballer) {
		
		double meanIndexes = this.averageStats(footballer);
		
		//System.out.println(meanIndexes+"\n"); //OK QUESTO LO PRENDE
		
		// NON MI PRENDE GIOCATORI
		
		List<Footballer> result = footballerDAO.getPossibleFootballers(footballer, meanIndexes);
		
		//System.out.println("Sto aggiungendo a possible:"+result.size()+"\n");
		
		for(Footballer fi: result) {
			if(!this.possible.contains(fi)) {
				this.possible.add(fi);
				//System.out.println(fi.getName()+"\n");
			}
		}
		
		//System.out.println("FInito aggiunta");
	}



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
	
	
	private List<String> caricaRuoli(List<Footballer> selected) {
		
		List<String> result = new LinkedList<>();
		
		for(Footballer fi: selected) {
			result.add(fi.getBest_role());
			
			if(fi.getBest_role().compareTo("P")==0) {
				this.numP++;
			}
			if(fi.getBest_role().compareTo("D")==0) {
				this.numD++;
			}
			if(fi.getBest_role().compareTo("C")==0) {
				this.numC++;
			}
			if(fi.getBest_role().compareTo("A")==0) {
				this.numA++;
			}
		}
		
		
		System.out.println("LISTA:\n");
		System.out.println("P:"+this.numP+"\n");
		System.out.println("D:"+this.numD+"\n");
		System.out.println("C:"+this.numC+"\n");
		System.out.println("A:"+this.numA+"\n");

		
		return result;
	}

	private boolean checkRuoli(List<Footballer> partial) {

		int cntP=0;
		int cntD=0;
		int cntC=0;
		int cntA=0;
		
		for(Footballer fi: partial) {
			if(fi.getBest_role().compareTo("P")==0) {
				cntP++;
			}
			if(fi.getBest_role().compareTo("D")==0) {
				cntD++;
			}
			if(fi.getBest_role().compareTo("C")==0) {
				cntC++;
			}
			if(fi.getBest_role().compareTo("A")==0) {
				cntA++;
			}
		}
		
		System.out.println("P:"+cntP+"\n");
		System.out.println("D:"+cntD+"\n");
		System.out.println("C:"+cntC+"\n");
		System.out.println("A:"+cntA+"\n");
		
		
		if(cntP!=this.numP) {
			System.out.println("USCITO PORTI");
			return false;
		}
		if(cntD!=this.numD) {
			System.out.println("USCITO DIFENSORI");
			return false;
		}
		if(cntC!=this.numC) {
			System.out.println("USCITO CENTROCAMPISTI");
			return false;
		}
		
		System.out.println("cntA "+cntA+ " numA "+this.numA);

		if(cntA!=this.numA) {
			System.out.println("USCITO ATTACANTI");
			return false;
		}
		
		System.out.println("PASSATI");
		return true;
	}



// FINO A QUA
	
	//C:\Users\User\Documents\MATURO_ALESSANDRO\UNIVERSITA\ANNO_3\SECONDO_SEMESTRE\TECNICHE_DI_PROGRAMMAZIONE\WORKSPACE\FootballManager\src\main\resources\fxml


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
	


	// METODI PER LA PARTE IMPORTANTE
	
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
	
	
	
	//RICORSIONE CON PIU' GIOCATORI
	
	
	//PUNTO 1
	
	public Footballer bestChoice(double maxWage, double maxValue, Club club) {
		
		Footballer footballer = null;
		
		//Il club mi servirà per calcolare le statistiche
		
		/*Magari aggiungi ancora il ruolo
		 * poi puoi fare la versione in cui prendi la lista e le migliori 5 soluzioni*/
		
		club.setFootballers(this.getFootballerByTeam(club.getName()));
		
		double stats = this.getMediumStatsByClub(club);
		
		return this.footballerDAO.getFootballerBestChoice(maxWage, maxValue, stats);
		
	}
	
	public List<Footballer> bestChoices(double maxWage, double maxValue, Club club) {
		
		//Il club mi servirà per calcolare le statistiche
		
		/*Magari aggiungi ancora il ruolo
		 * poi puoi fare la versione in cui prendi la lista e le migliori 5 soluzioni*/
		
		//System.out.println(club);
		club.setFootballers(this.getFootballerByTeam(club.getName()));
		double stats = this.getMediumStatsByClub(club); //non ci sono i giocatori del clu
		
		return this.footballerDAO.getFootballerBestChoices(maxWage, maxValue, stats);
		
	}
	
	private double getMediumStatsByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getTechnique()+footballer.getPassing()+footballer.getMarking()+footballer.getPositioning()+footballer.getStrength();
			//System.out.println(footballer);
		}
				
		mean = tot/(double)(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	
	//INDICI MEDI
	private double getMediumTechniqueByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getTechnique();
			//System.out.println(footballer);
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumPassingByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getPassing();
			System.out.println(footballer);
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumMarkingByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getMarking();
			System.out.println(footballer);
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumPositioningByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getPositioning();
			System.out.println(footballer);
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}
	
	private double getMediumStrenghtByClub(Club club) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		for(Footballer footballer: club.getFootballers()) {
			tot = footballer.getStrength();
			System.out.println(footballer);
		}
				
		mean = tot/(5*club.getFootballers().size());
		
		return mean;
		
	}



	public List<String> getAllIndexes() {
		
		List<String> result = new LinkedList<>();
		
		result.add("Techinque");
		result.add("Marking");
		result.add("Passing");
		result.add("Strenght");
		result.add("Positioning");
		
		return result;
	}
	
}
