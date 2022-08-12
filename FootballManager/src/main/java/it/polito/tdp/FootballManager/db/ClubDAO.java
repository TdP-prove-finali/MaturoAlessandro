package it.polito.tdp.FootballManager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.FootballManager.model.Club;
import it.polito.tdp.FootballManager.model.Footballer;

public class ClubDAO {
	
	public List<Footballer> getFootballerByTeam(String team) {
		
		List<Footballer> footballers = new LinkedList<Footballer>();
		
		String sql = "SELECT p.number, p.name, p.best_pos, p.club, p.age, p.value, p.wage, p.tec, p.pas, p.mar, p.pos, p.str "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				
			double valueMln = ((double)(rs.getInt("value")))/(1000000);
				
				footballers.add(new Footballer(rs.getInt("number"),
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
	
	public List<String> getAllClubs(){
		
		List<String> clubs = new LinkedList<>();
		
		String sql = "SELECT DISTINCT p.club as c "
				+ "FROM playerdef p "
				+ "WHERE (p.based='Spain (First Division)') <> (p.based='Italy (Serie A)') <> (p.based='France (Ligue 1 Conforama)') <> (p.based='Germany (Bundesliga)') <> (p.based='England (Premier Division)') "
				+ "ORDER BY p.club";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				clubs.add(rs.getString("c"));
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clubs;
	}
	
	public List<String> getAllClubsByChampionship(String championship){
		List<String> clubs = new LinkedList<>();
		
		String sql = "SELECT DISTINCT p.club as c "
				+ "FROM playerdef p "
				+ "WHERE p.based=? "
				+ "ORDER BY p.club";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, championship);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				clubs.add(rs.getString("c"));
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clubs;
	}
	
	public double averageAge(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.age) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}
	
	public double averageWage(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.wage) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}
	
	public double totValue(String team) {
		
		double tot = 0.0;
		
		String sql = "SELECT SUM(p.value) as tot "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				
				tot = (rs.getDouble("tot"))/(1000000);

			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return tot;
	}
	
	public double averageTec(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.tec) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}
	
	public double averagePas(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.pas) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}
	
	public double averageMar(String team) {
			
			double avg = 0.0;
			
			String sql = "SELECT AVG(p.mar) as avg "
					+ "FROM playerdef p "
					+ "WHERE p.club = ?";
			
			
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);		
				
				st.setString(1, team);
				
				ResultSet rs = st.executeQuery();
				
		
				
				while(rs.next()) {
					avg = rs.getDouble("avg");
				}
				
				conn.close();
				st.close();
				rs.close();			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return avg;
		}

	public double averagePos(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.pos) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}

	public double averageStr(String team) {
		
		double avg = 0.0;
		
		String sql = "SELECT AVG(p.str) as avg "
				+ "FROM playerdef p "
				+ "WHERE p.club = ?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			
			st.setString(1, team);
			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				avg = rs.getDouble("avg");
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return avg;
	}
	
	
	public List<Club> getAllClubsObject(){
		
		List<Club> clubs = new LinkedList<>();
		
		String sql = "SELECT p.club as club, p.based as c "
				+ "FROM playerdef p";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);			
			ResultSet rs = st.executeQuery();
			
	
			
			while(rs.next()) {
				clubs.add(new Club(rs.getString("club"), rs.getString("c")));
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clubs;
	}

	public Club getClubByName(String name) {
		Club club = null;
		
		String sql = "SELECT p.club as club, p.based as c "
				+ "FROM playerdef p "
				+ "WHERE p.club=?";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);			
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			
	
			
			if(rs.first()!=false) {
				club = new Club(rs.getString("club"), rs.getString("c"));
			}
			
			conn.close();
			st.close();
			rs.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return club;
	}
}
