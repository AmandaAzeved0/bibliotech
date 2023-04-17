import { Injectable } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from "@angular/router";
import {SnackbarService} from "../snackbar/snackbar.service";


@Injectable({
  providedIn: 'root'
})
export class RouteGaurdService {

  constructor(
    public auth: AuthService,
    public router: Router,
    private snackbarService: SnackbarService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: Router): boolean {
    let expectedRoleArray = route.data['expectedRole'];
    console.log(expectedRoleArray + " expectedRoleArray") //


    const token = localStorage.getItem('token');
    console.log(token + " token")
    var tokenPayload : any;
    var userAuthority : any;

    try{
      if (token !== null) {
        tokenPayload = JSON.parse(atob(token.split('.')[1]));
        userAuthority = tokenPayload.authorities[0].authority;
        console.log(tokenPayload.authorities[0].authority + " userAuthority!!!")
      }
    }catch(error){
      localStorage.clear();
      this.router.navigate(['/']);
    }
    let expectedRole ='';

    for(let i =0;i<expectedRoleArray.length;i++){
      if(expectedRoleArray[i] == userAuthority){
        expectedRole = userAuthority;
      }
    }

      if(expectedRoleArray === userAuthority) {
        expectedRole = userAuthority;
      }

    if(userAuthority == 'USR' || userAuthority == 'ADM'){
      if(this.auth.isAuthenticated() && expectedRole == userAuthority){
        return true;
      }
      this.snackbarService.openSnackBar('Você não tem permissão para acessar essa página', 'error');
      this.router.navigate(['bibliotech/dashboard']);
      return false;
    }else {
      this.router.navigate(['/']);
      localStorage.clear()
      return false;
    }
  }
}
