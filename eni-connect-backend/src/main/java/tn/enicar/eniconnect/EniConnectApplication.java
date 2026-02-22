package tn.enicar.eniconnect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EniConnectApplication {

    private static final Logger logger = LogManager.getLogger(EniConnectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EniConnectApplication.class, args);
        logger.info("===========================================");
        logger.info("  ENI Connect Backend démarré avec succès  ");
        logger.info("  http://localhost:8080                    ");
        logger.info("  H2 Console: http://localhost:8080/h2-console");
        logger.info("===========================================");
    }
}
