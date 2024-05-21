package com.projeto.integrador.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projeto.integrador.usuario.Usuario;



@Repository
public interface CrudRepository extends JpaRepository<Usuario, Long> {
	
	//public List<Usuario> findAll();
	//public List<Usuario> findById(Long id);
	//public Optional<Usuario>findByResponsavel(String responsavel);*/
	List<Usuario> findByNomeContainingIgnoreCase(String nome);

}