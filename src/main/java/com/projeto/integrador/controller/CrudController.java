package com.projeto.integrador.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.projeto.integrador.Repository.AnotacaoRepository;
import com.projeto.integrador.Repository.CrudRepository;
import com.projeto.integrador.service.Servicos;
import com.projeto.integrador.usuario.Anotacao;
import com.projeto.integrador.usuario.Usuario;
import com.projeto.integrador.usuario.UsuarioDTO;



@Controller
@CrossOrigin("*")

public class CrudController {
	@Autowired
    private AnotacaoRepository anotacaoRepository;
	@Autowired
	private Servicos servicos;
	@Autowired
	private CrudRepository crudrepository;	
	@GetMapping("/index")
	public String creador() {
		return "index";
	}
	@GetMapping("/index2")
	public String creador2() {
		return "redirect:/index";
	}
	
	@GetMapping("/creador")
	public String agenda1() {
		return "redirect:/list";
	}
	@GetMapping("/list")
	public String list() {
		return "creador";
	}

	@GetMapping("/pacienteDados")
	public String pacienteDados() {
		return "pacienteDados";
	}
	
	
	
	
	@PostMapping("/pacienteDados")
	public ModelAndView pacienteDados(@Valid @ModelAttribute Usuario usuario, BindingResult result,
	                                  @RequestParam("trataFinal") String trataFinalString,
	                                  @RequestParam("tratamento") String tratamentoString,
	                                  @RequestParam("nascimento") String nascimentoString) throws ParseException, java.text.ParseException {

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date trataFinal = formatter.parse(trataFinalString);
	    Date tratamento = formatter.parse(tratamentoString);
	    Date nascimento = formatter.parse(nascimentoString);

	    // Define o valor convertido na instância de Usuario
	    usuario.setTrataFinal(trataFinal);
	    usuario.setTratamento(tratamento);
	    usuario.setNascimento(nascimento);

	    servicos.postarUsuario(usuario);
	    System.out.println("result: " + usuario);
	    return new ModelAndView(new RedirectView("/index"));
	}
	
         
	@GetMapping("/pesquisa")
	public String pesquisa() {
		return "pesquisa";
	}
	/*@GetMapping("/pesquisa2")
    public String paciente(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Usuario> pacientes;
        if (search != null && !search.isEmpty()) {
            pacientes = crudrepository.findByNomeContainingIgnoreCase(search);
        } else {
            pacientes = crudrepository.findAll();
        }
        model.addAttribute("pacientes", pacientes);
        return "paciente";
    }*/
	
	
	
	@GetMapping("/anotacaoAdicionar2/{id}")
    public String mostrarFormularioAnotacao(@PathVariable Long id, Model model) {
        model.addAttribute("anotacao", new Anotacao());
        model.addAttribute("usuarioId", id);
        return "anotacaoNova";
    }
	
	
	/*@PostMapping("/anotacaoAdicionar/{id}")
	public ResponseEntity<Anotacao> salvarAnotacao(@PathVariable Long id, @RequestParam String texto) {
	    Usuario usuario = crudrepository.getById(id);
	    Anotacao anotacao = new Anotacao();
	    anotacao.setUsuario(usuario);
	    anotacao.setConteudo(texto);
	    servicos.salvarAnotacao(anotacao);
	    return ResponseEntity.ok(anotacao); // Retorna 200 OK juntamente com a nova anotação
	}*/

    @PostMapping("/anotacaoAdicionar/{id}")
    public String salvarAnotacao(@PathVariable Long id, @RequestParam String texto) {
        Usuario usuario = crudrepository.getById(id);
        Anotacao anotacao = new Anotacao();
        anotacao.setUsuario(usuario);
        anotacao.setConteudo(texto);
        servicos.salvarAnotacao(anotacao);
        return "redirect:/paciente";
    }
	
	
	
	
	
	
	
	
	@GetMapping("/paciente2")
	public String paciente() {
	    return "redirect:/paciente";
	}

	/*@GetMapping("/paciente")
	public ModelAndView paciente2() {
	    ModelAndView mv = new ModelAndView("paciente");
	    List<Usuario> usuarios = servicos.buscarTodosUsuarios(); // Supondo que você tenha um método para buscar usuários no banco de dados
	    mv.addObject("usuarios", usuarios);
	    return mv;
	}*/
	
	/* @GetMapping("/paciente")
	    public ModelAndView paciente2() {
	        ModelAndView mv = new ModelAndView("paciente");
	        List<UsuarioDTO> usuariosComAnotacoes = servicos.findAllUsuariosWithAnotacoes();
	        mv.addObject("usuarios", usuariosComAnotacoes);
	        return mv;
	        }  
	    */
	@GetMapping("/paciente")
	public ModelAndView paciente2(@RequestParam(name = "search", required = false) String search) {
	    ModelAndView mv = new ModelAndView("paciente");
	    List<Object> listaUsuarios = new ArrayList<>();

	    if (search != null && !search.isEmpty()) {
	        List<Usuario> usuarios = crudrepository.findByNomeContainingIgnoreCase(search);
	        listaUsuarios.addAll(usuarios);
	        List<UsuarioDTO> usuariosDTO = usuarios.stream().map(usuario -> {
	            UsuarioDTO usuarioDTO = new UsuarioDTO();
	            usuarioDTO.setUsuario(usuario);
	            List<Anotacao> anotacoes = anotacaoRepository.findByUsuarioId(usuario.getId());
	            usuarioDTO.setAnotacoes(anotacoes);
	            return usuarioDTO;
	        }).collect(Collectors.toList());
	        listaUsuarios.addAll(usuariosDTO);
	    } else {
	        List<UsuarioDTO> usuariosComAnotacoes = servicos.findAllUsuariosWithAnotacoes();
	        listaUsuarios.addAll(usuariosComAnotacoes);
	    }

	    mv.addObject("listaUsuarios", listaUsuarios);
	    mv.addObject("search", search);
	    return mv;
	}


	
	/* @GetMapping("/anotacaoDados/{id}")
	 public String anotacaoDados(@PathVariable Long id, Model model) {
	     // Aqui você deve implementar a lógica para buscar a anotação do banco de dados com o ID fornecido
	     Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
	     
	     // Adiciona a anotação ao modelo para ser acessada na página HTML
	     model.addAttribute("anotacao", anotacao);
	     
	     return "anotacaoDados";
	 }*/

	@GetMapping("/anotacaoDados/{id}")
	public String anotacaoDados(@PathVariable Long id, Model model) {
	    Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
	    model.addAttribute("anotacao", anotacao);
	    return "anotacaoDados";
	}

	
	@GetMapping("/anotacaoDados2/{id}")
	public String anotacaoDados2(@PathVariable Long id, Model model) {
		 Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
		    if (anotacao != null) {
		        model.addAttribute("anotacao", anotacao);
		    } else {
		        model.addAttribute("anotacao", null); // Removemos a anotação do modelo se não existir
		    }
		    return "redirect:anotacaoDados/"+id;
	}

	
	@GetMapping("/anotacao/{id}")
    public String anotacaoNova(@PathVariable Long id, Model model) {
        Usuario usuario = servicos.findById(id);
        Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
        model.addAttribute("anotacao", anotacao);
        model.addAttribute("usuario", usuario);
        model.addAttribute("anotacao", new Anotacao());
        return "anotacao";
    }
	@GetMapping("/anotacao2/{id}")
	public String anotacaoNova2(@PathVariable Long id, Model model) {
		model.addAttribute("anotacao", new Anotacao());
        model.addAttribute("usuarioId", id);
		
		return "redirect:anotacao/"+id;
	}


	
	@GetMapping("/anotacaoNova/{id}")
    public String anotacao(@PathVariable Long id, Model model) {
        model.addAttribute("anotacao", new Anotacao());
        model.addAttribute("usuarioId", id);
        return "anotacaoNova";
    }
	
	

	@PostMapping("/anotacaoNova2/{id}")
    public String anotacao2(@PathVariable Long id, @ModelAttribute Anotacao anotacao) {
        Usuario usuario = servicos.findById(id);
        anotacao.setUsuario(usuario);
        anotacaoRepository.save(anotacao);
        return "redirect:/anotacaoNova/" + id;
    }
	@GetMapping("/editarPaciente")
	public String editarPaciente() {
		return "editarPaciente";
	}
	@GetMapping("/editarPaciente2")
	public String editarPaciente2() {
		return "redirect:editarPaciente";
	}
	@GetMapping("/criarAnotacao/{id}")
	public String criarAnotacao() {
		
		return "criarAnotacao";
	}
	@GetMapping("/criarAnotacao2")
	public String criarAnotacao2() {
		return "redirect:criarAnotacao";
	}
	@GetMapping("/pacienteInfo/{id}")
	public String pacienteInfo(@PathVariable Long id, Model model) {
        Usuario usuario = servicos.findById(id);
        model.addAttribute("usuario", usuario);

		return "pacienteInfo";
	}
	
	@GetMapping("/pacienteInfo2/{id}")
	public String pacienteInfo2(@PathVariable Long id) {
		
		return "redirect:pacienteInfo"+id;
	}
	
	/*@PostMapping("/editarPaciente")
	public String editarPaciente(@ModelAttribute Usuario usuario) {
	    // Obtém o ID do paciente a ser editado
	    Long id = usuario.getId();
	    
	    // Busca o paciente no banco de dados pelo ID
	    Usuario pacienteExistente = crudrepository.findById(id).orElse(null);
	    
	    // Verifica se o paciente existe no banco de dados
	    if (pacienteExistente != null) {
	        // Atualiza as informações do paciente com base nos dados fornecidos no formulário
	        pacienteExistente.setNome(usuario.getNome());
	        pacienteExistente.setNascimento(usuario.getNascimento());
	        pacienteExistente.setTelefone(usuario.getTelefone());
	        pacienteExistente.setResponsavel(usuario.getResponsavel());
	        pacienteExistente.setTratamento(usuario.getTratamento());
	        pacienteExistente.setTrataFinal(usuario.getTrataFinal());
	        pacienteExistente.setEndereco(usuario.getEndereco());
	        pacienteExistente.setNumero(usuario.getNumero());
	        
	        // Salva as alterações no banco de dados
	        crudrepository.save(pacienteExistente);
	    } else {
	    	pacienteExistente=null;
	    }
	    
	    // Redireciona para a página de listagem de pacientes após a edição
	    return "redirect:/paciente";
	}*/
	
	/*@PostMapping("/pacienteInfo2/{id}")
	public String editarPaciente(@PathVariable Long id, @ModelAttribute Usuario usuario) {
	    // Obtém o ID do paciente a ser editado
	    //Long id = usuario.getId();
	    
	    // Busca o paciente no banco de dados pelo ID
	    Usuario pacienteExistente = crudrepository.findById(id).orElse(null);
	    
	    // Verifica se o paciente existe no banco de dados
	    if (pacienteExistente != null) {
	        // Atualiza as informações do paciente com base nos dados fornecidos no formulário
	        pacienteExistente.setNome(usuario.getNome());
	        pacienteExistente.setNascimento(usuario.getNascimento());
	        pacienteExistente.setTelefone(usuario.getTelefone());
	        pacienteExistente.setResponsavel(usuario.getResponsavel());
	        pacienteExistente.setTratamento(usuario.getTratamento());
	        pacienteExistente.setTrataFinal(usuario.getTrataFinal());
	        pacienteExistente.setEndereco(usuario.getEndereco());
	        pacienteExistente.setNumero(usuario.getNumero());
	        
	        // Salva as alterações no banco de dados
	        crudrepository.save(pacienteExistente);
	    } else {
	    	pacienteExistente=null;
	    }
	    
	    // Redireciona para a página de listagem de pacientes após a edição
	    return "redirect:/pacienteInfo/" + id;
	}*/
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	@PostMapping("/pacienteInfo2/{id}")
	public String editarPaciente(@PathVariable Long id, @ModelAttribute Usuario usuario) {
	    // Busca o paciente no banco de dados pelo ID
	    Usuario pacienteExistente = crudrepository.findById(id).orElse(null);

	    // Verifica se o paciente existe no banco de dados
	    if (pacienteExistente != null) {
	        // Atualiza as informações do paciente com base nos dados fornecidos no formulário
	        pacienteExistente.setNome(usuario.getNome());
	        pacienteExistente.setNascimento(usuario.getNascimento());
	        pacienteExistente.setTelefone(usuario.getTelefone());
	        pacienteExistente.setResponsavel(usuario.getResponsavel());
	        pacienteExistente.setTratamento(usuario.getTratamento());
	        pacienteExistente.setTrataFinal(usuario.getTrataFinal());
	        pacienteExistente.setEndereco(usuario.getEndereco());
	        pacienteExistente.setNumero(usuario.getNumero());

	        // Salva as alterações no banco de dados
	        crudrepository.save(pacienteExistente);
	    }

	    // Redireciona para a página de listagem de pacientes após a edição
	    return "redirect:/paciente";
	}
	
	 @GetMapping("/editarAnotacao/{id}")
	    public String showEditAnotacaoForm(@PathVariable Long id, Model model) {
	        Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
	        if (anotacao != null) {
	            model.addAttribute("anotacao", anotacao);
	            return "editarAnotacao"; // Nome da página de edição da anotação
	        } else {
	            return "redirect:/paciente"; // Redireciona se a anotação não for encontrada
	        }
	    }
	
	@PostMapping("/editarAnotacao/{id}")
    public String editarAnotacao(@PathVariable Long id, @ModelAttribute Anotacao anotacao) {
        Anotacao anotacaoExistente = anotacaoRepository.findById(id).orElse(null);
        if (anotacaoExistente != null) {
            anotacaoExistente.setConteudo(anotacao.getConteudo());
            anotacaoRepository.save(anotacaoExistente);
        }
        return "redirect:/paciente"; // Redireciona para a lista de pacientes após a edição
    }
	
	@GetMapping("/anotacaoExcluir/{id}")
    public String excluirAnotacao(@PathVariable Long id) {
        anotacaoRepository.deleteById(id);
        return "redirect:/paciente"; // Redireciona para a lista de pacientes após a exclusão
    }
	@GetMapping("/anotacao3/{id}")
    public String getAnotacao(@PathVariable Long id, Model model) {
        Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
        model.addAttribute("anotacao", anotacao);
        return "anotacaoDados"; // Retornar o nome da página de detalhes da anotação
    }
	@GetMapping("/excluirPaciente/{id}")
	public String excluirPaciente(@PathVariable Long id) {
	    // Lógica para excluir o paciente do banco de dados
	    servicos.deletar(id);
	    return "redirect:/paciente"; // Redireciona para a lista de pacientes após a exclusão
	}

	
}
