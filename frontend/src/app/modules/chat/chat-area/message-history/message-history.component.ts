import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ChatService} from "../../../core/services/chat.service";

import {WebSocketAPI} from "../../../core/services/WebSocketApi";
import {SecurityService} from "../../../core/services/security.service";

@Component({
  selector: 'app-message-history',
  templateUrl: './message-history.component.html',
  styleUrls: ['./message-history.component.css']
})
export class MessageHistoryComponent implements OnInit, OnDestroy {
  @Input() chatId: any;

  isWaiting: boolean = false;
  isOver: boolean = false;
  isInit: boolean = true;

  public messages: Array<any> = [];
  message = {
    text: '',
    authorId: ''
  };
  userId: any;
  // @ts-ignore
  @ViewChild('msgScroll') private myScrollContainer: ElementRef;
  private webSocketAPI: WebSocketAPI;

  constructor(private chatService: ChatService,
              private securityService: SecurityService) {
    this.webSocketAPI = new WebSocketAPI(this);
  }

  ngOnInit(): void {
    this.getMessages();
    this.userId = this.securityService.getUserId()
    this.message.authorId = this.userId;
    this.webSocketAPI.init(this.chatId)
  }

  ngOnDestroy(): void {
    this.webSocketAPI.destroy()
  }

  scrollToBottom(): void {
    this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
  }

  sendMessage() {
    if (this.message.text.length == 0) {
      return;
    }
    this.webSocketAPI._send("/app/chat/" + this.chatId, this.message);

    this.message.text = '';
  }

  getMessages(): void {
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;

    this.chatService.getMessages(this.chatId, this.messages.length)
      .subscribe(
        messages => {
          if (messages.length < 10) {
            this.isOver = true;
          }

          if (messages.length == 0) {
            this.isWaiting = false;
            return;
          }

          for (const message of messages) {
            message.isIncoming = message.authorId != this.userId
          }

          this.isWaiting = false;
          this.messages = messages.reverse().concat(this.messages);

          if (this.isInit) {
            this.isInit = false;
            setTimeout(() => this.scrollToBottom(), 10);
          }

        })
  }

  scrollHandler(event: Event) {
    if (!this.isOver) {
      // @ts-ignore
      if (event.target.scrollTop < 60) {
        this.getMessages();
      }
    }
  }

  handleMessage(event: any) {
    if (event.body) {
      let message = JSON.parse(event.body);

      message.creationDate = new Date();
      message.isIncoming = message.authorId != this.userId
      this.messages.push(message);

      setTimeout(() => this.scrollToBottom(), 2);
    }
  }

}
