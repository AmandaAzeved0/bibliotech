import {Routes} from '@angular/router';
import {EstoqueComponent} from "./estoque/estoque.component";
import {RouteGaurdService} from "../../services/routeGuard/route-gaurd.service";
import {LivrosComponent} from "./livros/livros/livros.component";
import {SolicitacoesEmprestimoComponent} from "./solicitacoes-emprestimo/solicitacoes-emprestimo.component";
import {
  GerenciarSolicitacoesEmprestimoComponent
} from "./gerenciar-solicitacoes-emprestimo/gerenciar-solicitacoes-emprestimo.component";


export const MaterialRoutes: Routes = [
  {
    path:'estoque',
    component: EstoqueComponent,
    canActivate:[RouteGaurdService],
    data: {
      expectedRole: ['ADM']}
  },
  {
    path:'livros',
    component: LivrosComponent,
    canActivate:[RouteGaurdService],
    data: {
      expectedRole: ['ADM']}
  },
  {
    path:'solicitacoes',
    component: SolicitacoesEmprestimoComponent,
    canActivate:[RouteGaurdService],
    data: {
      expectedRole: ['ADM', 'USR','BIB']}
  },
  {
    path:'gerir/solicitacoes',
    component: GerenciarSolicitacoesEmprestimoComponent,
    canActivate:[RouteGaurdService],
    data: {
      expectedRole: ['ADM', 'BIB']}
  }

];
