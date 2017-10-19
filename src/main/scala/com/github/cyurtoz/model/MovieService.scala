package com.github.cyurtoz.model

import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * Created by cyurtoz on 19/10/17.
  *
  */
object MovieService {

  val movies: List[Movie] = {
    val lines = scala.io.Source.fromInputStream(MovieService.getClass.getResourceAsStream("/data.json")).mkString
    implicit val movieFormat: MovieJsonProtocol.MovieJsonFormat.type = MovieJsonProtocol.MovieJsonFormat
     lines.parseJson.convertTo[List[Movie]].filter(p => p != null)
  }

  def get(id:String) = {
    movies.find(_.id == Integer.parseInt(id)) match {
      case Some(mv) => mv
      case _ => "Not Found"
    }
  }


}
