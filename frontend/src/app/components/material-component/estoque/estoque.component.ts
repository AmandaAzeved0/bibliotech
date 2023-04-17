import {Component, OnInit} from '@angular/core';
import {EstoqueService} from "../../../services/estoque/estoque.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar/snackbar.service";
import {Router} from "@angular/router";
import {Globalconstants} from "../../shared/global-constants";
import {ConfirmationComponent} from "../dialog/confirmation/confirmation.component";


@Component({
  selector: 'app-estoque',
  templateUrl: './estoque.component.html',
  styleUrls: ['./estoque.component.css']
})
export class EstoqueComponent implements OnInit{

  displayedColumns: string[] = ['titulo', 'autor', 'genero', 'quantidadeTotal', 'quantidadeDisponivel',  'acoes'];
  dataSource:any = MatTableDataSource<any> ;

  responseMessage:any;

  constructor(
    private estoqueService: EstoqueService,
    private dialog: MatDialog,
    private snackBar: SnackbarService,
    private  router: Router
  ) {}

    ngOnInit(): void {
    this.tableData();
  }

  private tableData() {

    this.estoqueService.getEstoque().subscribe((response:any) => {
      this.dataSource = new MatTableDataSource(response)
      console.log(this.dataSource + " data source")

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

  handleAdicionaEstoque() {
    return 'ok'
  }

  handleExcluirEstoque(id: number) {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = {
      message : 'excluir todos os livros desse estoque?',
      confirmation: true
    };

    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((result: any) => {
      this.excluirEstoque(id);
       dialogRef.close();
      window.location.reload()
    });
  }

  private excluirEstoque(id: number) {
    this.estoqueService.excluirEstoque(id).subscribe((response:any) => {
      this.snackBar.openSnackBar(response.message, Globalconstants.success);
      this.tableData();

    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMessage = error.error?.message}
      else {this.responseMessage = Globalconstants.genericError}
      this.snackBar.openSnackBar(this.responseMessage, Globalconstants.error);
    });
  }

  listarLivros(id: number) {
    this.router.navigate(['/estoque/livros', id, 'livros']);
  }

}


