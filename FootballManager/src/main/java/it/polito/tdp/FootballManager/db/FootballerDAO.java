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
	//(index, role, club, mean, maxWage, maxValue, 10.0);

	//METTERE ANCORA MEDIA TEAM DALL'ALTRO LATO
	//CONTROLLARE ANCORA CHE CLUB SIA DIVERSO
	//index, role, club, mean, maxWage, maxValue, meanTeam
	public List<Footballer> getFootballersMaxSalaryAndMaxValueAndBetterIndex(String index, String role, Club club, double mean, double maxWage, double maxValue, double meanTeam) {
		
		List<Footballer> footballers = new LinkedList<>();
		
		String indexStr = "";
		
		/* Li ordino per numero alla fine così so che prendo i 20 migliori per media indici */
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
				
				//con questo controllo ulteriore verifica che la media delle statistiche sia superiore al team
				if(this.getMediumStatsByPlayer(f)>=meanTeam) {
					footballers.add(f);
					cnt++;
					if(cnt>=20) {
						//stay=false;
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
			
			Collections.sort(footballers, new ComparatorPlayersByTotIndexes());
						
			for(Footballer fi: footballers) {
				if(bestTen.size()<10) {
					bestTen.add(fi);
				}
			}
		}

		return bestTen;		
	}
	

}
