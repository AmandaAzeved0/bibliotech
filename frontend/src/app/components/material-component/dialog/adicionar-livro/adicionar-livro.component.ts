import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LivrosComponent} from "../../livros/livros/livros.component";
import {LivrosService} from "../../../../services/livros/livros.service";
import {EstoqueService} from "../../../../services/estoque/estoque.service";
import {SnackbarService} from "../../../../services/snackbar/snackbar.service";
import {ConfirmationComponent} from "../confirmation/confirmation.component";
import {Globalconstants} from "../../../shared/global-constants";
import {Router} from "@angular/router";



@Component({
  selector: 'app-adicionar-livro',
  templateUrl: './adicionar-livro.component.html',
  styleUrls: ['./adicionar-livro.component.css']
})
export class AdicionarLivroComponent implements OnInit {
  onAddLivro = new EventEmitter();
  onEditLivro = new EventEmitter();
  livroForm: any = FormGroup;
  dialogAction:any = 'add';
  action:any = 'Adicionar';

  responseMeassage:any;


  details: any = {};


  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    private livroService: LivrosService,
    private dialogRef: MatDialogRef<AdicionarLivroComponent>,
    private dialog: MatDialog,

    private snackBar: SnackbarService,
    private  router: Router,
    private estoqueService: EstoqueService,

    private snackbar: SnackbarService

  ) { }

  ngOnInit(): void {
    this.livroForm = this.formBuilder.group({
      titulo: [null,Validators.required],
      genero: [null,Validators.required],
      autor: [null,Validators.required],
      estado: [null,Validators.required],
      quantidade: [null,Validators.required]
    });

    if(this.dialogData.action === 'edit'){
      this.action = 'Editar';
     this.livroForm.patchValue(this.dialogData);

    }
  }


  handleAddLivro() {
    var formData = this.livroForm.value;
    var data = {
      titulo: formData.titulo,
      genero: formData.genero,
      autor: formData.autor,
      estado: formData.estado,
      quantidade: formData.quantidade
    }

    this.livroService.adicionarLivros(data).subscribe((response:any) => {
      this.dialogRef.close();
      this.responseMeassage = response.message;
      this.snackbar.openSnackBar(this.responseMeassage, 'success');
      this.onAddLivro.emit();
    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMeassage = error.error?.message}
      else {this.responseMeassage = 'Erro ao adicionar livro'}
      this.snackbar.openSnackBar(this.responseMeassage, 'error');
    });
  }

  private handleEditLivro() {
    var formData = this.livroForm.value;
    var id = this.dialogData.data.id;
    var data = {
      titulo: formData.titulo,
      genero: formData.genero,
      autor: formData.autor,
      estado: formData.estado,
      quantidade: formData.quantidade
    }

    this.livroService.editarLivro(data, id).subscribe((response:any) => {
      this.dialogRef.close();
      this.responseMeassage = response.message;
      this.snackbar.openSnackBar(this.responseMeassage, 'success');
      this.onAddLivro.emit();
    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMeassage = error.error?.message}
      else {this.responseMeassage = 'Erro ao adicionar livro'}
      this.snackbar.openSnackBar(this.responseMeassage, 'error');
    });
  }

  private handleExcluirLivros() {
    const dialogConfig = new MatDialogConfig();
    var id = this.dialogData.data.id
    dialogConfig.data = {
      message : 'excluir esse livro?',
      confirmation: true
    };

    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((result: any) => {
      this.excluirLivro(id);
      dialogRef.close();
    });
  }

  private excluirLivro(id: number) {
    this.livroService.excluirLivro(id).subscribe((response:any) => {
      this.snackBar.openSnackBar(response.message, Globalconstants.success);
      this.router.navigate(['/livros'])
    }, (error:any) => {
      console.log(error.error?.message);
      if(error.error?.message){this.responseMeassage = error.error?.message}
      else {this.responseMeassage = 'Erro ao editar livro'}
      this.snackbar.openSnackBar(this.responseMeassage, 'error');
    });
  }

  handleSubmit(){
    if(this.dialogAction === 'add'){
      this.handleAddLivro();
    }else if (this.dialogAction === 'edit'){
      this.handleEditLivro();
    }else {
      this.handleExcluirLivros()
    }
  }

}
