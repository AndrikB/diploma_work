import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {MessageHistoryComponent} from "../../chat/chat-area/message-history/message-history.component";
import {environment} from "../../../../environments/environment";
import {Injectable} from "@angular/core";

@Injectable()
export class WebSocketAPI {
  topic: string = "/topic/chat/";
  // @ts-ignore
  stompClient: Stomp.Client;
  appComponent: MessageHistoryComponent;
  // @ts-ignore
  private chatId: string;

  constructor(appComponent: MessageHistoryComponent) {
    this.appComponent = appComponent;
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error: any) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this.init(this.chatId);
    }, 5000);
  }

  _send(destination: string, message: any) {
    this.stompClient.send(destination, {}, JSON.stringify(message));
  }

  onMessageReceived(message: any) {
    this.appComponent.handleMessage(message);
  }

  init(chatId: string): void {
    this.chatId = chatId;

    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(environment.baseUrl + '/ws');
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (_: any) {
      _this.stompClient.subscribe(_this.topic + _this.chatId, function (sdkEvent: any) {
        _this.onMessageReceived(sdkEvent);
      });
    }, this.errorCallBack);
  }

  destroy(): void {
    if (this.stompClient !== null) {
      // @ts-ignore
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }
}
