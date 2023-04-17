import { Injectable } from '@angular/core';
import {environment} from "../../../environments/enironments";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService<s> {
  url= environment.apiUrl;

  constructor(protected httpClient: HttpClient) {
    // super(httpClient);
  }

// @ts-ignore
  cadastrarUsuario(data: s):observable<s>{
    return this.httpClient.post<s>(this.url + 'cadastro/cliente', data);
  }

  login(data:s){
    return this.httpClient.post<s>(this.url + 'usuarios/autenticar', data);
  }

  verificaUsuario(data: s){
    // aqui retorna um token  para authenticar e mudar a senha
    return this.httpClient.post<s>(this.url + 'usuario/check-usuario', data);
  }

  resetSenha(data: s){
    return this.httpClient.post<s>(this.url + 'usuario/reset-senha', data);
  }

  mudarSenha(data: { senhaAtual: any; senhaNova: any; senhaNovaConfirmacao: any }) {
    return this.httpClient.post<s>(this.url + 'usuario/mudar-senha', data);
  }
}

