package com.projeto.integrador.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.integrador.usuario.Anotacao;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long>{
	List<Anotacao> findByUsuarioId(Long usuarioId);
}
