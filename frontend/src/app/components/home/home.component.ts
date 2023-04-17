import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CadastroUsuarioComponent} from "../cadastro-usuario/cadastro-usuario.component";
import {LoginUsuarioComponent} from "../login-usuario/login-usuario.component";
import {UsuarioService} from "../../services/usuario/usuario.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private usuarioService: UsuarioService<any>,

private router: Router

  ) { }

  ngOnInit(): void {
      this.router.navigate(['bibliotech/dashboard']);
  }

  handleCadastro(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(CadastroUsuarioComponent, dialogConfig);
  }

  handleResetSenha(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(CadastroUsuarioComponent, dialogConfig);
  }

  handleLogin(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(LoginUsuarioComponent, dialogConfig);
  }

}
