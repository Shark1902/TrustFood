package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Chef {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@NotBlank
	private String origine;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ImageEntity foto;
	
	@ManyToMany(mappedBy="chefs", fetch=FetchType.LAZY)
	private List<Ristorante> ristoranti;

	
	public Chef() {}
	
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	

	public ImageEntity getFoto() {
		return foto;
	}

	public void setFoto(ImageEntity foto) {
		this.foto = foto;
	}

	public List<Ristorante> getRistoranti() {
		return ristoranti;
	}

	public void setRistoranti(List<Ristorante> ristoranti) {
		this.ristoranti = ristoranti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chef other = (Chef) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(nome, other.nome);
	}
	
	
	

}
