import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app.routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/app.home';
import { PaymentDoneComponent } from './paymentcompleted/app.paymentdone';

@NgModule({
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        PaymentDoneComponent,
        HomeComponent
    ],
    // providers: [
    //     { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }

    //     // provider used to create fake backend
    //     //fakeBackendProvider
    // ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { };