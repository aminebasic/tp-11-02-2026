package ma.tp.model;

public class Contact {
	private int id;
	private String nom;
	private String prenom;
	private String tel;
	private String email;
}

public Contact (){}

public Contact (int id, String nom. String prenom, String tel, String email){
	this.id = id;
	this.nom = nom;
	this.presom = prenom;
	this.tel = tel;
	this.email = email;
}

public Contact (String nom. String prenom, String tel, String email){
	this.nom = nom;
	this.presom = prenom;
	this.tel = tel;
	this.email = email;
}