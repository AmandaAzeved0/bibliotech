import { Injectable } from '@angular/core';
import {environment} from "../../../environments/enironments";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SolicitacoesEmprestimoService {

  url = environment.apiUrl;
  constructor(
    private httpClient: HttpClient

  ) { }

  //abrir uma solicitação de empréstimo
  abrirSolicitacao(solicitacaoDeEmprestimo: any){
  //Integer livroId;
  //Integer quantidadeDias;

    return this.httpClient.post(this.url + 'solicitacaoDeEmprestimo', solicitacaoDeEmprestimo);
  }
  //atualizar uma solicitação de empréstimo
  atualizarSolicitacao(dados: any){

    return this.httpClient.put(this.url + 'solicitacaoDeEmprestimo' , dados);

  }


  getSolicitacoes() {
    return this.httpClient.get(this.url + 'solicitacaoDeEmprestimo/lista');
  }
}
