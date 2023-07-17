import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '@environments/environment';
import { first, map } from 'rxjs';

import { MerchantInfo, TransactionRequest, TransactionResponse, OTPRequest } from '@app/_models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  userIP = ''
  merchant?: MerchantInfo;
  transactionReqest: TransactionRequest = new TransactionRequest;
  otpRequest: OTPRequest = new OTPRequest;
  transactionResponse: TransactionResponse = new TransactionResponse;
  title = 'sample-project';
  dummyForm!: FormGroup;
  transactionForm!: FormGroup;

  otpForm!: FormGroup;

  monthlist: any[] = [];
  //month: any = {};

  //year: any = {};
  years: any[] = [];

  paymentmodel: any = {};

  isSubmitted: boolean = false;
  cardValidate: boolean = false;
  cardDetailsValidate: boolean = false;
  cardType: string = "";
  marchantName?: string = '';
  buttonType!: string;


  public constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) { 

  }

  ngOnInit() {

    this.transactionForm = this.formBuilder.group({
      cardHolderName: ['', Validators.required],
      cardNumber: ['', Validators.required],
      month: ['', Validators.required],
      year: ['', Validators.required],
      cvv: ['', Validators.required],
      zipCode: ['', Validators.required]
    });

    this.otpForm = this.formBuilder.group({
      transactionId: ['', Validators.required],
      otp: ['', Validators.required]
    });

    this.dummyForm = this.formBuilder.group({
      amount: ['', Validators.required]
    });

    this.initMerchant();
    this.getMonths();
    this.getYears();
    this.loadIp();
  }
  loadIp() {
    this.http.get('https://jsonip.com').pipe(first()).subscribe(
      (value:any) => {
        //console.log(value);
        this.userIP = value.ip;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  initMerchant(){
    this.fetchMerchantInfo().pipe(first()).subscribe(merchant => {
      this.merchant = merchant;
      this.marchantName = merchant.name;
    })
  }

  
  
   // convenience getter for easy access to form fields
   get f() { return this.transactionForm.controls; }

  fetchMerchantInfo(){
    return this.http.get<MerchantInfo>(`${environment.apiUrl}/merchant/M-1`);
  }

  getYears() {
    this.years.push('2022', '2023', '2024', '2025', '2026', '2027', '2028', '2029', '2030');
  }

  getMonths() {
    this.monthlist.push('01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12');
  }

  changeMonth(e: any) {
    this.transactionForm.controls['month'].setValue(e.target.value);
  }

  changeYear(e: any) {
    console.log(e.target);
    this.transactionForm.controls['year'].setValue(e.target.value);
  }

  saveCardDetails() {
    

    
     // stop here if form is invalid
    //  if (this.transactionForm.invalid) {
    //   console.log("2");
    //   return;
    // }
    this.transactionReqest = this.transactionForm.value;
    this.transactionReqest.ipLocation = this.userIP;
    this.transactionReqest.receiverName = this.merchant?.name;
    this.transactionReqest.amount = this.dummyForm.value['amount'];
    this.otpRequest.cardNumber = this.transactionForm.value['cardNumber'];
    //console.log(this.transactionReqest);
    this.initTransaction(this.transactionReqest);
    //this.isSubmitted = true;
  }

  initTransaction(transaction: any){
    var url = `${environment.apiUrl}/transaction/initiate`;

    return this.http.post<TransactionResponse>(url, transaction)
        .subscribe(txn => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            //localStorage.setItem('txn', JSON.stringify(txn));
            this.isSubmitted = true;
            this.transactionResponse = txn;
            this.otpRequest.transactionId = txn.id;
            return txn;
        });
  }

  clearCardDetails() {
      this.transactionForm.reset();
      this.dummyForm.reset();
      this.otpForm.reset();

  }

  submitOtp(){

    this.isSubmitted = true;
    this.otpRequest.otp = this.otpForm.value['otp'];
    var url = `${environment.apiUrl}/transaction/authorize`;

    return this.http.post<TransactionResponse>(url, this.otpRequest)
        .subscribe(txn => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            //localStorage.setItem('txn', JSON.stringify(txn));
            this.transactionResponse = txn;
            alert("Transaction is successful. Transaction id :: " + txn.id);
            this.clearCardDetails();
            this.isSubmitted = false;
            return txn;
        });
  }

  onSubmit(buttonType: string): void {
    if(buttonType==="Save") {
      this.saveCardDetails();
    }
    if(buttonType==="Clear"){
        this.clearCardDetails();
    }

}


}
