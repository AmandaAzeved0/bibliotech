import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {EstoqueService} from "../../../../services/estoque/estoque.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {SnackbarService} from "../../../../services/snackbar/snackbar.service";
import {Router} from "@angular/router";
import {Globalconstants} from "../../../shared/global-constants";
import {LivrosService} from "../../../../services/livros/livros.service";
import {AdicionarLivroComponent} from "../../dialog/adicionar-livro/adicionar-livro.component";
import {ConfirmationComponent} from "../../dialog/confirmation/confirmation.component";

@Component({
  selector: 'app-livros',
  templateUrl: './livros.component.html',
  styleUrls: ['./livros.component.css']
})
export class LivrosComponent implements OnInit {

  displayedColumns: string[] = ['titulo', 'autor', 'genero', 'estado', 'disponivel',  'acoes'];
  dataSource:any = MatTableDataSource<any> ;

  responseMessage:any;

  constructor(
    private livroService: LivrosService,
    private dialog: MatDialog,
    private snackBar: SnackbarService,
    private  router: Router
  ) {}

  ngOnInit(): void {
    this.tableData();
  }

  private tableData() {

    this.livroService.getLivros().subscribe((response:any) => {
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


  handleAdicionarLivros() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action:'add'
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(AdicionarLivroComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();

    });


    const sub = dialogRef.componentInstance.onAddLivro.subscribe((response) => {
      window.location.reload()
    });

  }


  handleEditarLivro(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action:'edit',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(AdicionarLivroComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });


    const sub = dialogRef.componentInstance.onEditLivro.subscribe((response) => {
      this.tableData();
    });

  }

  handleExcluirLivro(id: any) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = {
      message : 'excluir esse livros?',
      confirmation: true
    };

    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((result: any) => {
      this.excluirLivro(id);
      dialogRef.close();
      // this.tableData();
      window.location.reload()
    });

  }

  private excluirLivro(id: number) {
    this.livroService.excluirLivro(id).subscribe((response:any) => {
      this.snackBar.openSnackBar(response.message, Globalconstants.success);
      this.tableData();
    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }


}
