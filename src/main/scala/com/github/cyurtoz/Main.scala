package com.github.cyurtoz

import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Directives._
import com.github.cyurtoz.api.MoviesApi
import com.github.cyurtoz.model.{Movie, MovieJsonProtocol}
import com.github.cyurtoz.utils.Config


object Main extends App with Config with MoviesApi {
  private implicit val system = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  Http().bindAndHandle(handler = logRequestResult("log")(routes), interface = httpInterface, port = httpPort)
}