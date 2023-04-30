import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BggApiService {


  constructor(private http: HttpClient) {
  }

  searchGame(name: string): Observable<any> {
    return this.http.get(`api/v1/game/search/${name}`)
  }

  getGameById(id: string): Observable<any> {
    return this.http.get(`api/v1/game/${id}`)
  }

  getGameByIds(ids: string): Observable<any> {
    return this.http.get(`api/v1/game/ids/${ids}`)
  }

  getGameType(type: string): Observable<any> {
    return this.http.get(`api/v1/game/type/${type}`)
  }

  getGameByTypeName(type: string, name: string): Observable<any> {
    return this.http.get(`api/v1/game/${type}/${name}`)
  }
}
