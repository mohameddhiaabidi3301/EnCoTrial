# ENICAR Connect 🏛️

> **Plateforme web centralisée de la communauté de l'École Nationale d'Ingénieurs de Carthage**

*Projet de Développement Web Avancé - Spring Boot & Angular*

## 👥 Équipe — INFO2 Groupe C

Pour assurer un travail professionnel, structuré et couvrant l'ensemble du cahier des charges, le projet a été rigoureusement décomposé entre les trois membres :

| Membre | Rôle Global | Responsabilité Spécifique |
|--------|-------------|----------------------------|
| **Mohamed Jerbi** | Chef de projet | Architecture globale (Sécurité, Base de données), Modération & Administration Globale (Panel). |
| **Mohamed Babou** | Développeur | Module **Réseau Social Interne** (Fil d'actualités, Groupes, Événements, Messagerie). |
| **Mohamed Dhia Islem Abidi**| Développeur | Module **Réseau Professionnel** (Opportunités de stage/emploi, Mentorat, Connexions). |

---

## 📋 1. PRÉSENTATION DU PROJET (Cahier des charges)

L’École Nationale d’Ingénieurs de Carthage (ENI Carthage) souhaite développer une plateforme web centralisée destinée à l’ensemble de sa communauté.
La plateforme comprend 3 modules principaux :
1. **Module Réseau Social Interne :** Fil d'actualité, création et gestion de groupes, gestion d'événements, messagerie instantanée.
2. **Module Réseau Professionnel :** Profils riches (CV), connexions étudiantes/alumni, offres de stages et d'emploi, mentorat.
3. **Module Services Utiles & Administration :** Panel de modération, rôles, permissions et digitalisations diverses.

---

## 🎯 2. ANALYSE DU PROJET (FRONTEND & BACKEND) ET LISTES DES ISSUES

Afin de clore le projet et de satisfaire l'intégralité du cahier des charges, voici l'analyse complète de ce qui a été développé, de ce qui manque, et des corrections à effectuer. Ces points constituent notre "Backlog" de tâches (Issues).

### 🔴 A. ISSUES DE CORRECTION ET MAINTENANCE (Bugs existants)
* **Issue #1 (Backend - Jerbi) : Conflit de version Java.**
  * *Analyse :* Le `pom.xml` exige Java 17, mais les environnements de test tournent parfois sur Java 8, ce qui bloque la compilation (`mvn clean install`).
  * *Action :* Configurer le serveur ou rétrograder proprement les dépendances si nécessaire.
* **Issue #2 (Frontend - Babou & Abidi) : Débordement CSS (Budgets Angular).**
  * *Analyse :* Le compilateur Angular lève des `budget warnings` sur `groups.component.css` et `jobs.component.css`.
  * *Action :* Optimiser les feuilles de styles de ces modules.
* **Issue #3 (Frontend - Babou) : Avertissement CommonJS sur `@stomp/stompjs`.**
  * *Analyse :* Module incompatible avec ESM dans le build Angular.
  * *Action :* Dans `angular.json`, ajouter `allowedCommonJsDependencies: ["@stomp/stompjs"]`.

### 🟡 B. ISSUES D'INTÉGRATION ET COMPLÉTION (Ce qui manque)
* **Issue #4 (Sécurité/Admin - Jerbi) : Dashboard de modération manquant.**
  * *Analyse :* Le CDC stipule des outils de modération (validation de contenus, suppression, signalement). Les endpoints existent, mais le Front Admin n'est pas terminé.
  * *Action :* Lier le `ReportStatus` et les APIs d'administration au Frontend (Route `feature/admin`).
* **Issue #5 (Réseau Social - Babou) : Upload réel des médias et photos.**
  * *Analyse :* La création de "Posts" sur le réseau social permet théoriquement l'ajout d'images (CDC 3.1), mais l'implémentation MultipartFile n'est pas encore 100% active.
  * *Action :* Finaliser le processus d'upload via le contrôleur Spring Boot et la sauvegarde dans le dossier `uploads/`.
* **Issue #6 (Réseau Pro - Abidi) : Filtres de Recherche des Opportunités.**
  * *Analyse :* Le CDC (Section 4.3) réclame une "Recherche multicritères" pour les offres de stage et d'emploi. L'interface affiche la liste mais ne filtre pas activement.
  * *Action :* Connecter les filtres du composant `jobs` Angular aux requêtes personnalisées dans `JobRepository`.

### 🟩 C. ISSUES DE DÉVELOPPEMENT FINAL (Nouvelles fonctionnalités)
* **Issue #7 (Réseau Social - Babou) : Déploiement des WebSockets STOMP.**
  * *Action :* Rendre la "Messagerie interne" (Section 3.4 du CDC) totalement "Temps Réel" en activant les flux RxJS liés au backend Spring Boot.
* **Issue #8 (Réseau Pro - Abidi) : Cycle de vie du Mentorat.**
  * *Action :* Implémenter le changement d'état (PENDING, ACCEPTED) des relations Mentor/Mentoré depuis l'interface utilisateur.
* **Issue #9 (Services Utiles - Jerbi) : Export de Données Administratives.**
  * *Action :* Permettre l'extraction de statistiques d'utilisation globales (Panel Admin) en format CSV ou Excel pour répondre aux besoins de la direction.

---

## 🛠️ 3. ARCHITECTURE ET DÉMARRAGE

### Stack Technique
* **Frontend** : Angular 15+ (TypeScript, RxJS)
* **Backend** : Spring Boot 3 (Java 17, Spring Security, JWT)
* **Base de données** : MySQL 8.x + H2 DB (développement)
* **Temps réel** : WebSockets (STOMP)

### Comment lancer l'application en développement

**1. Frontend (Angular) :**
```bash
cd enicar-connect-frontend
npm install
npm run build
npm start
# Le port par défaut est http://localhost:4200
```

**2. Backend (Spring Boot) :**
```bash
cd enicar-connect-backend
# Assurez-vous d'avoir Java 17 installé et la variable JAVA_HOME configurée
mvn clean compile
mvn spring-boot:run
# L'API tourne sur http://localhost:8080 ou 8081 (selon application.properties)
```
