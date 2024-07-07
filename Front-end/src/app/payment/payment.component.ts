import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { MatTableDataSource } from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  public payments: any;
  public dataSource: MatTableDataSource<any>;
  public displayedColumns: string[] = ['id', 'date', 'amount', 'type', 'status', 'firstName'];
  @ViewChild(MatPaginator) paginator !:MatPaginator;
  @ViewChild(MatSort) sort !:MatSort;

  constructor(private http: HttpClient) {
    this.dataSource = new MatTableDataSource<any>([]);
  }

  ngOnInit(): void {
    this.http.get("http://localhost:8282/payments")
      .subscribe(
        {
          next: data => {
            this.payments = data;
            this.dataSource = new MatTableDataSource<any>(this.payments);
            this.dataSource.paginator= this.paginator;
            this.dataSource.sort=this.sort;
          },
          error: err => {
            console.log(err);
          }
        }
      )
  }

}
