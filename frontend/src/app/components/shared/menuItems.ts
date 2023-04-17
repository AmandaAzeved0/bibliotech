import {Injectable} from "@angular/core";

export interface Menu{
  state: string;
  name: string;
  type: string;
  icon: string;
  perfil: string;
}

const MENUITEMS = [
  {state: 'dashboard',name:'Dashboard',type: 'link',icon: 'dashboard',perfil: ''},
  {state: 'estoque',name:'Estoque',type: 'link',icon: 'category',perfil: 'ADM'},
  {state: 'livros',name:'Livros',type: 'link',icon: 'inventory_2',perfil: 'ADM'},
  {state: 'solicitacoes',name:'Solicitações de Empréstimo',type: 'link',icon: 'shopping_basket',perfil: ''},
  {state: '/gerir/solicitacoes',name:'Gerenciar Solicitações de Empréstimo',type: 'link',icon: 'assignment',perfil: ''}

]

@Injectable()
export class MenuItems {
  getAll(): Menu[] {
    return MENUITEMS;
  }
  add(menu: Menu) {
    MENUITEMS.push(menu);
  }
}
