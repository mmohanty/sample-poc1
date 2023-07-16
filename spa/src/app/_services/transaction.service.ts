import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '@environments/environment';
import { User } from '@app/_models';
import { Transaction } from '@app/_models';

@Injectable({ providedIn: 'root' })
export class TransactionService {
    private userSubject: BehaviorSubject<User | null>;
    public user: Observable<User | null>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
        this.user = this.userSubject.asObservable();
    }

    public get userValue() {
        return this.userSubject.value;
    }


    // getAll() {
    //     return this.http.get<Transaction[]>(`${environment.apiUrl}/users`);
    // }

    getAll() {
        return this.http.get<Transaction[]>(`${environment.apiUrl}/transaction/${this.userSubject.value?.username}`);
    }



}