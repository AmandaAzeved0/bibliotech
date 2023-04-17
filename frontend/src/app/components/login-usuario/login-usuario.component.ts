import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UsuarioService} from "../../services/usuario/usuario.service";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {MatDialogRef} from "@angular/material/dialog";
import {Globalconstants} from "../shared/global-constants";

@Component({
  selector: 'app-login-usuario',
  templateUrl: './login-usuario.component.html',
  styleUrls: ['./login-usuario.component.css']
})
export class LoginUsuarioComponent implements OnInit {

    hide = true;
    loginForm: any = FormGroup;
    responseMessage: any;

  senhaVisivel: boolean = false;
    constructor(
      private formBuilder: FormBuilder,
      private router:Router,
      private usuarioService: UsuarioService<any>,
      private snackbarService: SnackbarService,
      public dialogRef: MatDialogRef<LoginUsuarioComponent>
    ) { }

    ngOnInit(): void {
      this.loginForm = this.formBuilder.group({
        email: [null, [Validators.required, Validators.email]],
        senha: [null,[Validators.required]]
      })
    }

    handleSubmit() {
      var dadosDeLogin = this.loginForm.value;
      var dados = {
        email: this.loginForm.value.email,
        senha: this.loginForm.value.senha
      }
      this.usuarioService.login(dados).subscribe((response: any) => {
        this.dialogRef.close();
        localStorage.setItem('token', response);
        this.router.navigate(['/dashboard']);

      }, (error) => {
        if (error?.error?.message) {
          this.responseMessage = error?.error?.message;
        } else {
          this.responseMessage = error?.message;
        }
        this.snackbarService.openSnackBar(this.responseMessage, Globalconstants.error);

      })
    }

  logar() {
      this.handleSubmit();
  }
}
