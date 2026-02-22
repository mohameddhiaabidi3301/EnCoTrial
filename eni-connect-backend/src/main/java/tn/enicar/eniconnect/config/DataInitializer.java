package tn.enicar.eniconnect.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.enicar.eniconnect.model.*;
import tn.enicar.eniconnect.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Initialise la base H2 avec des données de démonstration
 * cohérentes avec le frontend Angular (mêmes noms, mêmes données affichées).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        logger.info("Initialisation des données de démonstration...");

        // ==================== USERS ====================
        User admin = User.builder()
                .firstName("Admin").lastName("ENI")
                .email("admin@enicar.ucar.tn")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.DIRECTION)
                .avatarUrl("https://ui-avatars.com/api/?name=Admin+ENI&background=0071e3&color=fff")
                .build();
        admin = userRepository.save(admin);

        User mohamedJ = User.builder()
                .firstName("Mohamed").lastName("Jerbi")
                .email("mohamed.jerbi@eni-carthage.tn")
                .password(passwordEncoder.encode("password123"))
                .role(Role.STUDENT)
                .filiere("Génie Informatique").promotion("2026")
                .avatarUrl("https://ui-avatars.com/api/?name=Mohamed+Jerbi&background=0071e3&color=fff")
                .build();
        mohamedJ = userRepository.save(mohamedJ);

        User mohamedB = User.builder()
                .firstName("Mohamed").lastName("Babou")
                .email("mohamed.babou@eni-carthage.tn")
                .password(passwordEncoder.encode("password123"))
                .role(Role.STUDENT)
                .filiere("Génie Informatique").promotion("2026")
                .avatarUrl("https://ui-avatars.com/api/?name=Mohamed+Babou&background=0071e3&color=fff")
                .build();
        mohamedB = userRepository.save(mohamedB);

        User dhia = User.builder()
                .firstName("Mohamed Dhia").lastName("Abidi")
                .email("dhia.abidi@eni-carthage.tn")
                .password(passwordEncoder.encode("password123"))
                .role(Role.STUDENT)
                .filiere("Génie Informatique").promotion("2026")
                .avatarUrl("https://ui-avatars.com/api/?name=Dhia+Abidi&background=0071e3&color=fff")
                .build();
        dhia = userRepository.save(dhia);

        User profJaidi = User.builder()
                .firstName("Faouzi").lastName("Jaidi")
                .email("faouzi.jaidi@eni-carthage.tn")
                .password(passwordEncoder.encode("password123"))
                .role(Role.TEACHER)
                .avatarUrl("https://ui-avatars.com/api/?name=Faouzi+Jaidi&background=34C759&color=fff")
                .build();
        profJaidi = userRepository.save(profJaidi);

        User alumni1 = User.builder()
                .firstName("Sara").lastName("Tounsi")
                .email("sara.tounsi@gmail.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ALUMNI)
                .avatarUrl("https://ui-avatars.com/api/?name=Sara+Tounsi&background=FF9500&color=fff")
                .build();
        alumni1 = userRepository.save(alumni1);

        // ==================== POSTS ====================
        Post post1 = Post.builder()
                .content(
                        "Bonne nouvelle ! Le laboratoire d'informatique a été entièrement rénové avec de nouveaux équipements. Venez découvrir les nouvelles machines 🖥️")
                .author(admin)
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .content(
                        "Qui est partant pour un hackathon ce weekend ? On cherche des développeurs Full Stack et des designers UI/UX ! 🚀")
                .imageUrl("https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=600")
                .author(mohamedJ)
                .build();
        postRepository.save(post2);

        Post post3 = Post.builder()
                .content(
                        "Rappel : Le TP de Spring Boot est à rendre avant vendredi. N'oubliez pas d'inclure les tests JUnit et la configuration Log4J.")
                .author(profJaidi)
                .build();
        postRepository.save(post3);

        Post post4 = Post.builder()
                .content(
                        "Retour d'expérience : après 3 ans chez Vermeg, voici mes conseils pour réussir votre stage de fin d'études 💼")
                .author(alumni1)
                .build();
        postRepository.save(post4);

        // ==================== EVENTS ====================
        Event event1 = Event.builder()
                .title("Journée Portes Ouvertes ENI 2026")
                .description("Découvrez les filières de l'ENI Carthage et rencontrez les enseignants et étudiants.")
                .eventDate(LocalDate.of(2026, 3, 15))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .location("Campus ENI Carthage")
                .category("Académique")
                .organizer(admin)
                .organizerName("Direction ENI")
                .build();
        eventRepository.save(event1);

        Event event2 = Event.builder()
                .title("Hackathon Spring Boot")
                .description(
                        "48h pour développer une application Spring Boot innovante. Prizes et certificats à gagner !")
                .eventDate(LocalDate.of(2026, 3, 22))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(20, 0))
                .location("Lab Informatique — Bloc B")
                .category("Tech")
                .organizer(profJaidi)
                .organizerName("Club Dev ENI")
                .build();
        eventRepository.save(event2);

        Event event3 = Event.builder()
                .title("Forum Entreprises 2026")
                .description("Rencontrez les recruteurs des plus grandes entreprises tunisiennes et internationales.")
                .eventDate(LocalDate.of(2026, 4, 10))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(16, 0))
                .location("Amphithéâtre principal")
                .category("Professionnel")
                .organizer(admin)
                .organizerName("Bureau des stages")
                .build();
        eventRepository.save(event3);

        // ==================== GROUPS ====================
        Group group1 = Group.builder()
                .name("Club Développement Web")
                .description(
                        "Apprenez et partagez vos connaissances en développement web : Angular, React, Spring Boot...")
                .category("Tech")
                .creator(mohamedJ)
                .build();
        group1.getMembers().add(mohamedJ);
        group1.getMembers().add(mohamedB);
        group1.getMembers().add(dhia);
        groupRepository.save(group1);

        Group group2 = Group.builder()
                .name("Génie Informatique 2026")
                .description("Groupe officiel de la promotion 2026 — Génie Informatique")
                .category("Académique")
                .creator(admin)
                .build();
        group2.getMembers().add(mohamedJ);
        group2.getMembers().add(mohamedB);
        group2.getMembers().add(dhia);
        group2.getMembers().add(profJaidi);
        groupRepository.save(group2);

        Group group3 = Group.builder()
                .name("Club Robotique ENI")
                .description("Passionnés de robotique et d'IA embarquée, rejoignez-nous !")
                .category("Tech")
                .creator(dhia)
                .build();
        group3.getMembers().add(dhia);
        groupRepository.save(group3);

        // ==================== JOBS ====================
        Job job1 = Job.builder()
                .title("Stage PFE — Développeur Full Stack")
                .companyName("Vermeg")
                .description("Rejoignez notre équipe R&D pour un stage de 6 mois. Stack : Spring Boot + Angular.")
                .jobType("STAGE")
                .location("Les Berges du Lac, Tunis")
                .salary("800 TND/mois")
                .skills("Java,Spring Boot,Angular,SQL")
                .build();
        jobRepository.save(job1);

        Job job2 = Job.builder()
                .title("Ingénieur DevOps Junior")
                .companyName("Sofrecom Tunisie")
                .description(
                        "Poste CDI pour un ingénieur DevOps. Environnement Cloud (AWS/Azure), CI/CD, Docker, Kubernetes.")
                .jobType("CDI")
                .location("Technopole El Ghazala")
                .salary("2500-3500 TND")
                .skills("Docker,Kubernetes,AWS,Jenkins,Linux")
                .build();
        jobRepository.save(job2);

        Job job3 = Job.builder()
                .title("Stage d'été — Mobile Developer")
                .companyName("Talan Tunisie")
                .description("Stage de 2 mois en développement mobile (Flutter/React Native).")
                .jobType("STAGE")
                .location("Centre Urbain Nord, Tunis")
                .salary("600 TND/mois")
                .skills("Flutter,Dart,React Native,Firebase")
                .build();
        jobRepository.save(job3);

        logger.info("Données de démonstration initialisées avec succès !");
        logger.info("  → {} utilisateurs", userRepository.count());
        logger.info("  → {} posts", postRepository.count());
        logger.info("  → {} événements", eventRepository.count());
        logger.info("  → {} groupes", groupRepository.count());
        logger.info("  → {} offres", jobRepository.count());
    }
}
