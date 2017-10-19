package com.github.cyurtoz.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.cyurtoz.api.MoviesApi
import org.scalatest.{Matchers, WordSpec}

class MoviesApiSpec extends WordSpec with Matchers with ScalatestRouteTest with MoviesApi {
  "return a greeting for GET requests to the root path" in {
    Post("/movies") ~> routes ~> check {
      val response = responseAs[String]

      response should not be ""
      response.length should be(64)
    }
  }
}