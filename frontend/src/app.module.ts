import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CartModule } from './pages/cart/cart.module'; 

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    CartModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
