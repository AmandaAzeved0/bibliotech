package br.com.techlead.service;

import br.com.techlead.domain.TiposDePenalidade;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.repository.TiposDePenalidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TiposDePenalidadeService {
    private final TiposDePenalidadeRepository repository;

    public TiposDePenalidade findByDescricao(String descricao) {
        return repository.findByDescricao(descricao).orElseThrow(()-> new BadRequestException("Tipo de penalidade não encontrado"));
    }

}