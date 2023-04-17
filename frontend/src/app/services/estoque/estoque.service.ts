import { Injectable } from '@angular/core';
import {environment} from "../../../environments/enironments";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class EstoqueService {
  url = environment.apiUrl;

  constructor(
    private httpClient: HttpClient
  ) { }

  getEstoque(){
    return this.httpClient.get(this.url + 'estoque');
  }

  excluirEstoque(id: number) {
    return this.httpClient.delete(this.url + 'estoque/delete/' + id);
  }

  getGeneros() {
    return this.httpClient.get(this.url + 'estoque/lista/generos');
  }
}
