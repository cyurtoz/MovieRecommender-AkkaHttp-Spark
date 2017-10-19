package com.github.cyurtoz.model

import spray.json.{DefaultJsonProtocol, JsArray, JsNumber, JsString, JsValue, RootJsonFormat}

/**
  * Created by cyurtoz on 10/10/17.
  *
  */
object MovieJsonProtocol extends DefaultJsonProtocol {

  implicit object MovieJsonFormat extends RootJsonFormat[Movie] {

    var idIncrementer = 0

    def write(c: Movie) =
      JsArray(JsString(c.director_name),
        JsNumber(c.director_facebook_likes),
        JsNumber(c.duration),
        JsNumber(c.budget))

    def read(value: JsValue) = {

      val asd = value.asJsObject.getFields(
        "color",
        "director_name",
        "num_critic_for_reviews",
        "duration",
        "director_facebook_likes",
        "actor_3_facebook_likes",
        "actor_2_name",
        "actor_1_facebook_likes",
        "gross",
        "genres",
        "actor_1_name",
        "movie_title",
        "num_voted_users",
        "cast_total_facebook_likes",
        "actor_3_name",
        "facenumber_in_poster",
        "plot_keywords",
        "movie_imdb_link",
        "num_user_for_reviews",
        "language",
        "country",
        "content_rating",
        "budget",
        "title_year",
        "actor_2_facebook_likes",
        "imdb_score",
        "aspect_ratio",
        "movie_facebook_likes"
      )

      val toDouble = (f: JsValue) => f match {
        case JsNumber(numberval) => numberval.toDouble
        case _ => 0d
      }

      val toString = (f: JsValue) => f match {
        case JsString(strval) => strval
        case _ => ""
      }

      val toLong = (f: JsValue) => f match {
        case JsNumber(numberval) => numberval.longValue()
        case _ => 0L
      }

      val toInt = (f: JsValue) => f match {
        case JsNumber(numberval) => numberval.intValue()
        case _ => 0
      }

      val fields = value.asJsObject.fields

      idIncrementer = idIncrementer + 1

      Movie(
        idIncrementer,
        fields.get("color").map(toString).get,
        fields.get("director_name").map(toString).get,
        fields.get("num_critic_for_reviews").map(toLong).get,
        fields.get("duration").map(toDouble).get,
        fields.get("director_facebook_likes").map(toLong).get,
        fields.get("actor_3_facebook_likes").map(toLong).get,
        fields.get("actor_2_name").map(toString).get,
        fields.get("actor_1_facebook_likes").map(toLong).get,
        fields.get("gross").map(toLong).get,
        fields.get("genres").map(toString).get,
        fields.get("actor_1_name").map(toString).get,
        fields.get("movie_title").map(toString).get,
        fields.get("num_voted_users").map(toLong).get,
        fields.get("cast_total_facebook_likes").map(toLong).get,
        fields.get("actor_3_name").map(toString).get,
        fields.get("facenumber_in_poster").map(toDouble).get,
        fields.get("plot_keywords").map(toString).get,
        fields.get("movie_imdb_link").map(toString).get,
        fields.get("num_user_for_reviews").map(toLong).get,
        fields.get("language").map(toString).get,
        fields.get("country").map(toString).get,
        fields.get("content_rating").map(toString).get,
        fields.get("budget").map(toLong).get,
        fields.get("title_year").map(toInt).get,
        fields.get("actor_2_facebook_likes").map(toLong).get,
        fields.get("imdb_score").map(toDouble).get,
        fields.get("aspect_ratio").map(toDouble).get,
        fields.get("movie_facebook_likes").map(toLong).get
      )



    }
  }

}
