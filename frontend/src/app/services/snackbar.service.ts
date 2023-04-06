import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  constructor(private snackbar:MatSnackBar) {}
    openSnapckBar(message:string,action:string){
      if(action === 'error'){
          this.snackbar.open(message,'',{
            duration: 2000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
            panelClass: ['error']
          })
      } else {
            this.snackbar.open(message,'',{
              duration: 2000,
              panelClass: ['success']
            })
      }
  }
}
