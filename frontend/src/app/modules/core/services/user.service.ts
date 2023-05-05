import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  public getMyUser(): Observable<any> {
    return this.getUser("me");
  }

  public getUser(id: string): Observable<any> {
    return this.http.get(`api/v1/user/${id}`);
  }

  public updateProfile(body: any): Observable<any> {
    return this.http.put(`api/v1/user/`, body);
  }

  public updatePassword(body: any): Observable<any> {
    return this.http.put(`api/v1/user/password`, body);
  }

  searchUsers(login: string): Observable<any> {
    return this.http.get(`api/v1/user/search/${login}`);
  }
}
