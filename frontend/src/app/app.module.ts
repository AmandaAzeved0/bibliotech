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
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CadastroUsuarioComponent} from "./components/cadastro-usuario/cadastro-usuario.component";
import { NgxMaskModule} from "ngx-mask";




@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    HomeComponent,
    FullComponent,
    AppHeaderComponent,
    AppSidebarComponent,
    CadastroUsuarioComponent
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
  providers: [],
})
export class AppModule { }
