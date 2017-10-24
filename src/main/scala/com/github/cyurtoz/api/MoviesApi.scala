package com.github.cyurtoz.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.cyurtoz.db.RedisRepoImpl
import com.github.cyurtoz.learn.MoviesRecommender
import com.github.cyurtoz.model.{MovieService, Recommendation}
import com.github.cyurtoz.utils.BearerTokenGenerator

trait MoviesApi extends BearerTokenGenerator {

  val routes: Route =
    logRequestResult("akka-http") {
      path("movies") {
        (parameters("id") & get) { id =>
          complete {
            MovieService.get(id)
          }
        }
      }
    } ~
      (path("train") & get) {
        complete {
          val mvv: Array[Recommendation] = MoviesRecommender.model
          val mv = mvv.map(x => (MovieService.getName(x.movieId),
            x.recommendationIds.map(z => MovieService.getName(z))))

          mv.mkString("\n")
        }
      } ~
      (path("redis") & get) {
        complete {
          RedisRepoImpl.save(Recommendation(1, List(1, 2, 3, 4, 5)))
          RedisRepoImpl.save(Recommendation(2, List(6, 7, 8)))

          RedisRepoImpl.get(2).toString

        }
      }

}

