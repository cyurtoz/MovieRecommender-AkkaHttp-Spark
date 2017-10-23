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

  def get(id:String):String = {
    val idInt = Integer.parseInt(id)
    movies.find(_.id == idInt) match {
      case Some(mv) => mv.toString
      case _ => "Not Found"
    }
  }


  def getName(id:Long):String = {
    movies.find(_.id.longValue() == id.longValue()) match {
      case Some(mv) => mv.movie_title
      case _ => "Not Found"
    }
  }

}
