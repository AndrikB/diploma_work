import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";
import {ChatListComponent} from './chat-list/chat-list.component';
import {ChatAreaComponent} from './chat-area/chat-area.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CoreModule} from "../core/core.module";
import { MessageHistoryComponent } from './chat-area/message-history/message-history.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [ChatListComponent, ChatAreaComponent, MessageHistoryComponent],
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        CoreModule,
        MatAutocompleteModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        MatButtonModule
    ],
})
export class ChatModule {
}
