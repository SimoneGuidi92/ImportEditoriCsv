package it.csv.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class CasaEditrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String citta;

	@ManyToMany
	@JoinTable(name = "editrice_book", joinColumns = @JoinColumn(name = "casaeditrice_id", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Book> books;

	@ManyToMany
	@JoinTable(name = "editrice_autore", joinColumns = @JoinColumn(name = "casaeditrice_id", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "autore_id", referencedColumnName = "id"))
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Autore> autori;

	public CasaEditrice(String nome, String citta) {
		super();
		this.nome = nome;
		this.citta = citta;
	}

}
