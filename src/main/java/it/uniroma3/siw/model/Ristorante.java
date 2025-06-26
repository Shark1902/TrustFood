package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ristorante {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String citta;
	
	@NotBlank
	private String cucina;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<ImageEntity> images;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Chef> chefs;
	
	@OneToMany(mappedBy="ristorante", cascade=CascadeType.ALL)
	private List<Esperienza> esperienze;
	
	public Ristorante() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	

	public Set<ImageEntity> getImages() {
		return images;
	}

	public void setImages(Set<ImageEntity> images) {
		this.images = images;
	}

	public List<Chef> getChefs() {
		return chefs;
	}

	public void setChefs(List<Chef> chefs) {
		this.chefs = chefs;
	}

	public List<Esperienza> getEsperienze() {
		return esperienze;
	}

	public void setEsperienze(List<Esperienza> esperienze) {
		this.esperienze = esperienze;
	}
	
	

	public String getCucina() {
		return cucina;
	}

	public void setCucina(String cucina) {
		this.cucina = cucina;
	}

	@Override
	public int hashCode() {
		return Objects.hash(citta, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ristorante other = (Ristorante) obj;
		return Objects.equals(citta, other.citta) && Objects.equals(nome, other.nome);
	}
	
	

}
