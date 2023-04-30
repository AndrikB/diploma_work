import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  renameChat(chatId: string, newName: string) {
    return this.http.post<any>(`api/v1/chats/${chatId}`, newName, this.httpOptions).pipe(
      catchError(this.handleError<any>(null))
    );
  }

  createChat(chat: any): Observable<any> {
    return this.http.post<any>(`api/v1/chats/createChat`, chat, this.httpOptions);
  }

  inviteToChat(userId: string, chatId: string) {
    return this.http.post<any>(`api/v1/user/${userId}/toChat/${chatId}`, this.httpOptions);
  }

  leaveChat(chatId: string) {
    return this.http.delete<string>(`api/v1/chats/${chatId}/leave`);
  }

  getUserChats(): Observable<any> {
    return this.http.get<any[]>(`api/v1/chats`)
  }

  getChat(chatId: string) {
    return this.http.get<any>(`api/v1/chats/${chatId}`)
      .pipe(
        catchError(this.handleError<any>(null))
      );
  }

  getMessages(chatId: string, offset: number): Observable<any[]> {
    return this.http.get<any[]>(`api/v1/chats/${chatId}/messages?offset=${offset}`)
      .pipe(
        catchError(this.handleError<any[]>([]))
      );
  }

  checkChatAffiliation(chatId: string) {
    return this.http.get<boolean>(`api/v1/chats/${chatId}/check`)
      .pipe(
        catchError(this.handleError<boolean>(false))
      );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      return of(result as T);
    };
  }

}
