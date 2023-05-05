import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SecurityService} from "./security.service";

@Injectable()
export class AuthHeaderInterceptor implements HttpInterceptor {

  constructor(private securityService: SecurityService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.securityService.getToken() != null){
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'Access-Control-Allow-Origin': 'http://localhost:8080',
          Authorization: 'Bearer ' + this.securityService.getToken()
        }
      });
    }
    return next.handle(request);
  }
}
