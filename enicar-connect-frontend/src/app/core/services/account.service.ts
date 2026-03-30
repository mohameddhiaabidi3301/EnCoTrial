import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserAccount } from '../models/account.model';

const DEFAULT_ACCOUNT: UserAccount = {
    id: 1,
    firstName: 'Mohamed',
    lastName: 'Jerbi',
    email: 'mohamed.jerbi@enicar.u-carthage.tn',
    phone: '+216 99 123 456',
    bio: 'Étudiant en 2ème année INFO à ENI Carthage. Passionné de développement web et de cybersécurité. Toujours à la recherche de nouveaux défis.',
    website: 'https://jerbi.dev',
    linkedin: 'https://linkedin.com/in/mjerbi',
    github: 'https://github.com/mjerbi',
    avatarInitials: 'MJ',
    avatarColor: 'rgba(99,102,241,.15)',
    role: 'student',
    department: 'Informatique',
    level: '2ème année',
    notifications: { emailPosts: true, emailMessages: true, emailEvents: false, pushAll: true },
    privacy: { profilePublic: true, showEmail: false, showPhone: false }
};

@Injectable({ providedIn: 'root' })
export class AccountService {
    private _account = new BehaviorSubject<UserAccount>(DEFAULT_ACCOUNT);
    readonly account$: Observable<UserAccount> = this._account.asObservable();

    get current(): UserAccount { return this._account.value; }

    update(changes: Partial<UserAccount>): void {
        this._account.next({ ...this._account.value, ...changes });
    }

    updatePassword(_old: string, _newPwd: string): boolean {
        // Mock: always succeeds as long as old password is not empty
        return _old.length >= 1;
    }
}
