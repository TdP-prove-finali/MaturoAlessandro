package it.polito.tdp.FootballManager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.FootballManager.model.Club;
import it.polito.tdp.FootballManager.model.Footballer;

public class FootballerDAO {
	
	public Footballer getFootballerByName(String name) {
		
		Footballer footballer = null;
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.name = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, name);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				footballer = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						rs.getInt("value"),
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));
						
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return footballer;
	}
	
	public Footballer getFootballerByNumber(int number) {
		
		Footballer footballer = null;
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.number=?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, number);
			ResultSet rs = st.executeQuery();
			
			
			while(rs.next()) {
				footballer = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						rs.getInt("value"),
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));

			}
			
			conn.close();
			st.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return footballer;
	}
	
	
	public List<Footballer> getFootballersByMaxWage(int maxWage) {
		
		List<Footballer> footballers = new LinkedList<>();
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, maxWage);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				footballers.add(new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						rs.getInt("value"),
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str")));	
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return footballers;
	}
	
	public List<Footballer> getFootballerWithMinMediumAndMaxSalary(Footballer footballer) {
		
		List<Footballer> footballers = new LinkedList<>();
		
		double min = this.getMediumStatsByPlayer(footballer);
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, footballer.getWage());
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				
				double valueMln = ((double)(rs.getInt("value")))/(1000000);

				
				Footballer f = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						valueMln,
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));	
				
				if(this.getMediumStatsByPlayer(f)>=min && f.getWage()!=0 && footballer.getBest_role().compareTo(f.getBest_role())==0) {
					footballers.add(f);
				}
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return footballers;
	}
	
	

	private double getMediumStatsByPlayer(Footballer footballer) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		tot = footballer.getTechnique()+footballer.getPassing()+footballer.getMarking()+footballer.getPositioning()+footballer.getStrength();
		
		mean = tot/5;
		
		return mean;
		
	}
	
	//CALCIATORE SINGOLO
	
	public Footballer getFootballerBestChoice(double maxWage, double maxValue, double stats) {
		
		List<Footballer> footballers = new LinkedList<>();
				
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND p.value <= ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setDouble(1,maxWage);
			st.setDouble(2, maxValue);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				
				double valueMln = ((double)(rs.getInt("value")))/(1000000);

				
				Footballer f = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						valueMln,
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));	
				
				if(this.getMediumStatsByPlayer(f)>=stats) {
					footballers.add(f);
				}
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(footballers.size()>0) {
			return footballers.get(0);
		} else {
			return null;
		}
	}
	
	//LISTA
	
	public List<Footballer> getFootballerBestChoices(double maxWage, double maxValue, double stats) {
		
		List<Footballer> footballers = new LinkedList<>();
				
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND p.value <= ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setDouble(1,maxWage);
			st.setDouble(2, maxValue);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				
				double valueMln = ((double)(rs.getInt("value")))/(1000000);

				
				Footballer f = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						valueMln,
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));	
				
				if(this.getMediumStatsByPlayer(f)>=stats) {
					footballers.add(f);
				}
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(footballers.size()>0) {
			
			List<Footballer> bestFive = new LinkedList<Footballer>();
			
			for(int i=0;i<5;i++) {
				bestFive.add(footballers.get(i));
			}
			
			return bestFive;
			
		} else {
			return null;
		}
	}

	public List<Footballer> getFootballersMaxSalaryAndMaxValueAndBetterIndex(String index, Club club, double mean, int maxWage, int maxValue, double meanTeam) {
		
		List<Footballer> footballers = new LinkedList<>();
		
		String indexStr = "";
		
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND ? >= ? AND p.value <= ?";
		
		switch(index) {
		
		case "Techinque":
			indexStr = "p.tec";
			break;
		case "Marking":
			indexStr = "p.mar";
			break;
		case "Passing":
			indexStr = "p.pas";
			break;
		case "Strenght":
			indexStr = "p.str";
			break;
		case "Positioning":
			indexStr = "p.pos";
			break;
		
		}
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, maxWage);
			st.setString(2, indexStr);
			st.setDouble(3, mean);
			st.setInt(4, maxValue);
			
			ResultSet rs = st.executeQuery();
			
			boolean stay = true;
			int cnt = 0;
			
			while(rs.next() && stay) {
				
				double valueMln = ((double)(rs.getInt("value")))/(1000000);

				
				Footballer f = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						valueMln,
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));	
				
				//con questo controllo ulteriore verifica che la media delle statistiche sia superiore al team
				if(this.getMediumStatsByPlayer(f)>=meanTeam) {
					footballers.add(f);
					cnt++;
					if(cnt>=5) {
						stay=false;
						break;
					}
				}
				
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return footballers;
	}

	public List<Footballer> getPossibleFootballers(Footballer footballer, double meanIndexes) {
		
		List<Footballer> footballers = new LinkedList<>();
				
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND p.value <= ? AND p.best_pos = ?";
		
		//FA DEI CASINI CON VALUE PERCHE' TU LO MEMORIZZI DIVISO UN MLN
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			//st.setInt(1, (footballer.getWage()*2));
			//st.setDouble(2, (footballer.getValue()*2));
			st.setInt(1, footballer.getWage());
			st.setDouble(2, footballer.getValue()*1000000);
			st.setString(3, footballer.getBest_role());
			
			ResultSet rs = st.executeQuery();
			
			//System.out.println("Medie da cnfrontare:\n");
			
			while(rs.next()) {
				
				//System.out.println("C'è almeno un risultato");
				
				double valueMln = ((double)(rs.getInt("value")))/(1000000);

				
				Footballer f = new Footballer(rs.getInt("number"),
						rs.getString("name"), 
						rs.getString("best_pos"), 
						rs.getString("club"), 
						rs.getInt("age"),
						valueMln,
						rs.getInt("wage"),
						rs.getInt("tec"),
						rs.getInt("pas"),
						rs.getInt("mar"),
						rs.getInt("pos"),
						rs.getInt("str"));	
				
				//System.out.println(this.getMediumStatsByPlayer(f)+"\n");
				
				if(this.getMediumStatsByPlayer(f)>=meanIndexes) {
					if(!f.equals(footballer)) {
						footballers.add(f);						
					}
				}
						
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// QUESTA PARTE QUA E' PER RETURNARE SOLO LE 5 MIGLIORI SOLUZIONI
		// tra l'altro occhio che così non va se tipo è 4 fai for(Footballer fi: ...)  e ragionaa con size 
		
		if(footballers.size()>0) {
			
			//System.out.println("Sono arrivato qua\n");
			
			// QUA DEVO PRENDERE SOLO I MIGLIORI
			
			List<Footballer> bestTen = new LinkedList<Footballer>();
			
			for(Footballer fi: footballers) {
				if(bestTen.size()<10) {
					bestTen.add(fi);
					//System.out.println(fi.getName()+"\n"); //ADESSO QUA FUNZIONA
				}
			}
			
			return bestTen;
			
		} else {
			return null;
		}
				
	}
	

}
