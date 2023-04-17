import { Injectable } from '@angular/core';
import {environment} from "../../../environments/enironments";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LivrosService {

  url = environment.apiUrl;

  constructor(
    private httpClient: HttpClient
  ) { }

  adicionarLivros(livros: any){
    return this.httpClient.post(this.url + 'livro', livros);
  }

  editarLivro(livro: any, id: number){
    return this.httpClient.put(this.url + 'livro/' + id, livro);
  }
  getLivros(){
    return this.httpClient.get(this.url + 'livro/lista');
  }
  excluirLivro(id: number){
    return this.httpClient.delete(this.url + 'livro/' + id);
  }
  excluirTodosOsLivros(){
    return this.httpClient.delete(this.url + 'livro/delete/all');
  }

  listarLivrosPorEstoque(estoqueId: number){
    return this.httpClient.get(this.url + 'livro/lista/' + estoqueId);
  }

  getLivroById(id: number){
    return this.httpClient.get(this.url + 'livro/' + id);
  }

  getLivrosDisponiveisPorGenero(genero:string) {
    return this.httpClient.get(this.url + 'livro/lista/livros/' + genero);
  }


}
