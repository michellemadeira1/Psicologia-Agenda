package com.projeto.integrador.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.integrador.service.Servicos;
import com.projeto.integrador.usuario.Usuario;

@RestController
@RequestMapping("/crud")
@CrossOrigin("*")
public class Crud2Controller {

	@Autowired
	private Servicos servicos;
	
	
	
	@GetMapping("id/{id}")
	public ResponseEntity<Usuario>pegarPorId(@PathVariable Long id){
		return servicos.pegarId(id);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario>postar(@RequestBody Usuario dto ){
		
		return servicos.postarUsuario1(dto);
	}
	
	
	/*@PutMapping("/logar")
	public ResponseEntity<?>logar(@Valid @RequestBody Usuario dadosParaLogar){
		return servicos.pegarCredenciais(dadosParaLogar)
				.map(usuarioAutorizado ->ResponseEntity.ok(usuarioAutorizado))
				.orElse(ResponseEntity.status(401).build());
	}*/
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Object>deletar(@PathVariable Long id){
		return servicos.deletar(id);
	}
}
