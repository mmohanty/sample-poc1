import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//import { AppComponent } from './app.component';
import { PaymentDoneComponent } from './paymentcompleted/app.paymentdone';
import { HomeComponent } from './home/app.home';
//const appModule = () => import('./app.module').then(x => x.AppModule);


const routes: Routes = [
    { path: '', component: HomeComponent },
    // otherwise redirect to home
    { path: '**', redirectTo: 'home' , pathMatch: 'full'},
    { path: 'paymentdone/:txnRefNumber', component: PaymentDoneComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }