import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from "./app-routing.module";
import {HomeComponent} from "./components/home/home.component";
import {FullComponent} from "./components/layouts/full/full.component";


import {AppSidebarComponent} from "./components/layouts/full/sidebar/sidebar.component";
import {AppHeaderComponent} from "./components/layouts/full/header/header.component";
import {MaterialModule} from "./components/shared/material-module";
import {SharedModule} from "./components/shared/shared.module";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {CadastroUsuarioComponent} from "./components/cadastro-usuario/cadastro-usuario.component";
import { NgxMaskModule} from "ngx-mask";
import {LoginUsuarioComponent} from "./components/login-usuario/login-usuario.component";
import {TokenInterceptorInterceptor} from "./services/tokenInterceptor/token-interceptor.interceptor";
import {EstoqueComponent} from "./components/material-component/estoque/estoque.component";
import {LivrosComponent} from "./components/material-component/livros/livros/livros.component";
import {
  AdicionarLivroComponent
} from "./components/material-component/dialog/adicionar-livro/adicionar-livro.component";
import {
  SolicitacoesEmprestimoComponent
} from "./components/material-component/solicitacoes-emprestimo/solicitacoes-emprestimo.component";
import {
  GerenciarSolicitacoesEmprestimoComponent
} from "./components/material-component/gerenciar-solicitacoes-emprestimo/gerenciar-solicitacoes-emprestimo.component";




@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    HomeComponent,
    FullComponent,
    AppHeaderComponent,
    AppSidebarComponent,
    CadastroUsuarioComponent,
    LoginUsuarioComponent,
    EstoqueComponent,
    LivrosComponent,
    AdicionarLivroComponent,
    SolicitacoesEmprestimoComponent,
    GerenciarSolicitacoesEmprestimoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    FlexLayoutModule,
    SharedModule,
    HttpClientModule,
    NgxMaskModule.forRoot()
  ],
  providers: [HttpClientModule,{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorInterceptor, multi: true}],
})
export class AppModule { }
