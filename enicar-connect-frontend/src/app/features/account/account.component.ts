import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserAccount } from '../models/account.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private readonly API = 'https://encotrial-production.up.railway.app/api';
    private http = inject(HttpClient);
    private auth = inject(AuthService);

    private _account = new BehaviorSubject<UserAccount>(this.buildFromAuth());

    readonly account$: Observable<UserAccount> = this._account.asObservable();
    get current(): UserAccount { return this._account.value; }

    private buildFromAuth(): UserAccount {
        const u = this.auth.currentUser();
        return {
            id: u?.id ?? 0,
            firstName: u?.firstName ?? '',
            lastName: u?.lastName ?? '',
            email: u?.email ?? '',
            phone: '',
            bio: '',
            website: '',
            linkedin: '',
            github: '',
            avatarInitials: u?.initials ?? '',
            avatarColor: u?.avatarColor ?? '',
            role: u?.role ?? 'student',
            department: '',
            level: '',
            notifications: { emailPosts: true, emailMessages: true, emailEvents: false, pushAll: true },
            privacy: { profilePublic: true, showEmail: false, showPhone: false }
        };
    }

    update(changes: Partial<UserAccount>): void {
        this._account.next({ ...this._account.value, ...changes });
        const u = this.auth.currentUser();
        if (!u) return;
        this.http.put(`${this.API}/users/${u.id}`, changes).subscribe({
            error: (err) => console.error('Account update failed', err)
        });
    }

    updatePassword(oldPwd: string, newPwd: string): boolean {
        const u = this.auth.currentUser();
        if (!u || oldPwd.length < 1) return false;
        this.http.post(`${this.API}/users/${u.id}/change-password`, {
            oldPassword: oldPwd,
            newPassword: newPwd
        }).subscribe({
            error: (err) => console.error('Password change failed', err)
        });
        return true;
    }
}
