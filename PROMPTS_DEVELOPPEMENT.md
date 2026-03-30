# Modules à Compléter - Projet ENICAR Connect

Voici le découpage détaillé des modules restants à compléter pour votre projet (Backend Java Spring Boot et Frontend Angular), accompagné pour chacun d'un **prompt clé-en-main** que vous pourrez copier-coller dans une IA pour générer le code exhaustif.

---

## 📅 1. Module : Gestion des Événements (Events)

* **Ce qu'il faut faire :** Remplacer les fausses données Frontend (`event.service.ts`) en créant le Backend complet (Entité, Repository, Service, Contrôleur) et gérer les inscriptions.
* **Tâches couvertes :** Création d'événements, catégorisation, liste, inscription/désinscription.

### 📝 Prompt à copier-coller :
```text
Génère le code complet pour le "Module Événements" de mon application réseau social (Spring Boot + Angular).

Côté Backend (Spring Boot 3 + JPA) :
1. Crée une entité `AppEvent` (id, title, date, time, location, description, category, organizer, color, maxCapacity).
2. Ajoute une relation `@ManyToMany` entre `AppEvent` et `User` pour gérer les inscriptions (`registeredUsers`).
3. Implémente le `EventRepository`, le `EventService` et le `EventController` avec les endpoints CRUD classiques et un endpoint `POST /api/events/{id}/toggle-register` pour s'inscrire/se désinscrire.

Côté Frontend (Angular) :
4. Modifie le fichier `event.service.ts` existant pour supprimer les MOCK_EVENTS et utiliser le `HttpClient` pour appeler mon API Backend locale (`http://localhost:8081/api/events`). Intègre la gestion des en-têtes d'authentification (si non fait par un intercepteur global).
```

---

## 💼 2. Module : Opportunités Professionnelles (Stages/Emplois)

* **Ce qu'il faut faire :** Remplacer le `job.service.ts` mocké sur le frontend par une véritable interface Backend qui gère les offres, critères et candidatures.
* **Tâches couvertes :** Publication d'offres (Stage, PFE, CDI), filtrages, suivi de statut, postulation.

### 📝 Prompt à copier-coller :
```text
Génère le module "Opportunités Professionnelles" pour mon application (Spring Boot + Angular).

Côté Backend (Java) :
1. Crée l'entité `JobOffer` (id, title, company, location, type, description, tags, authorId).
2. Crée l'entité `Application` pour gérer les candidatures (jobId, userId, dateApplied, status).
3. Développe `JobRepository`, `JobService` et `JobController` avec les endpoints : récupération par filtres (type, lieu), création, mise à jour, suppression.
4. Ajoute un endpoint `POST /api/jobs/{id}/apply` pour candidater à une offre.

Côté Frontend (Angular) :
5. Mets à jour le `job.service.ts` pour qu'il communique via `HttpClient` avec `/api/jobs`.
6. Adapte les méthodes `getAll()`, `add()`, `apply()` pour consommer l'API au lieu de la constante MOCK_JOBS.
```

---

## 💬 3. Module : Messagerie Instantanée Temps Réel

* **Ce qu'il faut faire :** Actuellement, `messaging.component.ts` est purement statique. Il faut mettre en place un serveur WebSocket côté Spring Boot et s'y connecter via RxJS / StompJS côté Angular.
* **Tâches couvertes :** Entités des messages, configuration WebSockets, envoi/réception en direct.

### 📝 Prompt à copier-coller :
```text
Génère le code complet pour intégrer une véritable "Messagerie en temps réel" dans mon projet Spring Boot / Angular.

Backend (Spring Boot WebSocket) :
1. Configure WebSockets avec l'annotation `@EnableWebSocketMessageBroker` dans une classe `WebSocketConfig`.
2. Crée une entité `ChatMessage` (id, senderId, recipientId, content, timestamp, isRead).
3. Implémente un `ChatController` avec `@MessageMapping` pour traiter l'envoi et la réception des messages via un broker de messagerie (STOMP).
4. Ajoute le code pour sauvegarder chaque message en base de données.

Frontend (Angular) :
5. Donne-moi le code pour installer et configurer `@stomp/ng2-stompjs` (ou équivalent).
6. Crée un `chat.service.ts` ou mets à jour le `messaging.component.ts` existant pour s'abonner au canal WebSocket et recevoir/afficher les messages en direct.
```

---

## 🤝 4. Module : Profils Avancés et Connexions Professionnelles

* **Ce qu'il faut faire :** Enrichir le modèle `User` basique actuel pour supporter les CV (expériences, études) et implémenter les requêtes d'amis/réseau métier.
* **Tâches couvertes :** Héritage de parcours académique et exp., système d'ajout "Se connecter" "Accepter".

### 📝 Prompt à copier-coller :
```text
Génère l'extension du modèle "Réseau Professionnel et Connexions" (Backend Java).

1. Crée les entités nécessaires pour étoffer le profil d'un utilisateur existant (entités liées à `User`) : `Education` (diplômes), `Experience` (historique entreprise), `Skill` (compétences).
2. Crée une entité `ConnectionRequest` pour la gestion des relations (senderId, receiverId, status: PENDING, ACCEPTED, REJECTED, timestamp).
3. Développe `ConnectionService` et `ConnectionController` pour gérer l'envoi d'une demande de contact, l'acceptation et le listage des relations approuvées (le réseau de l'utilisateur).
4. Écris brièvement la couche RxJS Angular (`network.service.ts`) qui consommera ces nouvelles routes API.
```

---

## 📂 5. Module : Services Utiles (Ressources & Documents)

* **Ce qu'il faut faire :** Transformer la maquette des fichiers partagés (`resource.service.ts`) en un vrai système d'upload avec persistance des fichiers côté serveur (+ chemins bdd).
* **Tâches couvertes :** Upload `MultipartFile`, stockage, listage et téléchargement sécurisé.

### 📝 Prompt à copier-coller :
```text
Aide-moi à implémenter le "Module Partage de Ressources" pour mon école avec gestion de fichiers uploadés (Spring Boot + Angular).

Backend Spring Boot :
1. Crée l'entité `ResourceFile` (id, title, authorId, uploadDate, fileSize, category, filePath).
2. Développe un `FileStorageService` permettant de sauvegarder (upload) un fichier reçu en MultipartFile dans un dossier local (ex: `/uploads`), et de le récupérer/télécharger.
3. Crée le `ResourceController` avec une route POST sécurisée pour uploader et une route GET pour lister.

Frontend Angular :
4. Remplace les données mockées dans mon fichier `resource.service.ts` actuel.
5. Donne moi l'implémentation de la fonction d'upload de fichiers HTTP côté Angular depuis un `<input type="file">`.
```

---

## 🎓 6. Module : Programme de Mentorat

* **Ce qu'il faut faire :** C'est un cas d'usage pur métier. Il faut gérer qui se propose, qui demande.
* **Tâches couvertes :** Mise en relation Mentor (Alumni/Enseignant) / Mentoré (Étudiant).

### 📝 Prompt à copier-coller :
```text
Développe le "Programme de Mentorat" pour mon application (Spring Boot Backend).

1. Crée l'entité `MentorshipRequest` (id, mentorId, menteeId, objective, status/state: PENDING, ACTIVE, COMPLETED, createdAt).
2. Implémente le service et le contrôleur pour permettre à un étudiant (mentee) de parcourir la liste des utilisateurs de type 'ALUMNI' ou 'TEACHER' disponibles pour le mentorat.
3. Crée un endpoint permettant au Mentee de postuler, et au Mentor d'approuver ou de refuser un élève.
4. Rédige un aperçu du composant Angular (`mentorship.component.ts`) avec un tableau de bord basique pour que l'étudiant suive le statut de ses demandes de mentorat.
```
