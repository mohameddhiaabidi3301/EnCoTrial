import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { ParticlesBgComponent } from '../../shared/particles-bg/particles-bg.component';
import { ToastComponent } from '../../shared/toast/toast.component';
import { ChatService, ChatMessage } from '../../core/services/chat.service';

interface Conversation {
  id: number; initials: string; name: string; online: boolean; preview: string; time: string; unread?: number;
}

@Component({
  selector: 'app-messaging',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent, ParticlesBgComponent, ToastComponent],
  templateUrl: './messaging.component.html',
  styleUrl: './messaging.component.css'
})
export class MessagingComponent implements OnInit {
  private chatService = inject(ChatService);

  // La liste des amis est toujours statique pour l'UI, mais les IDs (1, 2, 3) doivent exister en DB
  conversations: Conversation[] = [
    { id: 2, initials: 'FJ', name: 'Faouzi JAIDI (ID: 2)', online: true, preview: 'N\'oubliez pas...', time: '14:32' },
    { id: 3, initials: 'DA', name: 'Dhia Abidi (ID: 3)', online: false, preview: 'On commence demain ?', time: 'Lun.' },
    { id: 4, initials: 'AM', name: 'Ahmed Mansour (ID: 4)', online: true, preview: 'Merci pour l\'aide !', time: 'Dim.' },
  ];

  activeConv: Conversation | null = null;
  activeMessages: ChatMessage[] = [];
  inputText = '';
  typing = false;
  myId = this.chatService.myId; // Pour afficher de quel coté est le message

  ngOnInit() {
    // S'abonner aux messages pour afficher l'historique ou les nouveaux
    this.chatService.messages$.subscribe(msgs => {
      this.activeMessages = msgs;
    });
  }

  selectConv(c: Conversation): void {
    this.activeConv = c;
    c.unread = undefined;

    // Aller chercher l'historique REEL en BDD !
    this.chatService.getConversation(c.id);
  }

  sendMessage(): void {
    if (!this.inputText.trim() || !this.activeConv) return;

    // Envoie VRAIMENT le message par WebSocket
    this.chatService.sendMessage(this.activeConv.id, this.inputText.trim());
    this.inputText = '';
  }
}
