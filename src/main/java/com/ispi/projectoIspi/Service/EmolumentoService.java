/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.TipoEmolumento;
import com.ispi.projectoIspi.Repository.EmolumentoRepository;
import com.ispi.projectoIspi.model.Emolumento;
import com.ispi.projectoIspi.model.Matricula;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class EmolumentoService {

    @Autowired
    private EmolumentoRepository emolumentoRepository;

    //TRABALHANDO COM DATAS
    Calendar calendar = Calendar.getInstance();
    int anoAcademicoReferente = calendar.get(Calendar.YEAR);
    Date data = new Date();
    Locale local = new Locale("pt", "BR");
    DateFormat dfmt = new SimpleDateFormat("MMMM", local);
    String mesReferente = dfmt.format(data);

    public List<Emolumento> getAll() {
        return (List<Emolumento>) emolumentoRepository.findAll();
    }

    public Iterable<Emolumento> findByMatriculaAndTipoEmolumento(Matricula matricula, TipoEmolumento tipoEmolumento) {
        return emolumentoRepository.findByMatriculaAndTipoEmolumentoAndAnoAcademicoReferente(matricula, tipoEmolumento.TRANSPORTE, anoAcademicoReferente);

    }

    public Emolumento findByNumeroEstudanteAndMesReferente(String numeroEstudante) {
        Emolumento emolumento = emolumentoRepository.findByNumeroEstudante(numeroEstudante, anoAcademicoReferente, mesReferente);
        return emolumento; 

    }

    public Optional<Emolumento> findByCodigo(Long codigo) {
        return emolumentoRepository.findById(codigo);

    }

    public Optional<Emolumento> getOne(Long codigo) {
        return emolumentoRepository.findById(codigo);

    }

    public void addNew(Emolumento emolumento) {
        emolumentoRepository.save(emolumento);
    }

    public void update(Emolumento emolumento) {
        emolumentoRepository.save(emolumento);

    }

    public void delete(Long codigo) {
        emolumentoRepository.deleteById(codigo);

    }

}
