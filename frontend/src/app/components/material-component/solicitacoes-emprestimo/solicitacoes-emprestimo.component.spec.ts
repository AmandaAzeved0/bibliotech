import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitacoesEmprestimoComponent } from './solicitacoes-emprestimo.component';

describe('SolicitacoesEmprestimoComponent', () => {
  let component: SolicitacoesEmprestimoComponent;
  let fixture: ComponentFixture<SolicitacoesEmprestimoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolicitacoesEmprestimoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SolicitacoesEmprestimoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
