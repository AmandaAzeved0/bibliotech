import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarSolicitacoesEmprestimoComponent } from './gerenciar-solicitacoes-emprestimo.component';

describe('GerenciarSolicitacoesEmprestimoComponent', () => {
  let component: GerenciarSolicitacoesEmprestimoComponent;
  let fixture: ComponentFixture<GerenciarSolicitacoesEmprestimoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GerenciarSolicitacoesEmprestimoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GerenciarSolicitacoesEmprestimoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
