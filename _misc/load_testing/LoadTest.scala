package heterogeneousmicroservices

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8084")

	val scn = scenario("GetApplicationInfo").repeat(2000) {
		exec(http("GetApplicationInfo")
			.get("/application-info")
			.check(status.is(200)))
	}

	setUp(scn.inject(atOnceUsers(30)))
		.protocols(httpProtocol)
}