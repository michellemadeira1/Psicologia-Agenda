package com.projeto.integrador.usuario;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	
	private String nome;
	
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	
	private String telefone;
	
	
	private String responsavel;
	
	
	
	@Temporal(TemporalType.DATE)
	private Date tratamento;
	
	
	@Temporal(TemporalType.DATE)
	private Date trataFinal;
	
	
	private String endereco;
	
	
	private String numero;
	
	//private String anotacao;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Anotacao> anotacoes;
	
	public Usuario() {
		
	}

	public Usuario( String nome, Date nascimento, String telefone,String responsavel,Date tratamento,Date trataFinal, String endereco,String numero  ) {
	
		this.nome = nome;
		this.nascimento = nascimento;
		this.telefone = telefone;
		this.responsavel = responsavel;
		this.tratamento = tratamento;
		this.trataFinal = trataFinal;
		this.endereco = endereco;
		this.numero = numero;
		
		
		
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Date getTratamento() {
		return tratamento;
	}

	public void setTratamento(Date tratamento) {
		this.tratamento = tratamento;
	}

	public Date getTrataFinal() {
		return trataFinal;
	}

	public void setTrataFinal(Date trataFinal) {
		this.trataFinal = trataFinal;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", nascimento=" + nascimento + ", telefone=" + telefone
				+ ", responsavel=" + responsavel + ", tratamento=" + tratamento + ", trataFinal=" + trataFinal
				+ ", endereco=" + endereco + ", numero=" + numero + "]";
	}

	

}
