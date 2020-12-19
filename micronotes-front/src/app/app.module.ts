import { AuthService } from './auth/auth.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterSuccessComponent } from './auth/register-success/register-success.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { WelcomeComponent } from './welcome/welcome.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NoteDashboardComponent } from './notes/note-dashboard/note-dashboard.component';
import { HttpClientInterceptor } from './http-request-interceptor';
import { NoteEditComponent } from './notes/note-edit/note-edit.component';
import { NoteCreateComponent } from './notes/note-create/note-create.component';
import { NoteViewComponent } from './notes/note-view/note-view.component';
import { AuthGuard } from './auth.guard';
import { QuillModule } from 'ngx-quill';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    RegisterSuccessComponent,
    WelcomeComponent,
    NoteDashboardComponent,
    NoteEditComponent,
    NoteCreateComponent,
    NoteViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    QuillModule.forRoot(),
    NgxWebstorageModule.forRoot(),
    RouterModule.forRoot([
      {path: '', component: WelcomeComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'register-success', component: RegisterSuccessComponent},
      {path: 'login', component: LoginComponent},
      {path: 'notes', component: NoteDashboardComponent, canActivate: [AuthGuard]},
      {path: 'note/create', component: NoteCreateComponent, canActivate: [AuthGuard]},
      {path: 'note/:id', component: NoteViewComponent, canActivate: [AuthGuard]},
      {path: 'note/edit/:id', component: NoteEditComponent, canActivate: [AuthGuard]},
    ]),
    HttpClientModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpClientInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
