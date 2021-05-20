/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Iterable<Matricula> findByTurma(Turma turma);

    public Matricula findByNumeroEstudanteAndSituacao(String numeroEstudante, SituacaoMatricula situacao);
    //public Set<Matricula> findByNumeroEstudante(String numeroEstudante);
    @Query("SELECT m FROM Matricula m JOIN m.aluno a WHERE a.bi= :bi AND m.numeroEstudante= :numeroEstudante AND m.situacao='MATRICULADO'")
    public Matricula findByBiEstudanteAndSituacao(@Param("bi") String bi, @Param("numeroEstudante") String numeroEstudante);
    public Optional<Matricula> findByNumeroEstudante(String numeroEstudante);
    public Optional<Matricula> findByAluno(Aluno aluno);

}
