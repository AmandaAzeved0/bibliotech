import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UsuarioService} from "../../services/usuario.service";
import {SnackbarService} from "../../services/snackbar.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Globalconstants} from "../shared/global-constants";
import {AppComponent} from "../../app.component";


@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.scss']
})
export class CadastroUsuarioComponent {
  senha =  true;
  confirmaSenha = true;
  formCadastro: any = FormGroup;
  responseMessage: any;
  senhaVisivel = false;


  constructor(
    private formBuilder:FormBuilder ,
    private router:Router,
    private usuarioService: UsuarioService<any>,
    private snackbarService: SnackbarService,
    public dialogRef: MatDialogRef<CadastroUsuarioComponent>
  ) { }

  ngOnInit(): void {
    this.formCadastro = this.formBuilder.group({
      nome: [null, [Validators.required,Validators.pattern(Globalconstants.nameRegex)]],
      cpf : [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      senha: [null,[Validators.required]],
      confirmaSenha: [null,[Validators.required]]
    })
  }

  validaSunmit(){
      if(this.formCadastro.value.senha === this.formCadastro.value.confirmaSenha) {
        return true;
      }else {
        this.snackbarService.openSnapckBar('As senhas não conferem','error');
        return false;
      }
  }

  handleSubmit(){
    var dadosDeCadastro = this.formCadastro.value;
    var dados = {
      nome: 'dadosDeCadastro.nome',
      email: 'email@gmail.com',
      senha: '1234',
      cpf: '00108122298'
    }
    this.usuarioService.cadastrarUsuario(dados).subscribe((response:any) => {
      this.dialogRef.close();
      this.responseMessage = response?.message;
      this.snackbarService.openSnapckBar(this.responseMessage,"");
      this.router.navigate(['/']);
    })
  }


  cadastrar() {
    if(this.validaSunmit()){
      this.handleSubmit();
      }

  }

}

