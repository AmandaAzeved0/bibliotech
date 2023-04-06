package br.com.techlead.service;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.PenalidadeUsuario;
import br.com.techlead.domain.TiposDePenalidade;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.PenalidadeUsuarioDto;
import br.com.techlead.enums.TipoDePenalidadeEnum;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.mapper.penalidadeUsuarioMapper.PenalidadeUsuarioMapper;
import br.com.techlead.repository.PenalidadeUsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PenalidadeUsuarioService {
    private final PenalidadeUsuarioRepository repository;
    private final TiposDePenalidadeService tiposDePenalidadeService;

    private final UsuarioService usuarioService;

    public void save(PenalidadeUsuarioDto penalidadeUsuarioDto) {
        PenalidadeUsuario entity = PenalidadeUsuarioMapper.INSTANCE.toEntity(penalidadeUsuarioDto);
        repository.save(entity);
    }

    public void aplicaPenalidadeAtraso(Emprestimo emprestimo) {
        Integer diasAtraso = this.calculaDiasDeAtraso(emprestimo);
        TiposDePenalidade tiposDePenalidade = this.tipoPenalidadePorDiasDeAtraso(diasAtraso);
        PenalidadeUsuarioDto penalidadeUsuarioDto = PenalidadeUsuarioDto.builder()
                .usuario(emprestimo.getUsuario())
                .dataPenalidade(emprestimo.getDataDevolucao())
                .dataPenalidadeFim(emprestimo.getDataDevolucao().plusDays(tiposDePenalidade.getDiasDePenalidade()))
                .status(true)
                .build();


        penalidadeUsuarioDto.setTipoDePenalidade(tiposDePenalidade);
        this.save(penalidadeUsuarioDto);
    }

    public void aplicaPenalidadePerdaOuDano(Emprestimo emprestimo){
        String descricaoPenalidade = TipoDePenalidadeEnum.PERDA_OU_DANO.getDescricao();
        TiposDePenalidade tipoDePenalidade = tiposDePenalidadeService.findByDescricao(descricaoPenalidade);
        PenalidadeUsuarioDto penalidadeUsuarioDto = PenalidadeUsuarioDto.builder()
                .usuario(emprestimo.getUsuario())
                .dataPenalidade(emprestimo.getDataDevolucao())
                .tipoDePenalidade(tipoDePenalidade)
                .dataPenalidadeFim(emprestimo.getDataDevolucao().plusDays(tipoDePenalidade.getDiasDePenalidade()))
                .dataDeBloqueioFim(emprestimo.getDataDevolucao().plusDays(7))
                .status(true)
                .build();
        this.save(penalidadeUsuarioDto);
        usuarioService.bloqueiaUsuario(emprestimo.getUsuario().getId());
    }

//    private TiposDePenalidade tipoPenalidadePorDiasDeAtraso(Integer diasAtraso) {
//        String descricaoPenalidade = "";
//        if (diasAtraso < 10 ) {
//           descricaoPenalidade = TipoDePenalidadeEnum.INFERIOR_A_DEZ_DIAS.getDescricao();
//        }else if (diasAtraso > 10 && diasAtraso < 20) {
//            descricaoPenalidade = TipoDePenalidadeEnum.SUPERIOR_A_DEZ_DIAS.getDescricao();
//        }else if (diasAtraso > 20 && diasAtraso < 30) {
//            descricaoPenalidade = TipoDePenalidadeEnum.SUPERIOR_A_VINTE_DIAS.getDescricao();
//        }else if (diasAtraso > 30) {
//            descricaoPenalidade = TipoDePenalidadeEnum.SUPERIOR_A_TRINTA_DIAS.getDescricao();
//        }else if (diasAtraso > 40) {
//            descricaoPenalidade = TipoDePenalidadeEnum.SUPERIOR_A_QUARENTA_DIAS.getDescricao();
//        }
//        return tiposDePenalidadeService.findByDescricao(descricaoPenalidade);
//    }

    private TiposDePenalidade tipoPenalidadePorDiasDeAtraso(Integer diasAtraso) {
        TipoDePenalidadeEnum tipoPenalidade = TipoDePenalidadeEnum.obterPenalidadePorDiasDeAtraso(diasAtraso);
        return tiposDePenalidadeService.findByDescricao(tipoPenalidade.getDescricao());
    }


    private Integer calculaDiasDeAtraso(Emprestimo emprestimo) {
        return emprestimo.getDataDevolucao().getDayOfYear() - emprestimo.getDataEmprestimoFim().getDayOfYear();
    }

    public void verificaUsuarioPenalizado(Usuario usuario) {
        if (this.existsByUsuario(usuario)) {
            throw new BadRequestException("Usuário com penalidade ativa");
        }
    }

    public boolean existsByUsuario(Usuario usuario) {
        return repository.existsByUsuario(usuario);
    }
}