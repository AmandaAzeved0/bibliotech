import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EstoqueService} from "../../../services/estoque/estoque.service";
import {SnackbarService} from "../../../services/snackbar/snackbar.service";
import {LivrosService} from "../../../services/livros/livros.service";
import {SolicitacoesEmprestimoService} from "../../../services/solicitacaoDeEmprestimo/solicitacoes-emprestimo.service";
import {Globalconstants} from "../../shared/global-constants";

@Component({
  selector: 'app-solicitacoes-emprestimo',
  templateUrl: './solicitacoes-emprestimo.component.html',
  styleUrls: ['./solicitacoes-emprestimo.component.css']
})
export class SolicitacoesEmprestimoComponent implements OnInit{

  dataSource:any = MatTableDataSource<any> ;
  abrirSolicitacaoForm:any = FormGroup;
  genero :any;
  dias :number = 0;
  responseMessage :any;
  livrosDisponiveis: any =[];
  generos: any = [];

  constructor(
    private formBuilder: FormBuilder,
    private estoqueService: EstoqueService,
    private snackBar: SnackbarService,
    private livrosService: LivrosService,
    private solicitacoesEmprestimoService: SolicitacoesEmprestimoService
  ) { }

  ngOnInit(): void {
    this.getGeneros();
    this.abrirSolicitacaoForm = this.formBuilder.group({
      livro: [null,Validators.required],
      dias: [null,Validators.required]
    });
  }

  getGeneros() {
    this.estoqueService.getGeneros().subscribe((response:any) => {
      this.generos = response;
      console.log(this.generos);
    }, error => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }

  getLivrosDisponiveisPorGenero(value:any) {
   console.log(value);
    this.livrosService.getLivrosDisponiveisPorGenero(value).subscribe((response:any) => {
      this.livrosDisponiveis = response;
      console.log(this.livrosDisponiveis);
      this.dataSource = new MatTableDataSource(this.livrosDisponiveis);
    }, error => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }

  getLivroPorid(id:any) {
    this.livrosService.getLivroById(id).subscribe((response:any) => {
      return response;
    }, error => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }

  salvarNovaSolicitacao() {
    var formdata = this.abrirSolicitacaoForm.value;
    var dados = {
      livroId: formdata.livro.id,
      quantidadeDias: this.abrirSolicitacaoForm.get('dias').value
    }
    this.solicitacoesEmprestimoService.abrirSolicitacao(dados).subscribe((response:any) => {
      this.responseMessage = response.message;
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.success);
      this.abrirSolicitacaoForm.reset();
      window.location.reload();
    }, error => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }
}
