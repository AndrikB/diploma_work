import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class PlanGameService {

  constructor(private http: HttpClient) {
  }

  planGame(body: any): Observable<any> {
    return this.http.post(`api/v1/plan/`, body)
  }

  getAllPlannedGames(): Observable<any> {
    return this.http.get(`api/v1/plan/`)
  }

  participateGame(body: any): Observable<any> {
    return this.http.post(`api/v1/plan/participate/`, body)
  }
}
