package com.awesome.testing;

import io.gatling.javaapi.core.*;

import java.time.Duration;

import static com.awesome.testing.core.GlobalAssertions.GLOBAL_ASSERTIONS;
import static com.awesome.testing.config.HttpConfig.HTTP_CONFIG;
import static com.awesome.testing.scenario.TrainingScenario.TRAINING_SCENARIO;
import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Zakładamy że każdy nasz endpoint ma 60rpm i piszemy test regresyjny żeby sprawdzić że wciąż jesteśmy w stanie dobrze
 * obsłużyć tego typu ruch
 */
public class BasicSimulation extends Simulation {

    private static final int DESIRED_RPM = 60;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final double RPS = (double) DESIRED_RPM / SECONDS_IN_MINUTE;

    {
        setUp(TRAINING_SCENARIO.injectOpen(
                        rampUsersPerSec(0).to(0.5).during(Duration.ofMinutes(30)),
                        constantUsersPerSec(0.5).during(Duration.ofMinutes(30)),

                        rampUsersPerSec(0.5).to(1).during(Duration.ofMinutes(30)),
                        constantUsersPerSec(1).during(Duration.ofMinutes(30)),

                        rampUsersPerSec(1).to(1.5).during(Duration.ofMinutes(30)),
                        constantUsersPerSec(1).during(Duration.ofMinutes(30)),

                        rampUsersPerSec(1.5).to(2).during(Duration.ofMinutes(30)),
                        constantUsersPerSec(2).during(Duration.ofMinutes(30))

                )
                .protocols(HTTP_CONFIG))
                .assertions(GLOBAL_ASSERTIONS);
    }
}
