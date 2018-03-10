import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule } from 'patternfly-ng';


import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NotificationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
