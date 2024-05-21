package com.projeto.integrador.usuario;


import java.util.List;

public class UsuarioDTO {
    private Usuario usuario;
    private List<Anotacao> anotacoes;
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}
	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}

 
}
