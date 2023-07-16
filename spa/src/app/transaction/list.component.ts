import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { TransactionService } from '@app/_services';
import { Transaction } from '@app/_models';

@Component({ templateUrl: 'list.component.html' })
export class ListComponent implements OnInit {
    transactions?: Transaction[];

    constructor(private transactionService: TransactionService) {}

    ngOnInit() {
        this.transactionService.getAll()
            .pipe(first())
            .subscribe(transactions => this.transactions = transactions);
    }

}