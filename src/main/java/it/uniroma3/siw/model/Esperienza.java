package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Esperienza {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String titolo;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Integer stelle;
	
	@NotBlank
	private String descrizione;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Ristorante ristorante;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getStelle() {
		return stelle;
	}

	public void setStelle(Integer stelle) {
		this.stelle = stelle;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Esperienza other = (Esperienza) obj;
		return Objects.equals(titolo, other.titolo);
	}
	
	
	
	

}
