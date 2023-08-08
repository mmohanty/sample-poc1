import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '@environments/environment';
import { first, map } from 'rxjs';
import {Router} from '@angular/router';

import { MerchantInfo, TransactionRequest, TransactionResponse, OTPRequest } from '@app/_models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{


}
