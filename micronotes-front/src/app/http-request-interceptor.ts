import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpClientInterceptor implements HttpInterceptor {
  constructor(private $localStorage: LocalStorageService) {

  }

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.$localStorage.retrieve("authenticationToken");
    if (token) {
      const bearerToken = 'Bearer ' + token;
      const authReq = req.clone({ setHeaders: 
                                    { 
                                      'Content-Type': 'application/json',
                                      'Authorization': bearerToken,
                                      'Access-Control-Allow-Origin':'*'
                                    } 
                                });
      return next.handle(authReq);
    }
    else {
      return next.handle(req);
    }
  }
}