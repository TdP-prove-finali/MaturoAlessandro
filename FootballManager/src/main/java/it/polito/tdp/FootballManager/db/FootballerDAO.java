package it.polito.tdp.FootballManager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.FootballManager.model.Club;
import it.polito.tdp.FootballManager.model.ComparatorPlayersByTotIndexes;
import it.polito.tdp.FootballManager.model.Footballer;

public class FootballerDAO {	

	/**
	 * Usato all'interno di getFootballersMaxSalaryAndMaxValueAndBetterIndex e getPossibleFootballers
	 * @param footballer calciatore di cui si vuole calcolare la media dei 5 indici
	 * @return la media dei 5 indici del calciatore
	 */
	
	private double getMediumStatsByPlayer(Footballer footballer) {
		
		double mean = 0.0;
		double tot = 0.0;
		
		tot = footballer.getTechnique()+footballer.getPassing()+footballer.getMarking()+footballer.getPositioning()+footballer.getStrength();
		
		mean = tot/5;
		
		return mean;
		
	}

	
	
	/**
	 * 
	 * @param index indice scelto
	 * @param role ruolo del giocatore
	 * @param club club di appartenenza del giocatore
	 * @param mean media per quell'indice per quel club
	 * @param maxWage stipendio massimo che potrebbe percepire
	 * @param maxValue costo massimo del cartellino 
	 * @param meanTeam media indici team
	 * @return
	 */
	public List<Footballer> getFootballersMaxSalaryAndMaxValueAndBetterIndex(String index, String role, Club club, double mean, double maxWage, double maxValue, double meanTeam) {
		
		List<Footballer> footballers = new LinkedList<>();
		
		String indexStr = "";
		
		/* Li ordino per numero alla fine così so che prendo i 20 migliori per media di TUTTI gli indici (io ne uso solo 5) */
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND ? >= ? AND p.value <= ? AND p.best_pos = ? AND p.club <> ? "
				+ "ORDER BY p.number"; 
		
		switch(index) {
		
		case "Technique":
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
			
			st.setDouble(1, maxWage);
			st.setString(2, indexStr);
			st.setDouble(3, mean);
			st.setDouble(4, maxValue);
			st.setString(5, role);
			st.setString(6, club.getName());
			
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
				
				/*con questo controllo ulteriore verifica che la media delle statistiche
				 *  sia superiore al team perchè dall'sql non si sarebbe potuto verificare*/
				
				if(this.getMediumStatsByPlayer(f)>=meanTeam) {
					footballers.add(f);
					cnt++;
					
					/*Prendo solo i 20 migliori*/
					
					if(cnt>=20) {
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

	/**
	 * Prende tutti i calciatori con stipendio inferiore, valore inferiore
	 * dello stesso ruolo del calciatore passato ma con media indici superiore
	 * @param footballer
	 * @param meanIndexes
	 * @return
	 */
	public List<Footballer> getPossibleFootballers(Footballer footballer, double meanIndexes) {
		
		List<Footballer> footballers = new LinkedList<>();
				
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.wage <= ? AND p.value <= ? AND p.best_pos = ? AND p.club <> ?";
				
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, footballer.getWage());
			st.setDouble(2, footballer.getValue()*1000000);
			st.setString(3, footballer.getBest_pos());
			st.setString(4, footballer.getClub());
			
			ResultSet rs = st.executeQuery();
			
			int i=0;
			
			while(rs.next()) {
				
				i++;
				
				/*prendo solo i migliori 20 */
				
				if(i>20) {
					break;
				}
				
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
				
				/* Lo aggiungo solo se la media indici è superiore a quella passata*/
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
		
		
		List<Footballer> bestTen = new LinkedList<>();
		
		if(footballers.size()>0) {
							
			for(Footballer fi: footballers) {
				/* prendo solo i migliori 10 */				
				if(bestTen.size()<10) {
					bestTen.add(fi);
				}
			}
		}

		return bestTen;		
	}
	

/*	public Footballer getFootballerByName(String name) {
		
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
				
				if(this.getMediumStatsByPlayer(f)>=min && f.getWage()!=0 && footballer.getBest_pos().compareTo(f.getBest_pos())==0) {
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
	}*/
	
}
