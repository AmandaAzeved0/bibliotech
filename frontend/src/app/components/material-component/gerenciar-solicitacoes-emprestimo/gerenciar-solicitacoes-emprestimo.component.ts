import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {FormGroup} from "@angular/forms";
import {LivrosService} from "../../../services/livros/livros.service";
import {MatDialog} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar/snackbar.service";
import {Router} from "@angular/router";
import {SolicitacoesEmprestimoService} from "../../../services/solicitacaoDeEmprestimo/solicitacoes-emprestimo.service";
import {Globalconstants} from "../../shared/global-constants";

@Component({
  selector: 'app-gerenciar-solicitacoes-emprestimo',
  templateUrl: './gerenciar-solicitacoes-emprestimo.component.html',
  styleUrls: ['./gerenciar-solicitacoes-emprestimo.component.css']
})
export class GerenciarSolicitacoesEmprestimoComponent implements OnInit {

  //classe para exibir as solicitacoes em aberto e aceitar ou recusar um solicitacao

  displayedColumns: string[] = [ 'data', 'usuario','titulo', 'dias','acoes'];
  dataSource:any = MatTableDataSource<any> ;
  responseMessage:any;

  constructor(
    private solicitacoesService: SolicitacoesEmprestimoService,
    private dialog: MatDialog,
    private snackBar: SnackbarService,
    private  router: Router
  ) { }

  ngOnInit(): void {
    this.tableData();
  }

  private tableData() {

    this.solicitacoesService.getSolicitacoes().subscribe((response:any) => {
      this.dataSource = new MatTableDataSource(response)

    }, (error:any) => {

      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);

    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //funcao para aceitar uma solicitacao

  atualizarSolicitacao(id: number, aceite: boolean){
    var dados =  {
      solicitacaoEmprestimoId: id,
      status: aceite ? 'APROVADO' : 'RECUSADO'
    }
    this.solicitacoesService.atualizarSolicitacao(dados).subscribe((response:any) => {
      this.snackBar.openSnackBar(response.message, Globalconstants.success);
      this.tableData();
      window.location.reload()
    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }
}
