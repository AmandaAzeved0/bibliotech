import { Injectable } from '@angular/core';
import {environment} from "../../environments/enironments";
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

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Access-Control-Allow-Origin', 'http://localhost:4200');

    return this.httpClient.post<s>(this.url + 'cadastro', data);
  }
}

