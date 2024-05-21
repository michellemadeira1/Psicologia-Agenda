package com.projeto.integrador.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.integrador.Repository.AnotacaoRepository;
import com.projeto.integrador.Repository.CrudRepository;
import com.projeto.integrador.usuario.Anotacao;
import com.projeto.integrador.usuario.Usuario;
import com.projeto.integrador.usuario.UsuarioDTO;


@Service
public class Servicos {
	
	
	@Autowired
    private AnotacaoRepository anotacaoRepository;
	@Autowired
	private CrudRepository crudrepository;

	
	public List<Usuario> buscarTodosUsuarios() {
	    return crudrepository.findAll();
	}
	@Transactional
	public Usuario findById(Long id) {
        return crudrepository.findById(id)
        		.orElse(null);
    }
	
	public List<UsuarioDTO> findAllUsuariosWithAnotacoes() {
        List<Usuario> usuarios = crudrepository.findAll();
        return usuarios.stream().map(usuario -> {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setUsuario(usuario);
            List<Anotacao> anotacoes = anotacaoRepository.findByUsuarioId(usuario.getId());
            usuarioDTO.setAnotacoes(anotacoes);
            return usuarioDTO;
        }).collect(Collectors.toList());
    }
	
	public ResponseEntity<Usuario>pegarId(Long id){
		return crudrepository.findById(id)
				.map(idExiste-> ResponseEntity.status(200).body(idExiste)).orElse(ResponseEntity.status(404).build());
	}
	
	public ResponseEntity<Usuario>postarUsuario1(Usuario dto){
		/*Optional<Usuario>usuarioExiste = crudrepository.findByResponsavel(dto.getResponsavel());
		
		if(usuarioExiste.isPresent()) {
			return ResponseEntity.status(406).build();
		}else {
			
			
			
			return ResponseEntity.ok(crudrepository.save(dto));
		}*/
		return null;
	}
	
	
	public void salvarAnotacao(Anotacao anotacao) {
        anotacaoRepository.save(anotacao);
    }
	public List<Anotacao> findByUsuarioId(Long usuario) {
        return anotacaoRepository.findByUsuarioId(usuario);
    }
	
	public void postarUsuario(Usuario usuario) {
		crudrepository.save(usuario);
    }
	public void saveAnotacao(Anotacao anotacao) {
        anotacaoRepository.save(anotacao);
    }
	
	public ResponseEntity<Object>deletar(Long id){
		
		Optional<Usuario> idExistente = crudrepository.findById(id);
		if (idExistente.isEmpty()) {
			return ResponseEntity.status(400).build();
		}else {
			crudrepository.deleteById(id);
			return ResponseEntity.status(200).build();
		}
	}
	
	
	

}


