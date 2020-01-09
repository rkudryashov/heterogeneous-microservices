package heterogeneousmicroservices

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8081")

	val scn = scenario("GetApplicationInfo").repeat(1000) {
		exec(http("GetApplicationInfo")
			.get("/application-info")
			.check(status.is(200))
			.check(jsonPath("$.name")))
	}

	setUp(
		scn.inject(
			rampUsers(50) during (20 seconds)
		).protocols(httpProtocol)
	)
}