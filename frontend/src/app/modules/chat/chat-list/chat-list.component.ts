import {Component, OnInit} from '@angular/core';
import {SecurityService} from "../../core/services/security.service";
import {ChatService} from "../../core/services/chat.service";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {
  public creation: boolean = false;

  public chats: Array<any> = [];
  chatName: string = '';

  constructor(private chatService: ChatService,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    this.chatService.getUserChats()
      .subscribe(
        chats => {
          console.log(chats)
          this.chats = chats;
        })
  }

  createChat() {
    let name = this.chatName
    this.chatService.createChat({name})
      .subscribe(
        data => {
          this.creation = false;
          window.location.reload();
        }
      )
  }
}
