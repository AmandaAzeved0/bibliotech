import { Component, AfterViewInit } from '@angular/core';
import {DashboardService} from "../../services/dashboard/dashboard.service";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit {

  responseMassage: any ;
  data: any;
	ngAfterViewInit() { }

	constructor(
    private dashboardService: DashboardService,
    private snackbarService: SnackbarService) {

    this.dashboardData();
	}

  dashboardData(){
    this.dashboardService.getDetalais().subscribe((response: any) => {
      this.data = response;
      console.log(response + " response ")
    }, error => {
      this.snackbarService.openSnackBar('Erro ao carregar dados do dashboard', 'error');
    });
  }
}
