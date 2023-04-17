import { TestBed } from '@angular/core/testing';

import { SolicitacoesEmprestimoService } from './solicitacoes-emprestimo.service';

describe('SolicitacoesEmprestimoService', () => {
  let service: SolicitacoesEmprestimoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolicitacoesEmprestimoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
