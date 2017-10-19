package com.github.cyurtoz.model

import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * Created by cyurtoz on 19/10/17.
  *
  */
object MovieService {

  def get(id:String): Movie = {
    val lines = scala.io.Source.fromInputStream(MovieService.getClass.getResourceAsStream("/data.json")).mkString
    implicit val movieFormat: MovieJsonProtocol.MovieJsonFormat.type = MovieJsonProtocol.MovieJsonFormat
    val movies: List[Movie] = lines.parseJson.convertTo[List[Movie]].filter(p => p != null)
    println("movies = " + movies.length)

    movies(Integer.parseInt(id))
  }


}
