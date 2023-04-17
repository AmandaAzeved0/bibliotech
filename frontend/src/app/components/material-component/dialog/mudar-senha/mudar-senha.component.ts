import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UsuarioService} from "../../../../services/usuario/usuario.service";
import {MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../../services/snackbar/snackbar.service";
import {Globalconstants} from "../../../shared/global-constants";

@Component({
  selector: 'app-mudar-senha',
  templateUrl: './mudar-senha.component.html',
  styleUrls: ['./mudar-senha.component.css']
})
export class MudarSenhaComponent implements OnInit{

  senhaAtual = true;
  senhaNova = true;
  senhaNovaConfirmacao:any = true;
  mudarSenhaForm: any = FormGroup;
  responseMessage:any;

    constructor(
      private formBuilder: FormBuilder,
      private usuarioService: UsuarioService<any>,
      public dialogRef: MatDialogRef<MudarSenhaComponent>,

      private snackbar: SnackbarService

    ) {}

    ngOnInit() {
      this.mudarSenhaForm = this.formBuilder.group({
        senhaAtual: [null, Validators.required],
        senhaNova: [null, Validators.required],
        senhaNovaConfirmacao: [null, Validators.required]
      });
    }

    validaSubmit(){
      if(this.mudarSenhaForm.controls['novaSenha'].value != this.mudarSenhaForm.controls['novaSenhaConfirmacao'].value){
        return true;
      }
      return false;
    }

    handleMudarSenhaSubmit(){
      var formData = this.mudarSenhaForm.value;
      var data = {
        "senhaAtual": formData.senhaAtual,
        "senhaNova": formData.senhaNova,
        "senhaNovaConfirmacao": formData.senhaNovaConfirmacao
      }
      this.usuarioService.mudarSenha(data).subscribe((response:any) => {
        this.responseMessage = response?.message;
        this.dialogRef.close();
        this.snackbar.openSnackBar(this.responseMessage, 'success');
      }, (error:any) => {
        console.log(error.error?.message);
        if(error.error?.message){this.responseMessage = error.error?.message}
        else {this.responseMessage = Globalconstants.genericError}
        this.snackbar.openSnackBar(this.responseMessage, Globalconstants.error);
      });
    }

}
