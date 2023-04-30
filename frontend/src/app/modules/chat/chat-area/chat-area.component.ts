import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

import {SecurityService} from "../../core/services/security.service";
import {ChatService} from "../../core/services/chat.service";
import {UserService} from "../../core/services/user.service";
import {FormControl} from "@angular/forms";


@Component({
  selector: 'app-chat-area',
  templateUrl: './chat-area.component.html',
  styleUrls: ['./chat-area.component.css']
})
export class ChatAreaComponent implements OnInit {
  chat: any;

  public invitation: boolean = false;
  chatId: string = '';
  myControl = new FormControl();
  private keyUpTimeout: any;
  users: Array<any> = [];

  constructor(private chatService: ChatService,
              private securityService: SecurityService,
              private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.chatId = this.route.snapshot.paramMap.get('chatId')!;

    this.chatService.checkChatAffiliation(this.chatId)
      .subscribe(
        answer => {
          if (!answer) {
            this.router.navigate([`chats`]).then();
          }
        }
      );

    this.chatService.getChat(this.chatId)
      .subscribe(
        chat => {
          this.chat = chat;
        }
      );

    this.myControl.valueChanges.subscribe(next =>
      this.onKey(next));
  }

  onKey(value: string) {
    console.log(value)
    clearTimeout(this.keyUpTimeout);
    this.keyUpTimeout = setTimeout(() => {
      this.getNew(value);
    }, 450);
  }

  rename() {
    this.chatService.renameChat(this.chatId, this.chat.name).subscribe();
  }

  leaveChat() {
    this.chatService.leaveChat(this.chatId)
      .subscribe(
        data => {
          this.router.navigate([`chats`]).then();
        }
      )
  }

  inviteUser() {
    if (this.myControl.value) {
      this.chatService.inviteToChat(this.myControl.value, this.chatId)
        .subscribe(
          data => {
            this.invitation = false;
            this.chat.users.push(this.myControl.value);
          }
        );
    }
  }

  private getNew(value: string) {
    console.log("getNew " + value)
    this.userService.searchUsers(value)
      .subscribe(
        users => {
          this.users = users;
        })
  }
}
