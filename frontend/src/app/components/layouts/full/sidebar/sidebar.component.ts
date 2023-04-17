import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import {MenuItems} from "../../../shared/menuItems";
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css' ]
})
export class AppSidebarComponent implements OnDestroy {
  mobileQuery: MediaQueryList;
  perfilUsuario:any;

  token:any = localStorage.getItem('token');
  tokenPayload : any;

  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    public menuItems: MenuItems

  ) {
    this.tokenPayload = JSON.parse(atob(this.token.split('.')[1]));
    this.perfilUsuario = this.tokenPayload.authorities[0].authority;
    this.mobileQuery = media.matchMedia('(min-width: 768px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
}
