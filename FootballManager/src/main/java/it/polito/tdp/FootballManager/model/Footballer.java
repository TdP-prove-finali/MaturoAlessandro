package it.polito.tdp.FootballManager.model;

import java.util.Objects;

public class Footballer {

	// p.number, p.name, p.best_role, p.club, p.age, p.wage, p.value, p.tec, p.pas, p.mar, p.pos, p.str, p.fin 
	
	private int id;
	private String name;
	private String best_pos;
	private String club;
	private int age;
	private double value;
	private int wage;
	private int technique;
	private int passing;
	private int marking;
	private int positioning;
	private int strength;
	
	public Footballer(int id, String name, String best_role, String club, int age, double value, int wage, int technique,
			int passing, int marking, int positioning, int strength) {
		super();
		this.id = id;
		this.name = name;
		this.best_pos = best_role;
		this.club = club;
		this.age = age;
		this.wage = wage;
		this.value = value;
		this.technique = technique;
		this.passing = passing;
		this.marking = marking;
		this.positioning = positioning;
		this.strength = strength;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBest_pos() {
		return best_pos;
	}

	public void setBest_pos(String best_pos) {
		this.best_pos = best_pos;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getTechnique() {
		return technique;
	}

	public void setTechnique(int technique) {
		this.technique = technique;
	}

	public int getPassing() {
		return passing;
	}

	public void setPassing(int passing) {
		this.passing = passing;
	}

	public int getMarking() {
		return marking;
	}

	public void setMarking(int marking) {
		this.marking = marking;
	}

	public int getPositioning() {
		return positioning;
	}

	public void setPositioning(int positioning) {
		this.positioning = positioning;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, best_pos, club, id, marking, name, passing, positioning, strength, technique, value,
				wage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Footballer other = (Footballer) obj;
		return age == other.age && Objects.equals(best_pos, other.best_pos) && Objects.equals(club, other.club)
				&& id == other.id && marking == other.marking && Objects.equals(name, other.name)
				&& passing == other.passing && positioning == other.positioning && strength == other.strength
				&& technique == other.technique && value == other.value && wage == other.wage;
	}

	@Override
	public String toString() {
		return "Footballer [id=" + id + ", name=" + name + ", best_role=" + best_pos + ", club=" + club + ", age="
				+ age + ", wage=" + wage + ", value=" + value + ", technique=" + technique + ", passing=" + passing
				+ ", marking=" + marking + ", positioning=" + positioning + ", strength=" + strength + "]";
	}
	
	
	
}
