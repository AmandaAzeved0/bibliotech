import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ConfirmationComponent} from "../../../material-component/dialog/confirmation/confirmation.component";
import {MudarSenhaComponent} from "../../../material-component/dialog/mudar-senha/mudar-senha.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class AppHeaderComponent {

  role: any;
  constructor(
    private router: Router,
    private dialog: MatDialog
  ) {}

  logOut() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message : 'sair do sistema?',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((result: any) => {
      dialogRef.close();
      localStorage.clear();
      this.router.navigate(['/']);
    });
  }

  mudarSenha(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '500px';
    this.dialog.open(MudarSenhaComponent, dialogConfig);
  }
}
