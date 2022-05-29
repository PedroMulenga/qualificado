/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import com.qualificador.ExceptionMessages.DataInvalidaException;
import com.qualificador.ExceptionMessages.EmailUsuarioExistenteException;
import com.qualificador.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.qualificador.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.qualificador.Repository.GrupoRepository;
import com.qualificador.Service.CertificacaoService;
import com.qualificador.Service.ExperienciaProfissionalService;
import com.qualificador.Service.FormacaoAcademicaService;
import com.qualificador.Service.UsuarioService;
import com.qualificador.helper.UsuariosImpl;
import com.qualificador.model.Grupo;
import com.qualificador.model.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class CriarContaUsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoRepository grupoRepository;
    private List<Grupo> grupos = new ArrayList<>();
    private Optional<Usuario> usuarioSistema;
    private static String caminhoImagem = "C:/ISPI_PAYMENT/imagens/usuarios/";
    @Autowired
    private UsuariosImpl usuariosQueries;

    @Autowired
    private FormacaoAcademicaService formacaoAcademicaService;
    @Autowired
    private ExperienciaProfissionalService experienciaProfissionalService;
    @Autowired
    private CertificacaoService certificacaoService;

    @GetMapping("/inicio")
    public ModelAndView index() {
        return new ModelAndView("inicio");
    }

    @GetMapping("/paginaPrincipal")
    public ModelAndView paginaPrincial() {
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal User user, Usuario usuario, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView();
        //mv.addObject("grupo", grupoRepository.findByNomeIgnoreCase("Usuario"));
        if (user != null) {
            return new ModelAndView("redirect:/paginaPrincipal");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/recrutamentos/listarUsuarios")
    public ModelAndView listarCandidatos() {
        ModelAndView mv = new ModelAndView("security/quadro");
        mv.addObject("listaDeUsuarios", usuarioService.findAllQuadros());

        return mv;
    }

    @GetMapping("/usuarios/listarTodosUsuarios")
    public ModelAndView listrUsuarios(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/listaUsuarios");
        mv.addObject("listaDeUsuarios", usuarioService.getAll());
        return mv;
    }

    @PostMapping(value = "**/recrutamentos/buscarQuadro/")
    public ModelAndView search(@RequestParam("profissao") String profissao) {
        ModelAndView mv = new ModelAndView("security/quadro");
        mv.addObject("listaDeUsuarios", usuarioService.finByProfissao(profissao));
        return mv;
    }

    @GetMapping("/recrutamentos/perfilCandidato/{codigo}")
    public ModelAndView perfilCandidato(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/perilCandidato");
        Optional<Usuario> usuario = usuarioService.getOne(codigo);
        //mv.addObject("grupos", grupos = usuarioSistema.get().getGrupos());
        mv.addObject("listaDeUsuarios", usuario.get());
        mv.addObject("listaFormacoesProfissionais", formacaoAcademicaService.findByUsuario(usuario.get()));
        mv.addObject("listaDeExperieciasProfissionais", experienciaProfissionalService.findByUsuario(usuario.get()));
        mv.addObject("listaDeCertificacoes", certificacaoService.findByUsuario(usuario.get()));
        return mv;

    }

    @GetMapping("/criarContaUsuarios")
    public ModelAndView novaContaUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("crateAcount");
        mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @PostMapping("/criarContaUsuarios")
    public ModelAndView criarContaUsuarios(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novaContaUsuario(usuario);
        }
        try {
            usuario.setGrupos(usuario.getGrupos());
            usuarioService.addNew(usuario);
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        }
        attribute.addFlashAttribute("success", "Conta Criada com sucesso! Faça Login");
        return new ModelAndView("redirect:/criarContaUsuarios");
    }

    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }

    @GetMapping(value = "/usuarios/eliminarUsuario/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        usuarioService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    //CARREGANDO O PERFIL DO USUÁRIO
    @GetMapping("/listarPerfil")
    public ModelAndView listaUsuarios(@AuthenticationPrincipal User user, Usuario usuario) {
        ModelAndView mv = new ModelAndView("profile");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("listaDeUsuarios", usuarioSistema.get());
        mv.addObject("listaFormacoesProfissionais", formacaoAcademicaService.findByUsuario(usuarioSistema.get()));
        mv.addObject("listaDeExperieciasProfissionais", experienciaProfissionalService.findByUsuario(usuarioSistema.get()));
        mv.addObject("listaDeCertificacoes", certificacaoService.findByUsuario(usuarioSistema.get()));
        return mv;
    }
    //EDITANDO O PERFIL DO USUÁRIO ESPECÍFICO

    @GetMapping("/editarPerfil/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/editarPerfil");
        Optional<Usuario> usuario = usuarioService.getOne(codigo);
        mv.addObject("grupos", grupoRepository.findAll());
        mv.addObject("usuario", usuario.get());
        return mv;

    }

    @GetMapping("/atualizarConta")
    public ModelAndView atualizarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/editarPerfil");
        //mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @PostMapping("/atualizarConta")
    public ModelAndView atualizarContaUsuarios(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attribute, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return atualizarUsuario(usuario);
        }
        try {
            attribute.addFlashAttribute("success", "Seus dados foram Actualizados com sucesso!");
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                Path caminho = Paths.get(caminhoImagem + String.valueOf(usuario.getCodigo()) + multipartFile.getOriginalFilename());
                Files.write(caminho, bytes);
                usuario.setNomeImagen(String.valueOf(usuario.getCodigo()) + multipartFile.getOriginalFilename());
            }
            usuario.setGrupos(usuario.getGrupos());;
            usuarioService.addNew(usuario);

        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/listarPerfil");
    }

    @GetMapping("/mostrarImagemUsuario/{imagem}")
    @ResponseBody
    public byte[] carregarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File("C:/ISPI_PAYMENT/imagens/usuarios/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }

    @GetMapping("/usuarios/cadastroUsuarios")
    public ModelAndView novoUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/usuario");
        mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @GetMapping("/usuarios/editarUsuario/{codigo}")
    public ModelAndView editarUsuario(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/usuario");
        Usuario usuario = usuariosQueries.buscarComGrupos(codigo);
        mv.addObject("grupos", grupoRepository.findAll());
        mv.addObject("usuario", usuario);
        return mv;

    }

    @PostMapping("/usuarios/cadastroUsuarios")
    public ModelAndView cadastroUsuarios(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attribute, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return novoUsuario(usuario);
        }
        try {
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                Path caminho = Paths.get(caminhoImagem + String.valueOf(usuario.getCodigo()) + multipartFile.getOriginalFilename());
                Files.write(caminho, bytes);
                usuario.setNomeImagen(String.valueOf(usuario.getCodigo()) + multipartFile.getOriginalFilename());
            }
            usuario.setGrupos(usuario.getGrupos());
            usuarioService.addNew(usuario);
            attribute.addFlashAttribute("success", "Usuário salvo com sucesso!");
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        }

        return new ModelAndView("redirect:/usuarios/cadastroUsuarios");
    }

}
