import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from "@angular/router";


@Injectable()
export class SecurityService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private authorizeEndpoint = '/oauth2/authorization/github'
  private tokenEndpoint = '/login/oauth2/code/github';
  private baseUrl = environment.baseUrl;
  private tokenKey = 'token';

  constructor(private http: HttpClient,
              private jwtHelper: JwtHelperService,
              public router: Router) {
  }

  loginViaGitHub() {
    console.log("this.baseUrl: " + this.baseUrl)
    console.log("this.baseUrl + this.authorizeEndpoint: " + this.baseUrl + this.authorizeEndpoint)

    window.open(this.baseUrl + this.authorizeEndpoint, '_self')
  }

  login(login: string, password: string): Observable<any> {
    return this.http.post(`/api/v1/auth`, {login, password}, {responseType: 'text'})
  }

  createAccount(login: string, mail: string, password: string): Observable<boolean> {
    return this.http.post<boolean>(`/api/v1/auth/register`, {login, mail, password})
  }

  updateToken(token: any) {
    localStorage.setItem(this.tokenKey, token);
  }

  fetchToken(code: String, state: String): Observable<any> {
    console.log(this.baseUrl + this.tokenEndpoint + '?code=' + code + '&state=' + state)
    return this.http.get(this.baseUrl + this.tokenEndpoint + '?code=' + code + '&state=' + state, {responseType: 'text'});
  }

  getToken() {
    let token = localStorage.getItem(this.tokenKey);
    if (token == null)
      return null
    if (this.jwtHelper.isTokenExpired(token)) {
      this.removeToken();
      return null;
    }
    return token
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return token != null;
  }

  getUserId(): string {
    const token = this.getToken()!;
    return this.jwtHelper.decodeToken(token).jti
  }

  removeToken() {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['login']);
  }

  logout(): Observable<any> {
    return this.http.post(this.baseUrl + '/logout', this.getToken());
  }

  activateUser(token: String): Observable<any> {
    return this.http.post(`/api/v1/auth/activate/${token}`, null)
  }
}
