package com.qualificador.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.qualificador.Enum.Genero;
import com.qualificador.validadores.AtributoConfirmacaoSenha;
import com.qualificador.validadores.Bi;
import com.qualificador.validadores.Strings;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author PEDRO P MULENGA
 */
@AtributoConfirmacaoSenha(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "As senhas não são iguais")
@Entity
@DynamicUpdate
public class Usuario extends GenericDomin {

    @Strings
    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @Strings
    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo sobrenome é obrigatório!")
    private String sobrenome;
    @Column(unique = true)
    @NotBlank(message = "O E-mail é obrigatório!")
    @Email(message = "E-mail inválido")
    private String email;
    @Bi
    @Column(length = 16, nullable = true)
    private String bi;
    @Size(min = 6, message = "A senha deve conter pelo menos 6 caractter!")
    private String senha;
    @Transient
    private String confirmacaoSenha;
    @NotNull(message = "Digite a data de Nascimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    @Column(length = 10)
    @NotNull(message = "O campo telefone é obrigatório!")
    private Integer telefone;
    private String nacionalidade;
    private String provincia;
    private String municipio;
    private String bairro;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O Género é obrigatório!")
    private Genero genero;
    @Temporal(TemporalType.DATE)
    private Date dataRegisto = new Date();
    @Transient
    private String estadoFormato;
    @Column(nullable = false)
    private boolean estado = true;
    @Size(min = 1, message = "Selecione o tipo de Usuário")
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"),
            inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
    private List<Grupo> grupos;
    private String nomeImagen;
    @Column(length = 45, name = "reset_password_token")
    private String resetPasswordToken;
    @Column(length = 2555555)
    private String sobreMim;
    private String profissao;

    @PreUpdate
    private void preUpdate() {
        this.confirmacaoSenha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isNovo() {
        return codigo == null;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEstadoFormato() {
        estadoFormato = "Inactivo";
        if (estado == true) {
            estadoFormato = "Activo";
        }
        return estadoFormato;
    }

    public void setEstadoFormato(String estadoFormato) {
        this.estadoFormato = estadoFormato;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getNomeImagen() {
        return nomeImagen;
    }

    public void setNomeImagen(String nomeImagen) {
        this.nomeImagen = nomeImagen;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi.toUpperCase();
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getSobreMim() {
        return sobreMim;
    }

    public void setSobreMim(String sobreMim) {
        this.sobreMim = sobreMim;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
    
    

}
