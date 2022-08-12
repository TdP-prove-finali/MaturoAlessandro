package it.polito.tdp.FootballManager.model;

import java.util.LinkedList;
import java.util.List;

public class Club {
	
	private String name;
	private String championship;
	private List<Footballer> footballers;
	private int tot_value;
	private double medium_age;
	private double medium_technique;
	private double medium_passing;
	private double medium_marking;
	private double medium_positioning;
	private double medium_strength;

	
	public Club(String name, String championship) {
		super();
		this.name = name;
		this.championship = championship;
		footballers = new LinkedList<Footballer>();
		this.tot_value=0;
		this.medium_age=0.0;
		this.medium_technique=0.0;
		this.medium_passing=0.0;
		this.medium_marking=0.0;
		this.medium_positioning=0.0;
		this.medium_strength=0.0;
		
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Footballer> getFootballers() {
		return footballers;
	}

	public void setFootballers(List<Footballer> list) {
		this.footballers = list;
	}

	public int getTot_value() {
		return tot_value;
	}

	public void setTot_value(int tot_value) {
		this.tot_value = tot_value;
	}

	public double getMedium_age() {
		return medium_age;
	}

	public void setMedium_age(double medium_age) {
		this.medium_age = medium_age;
	}

	public double getMedium_technique() {
		return medium_technique;
	}

	public void setMedium_technique(double medium_technique) {
		this.medium_technique = medium_technique;
	}

	public double getMedium_passing() {
		return medium_passing;
	}

	public void setMedium_passing(double medium_passing) {
		this.medium_passing = medium_passing;
	}

	public double getMedium_marking() {
		return medium_marking;
	}

	public void setMedium_marking(double medium_marking) {
		this.medium_marking = medium_marking;
	}

	public double getMedium_positioning() {
		return medium_positioning;
	}

	public void setMedium_positioning(double medium_positioning) {
		this.medium_positioning = medium_positioning;
	}

	public double getMedium_strength() {
		return medium_strength;
	}

	public void setMedium_strength(double medium_strength) {
		this.medium_strength = medium_strength;
	}

	

	public String getChampionship() {
		return championship;
	}


	public void setChampionship(String championship) {
		this.championship = championship;
	}


	@Override
	public String toString() {
		return "Club [name=" + name + ", championship=" + championship + "]";
	}
	
	
	

}
