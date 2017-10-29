package com.github.cyurtoz.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.cyurtoz.db.RedisRepoImpl
import com.github.cyurtoz.learn.MoviesRecommender
import com.github.cyurtoz.model.{MovieService, Recommendation}
import com.github.cyurtoz.utils.BearerTokenGenerator

import scala.util.Success

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
      pathPrefix("recommendations") {
        pathEnd {
          complete {
            val recommenderModel: Array[Recommendation] = MoviesRecommender.model

            RedisRepoImpl.saveAll(recommenderModel)
            recommenderModel.mkString("\n")
          }
        } ~
          path(IntNumber) { id =>
            onComplete(RedisRepoImpl.get(id.longValue())) { comp =>
               val response = comp match {
                case Success(Some(v)) => v.toString
                case _ => "Not Found"
              }
              complete(response)
            }
          } ~
          path("titles") {
            complete {
              val recommenderModel: Array[Recommendation] = MoviesRecommender.model

              val mappedRecommendations = recommenderModel.map(x => (MovieService.getName(x.movieId),
                x.recommendationIds.map(z => MovieService.getName(z))))
              mappedRecommendations.mkString("\n")
            }
          }
      }
}

