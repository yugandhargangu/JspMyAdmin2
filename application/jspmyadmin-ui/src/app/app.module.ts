import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { APP_BASE_HREF } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { SidebarComponent, TopbarComponent, HeaderServerComponent } from './components';
import {
  InfoComponent,
  DatabaseListComponent,
  StatusComponent,
  CharsetsComponent,
  VariablesComponent,
  EnginesComponent
} from './pages/server';

import { routingModule } from './app.routing';
import { ErrorInterceptor, TokenInterceptor } from './helpers';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    TopbarComponent,
    HeaderServerComponent,
    InfoComponent,
    DatabaseListComponent,
    StatusComponent,
    CharsetsComponent,
    VariablesComponent,
    EnginesComponent
  ],
  imports: [
    BrowserModule,
    routingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
