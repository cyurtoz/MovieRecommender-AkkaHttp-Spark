package com.github.cyurtoz.learn

import com.github.cyurtoz.model.Movie
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
  * Created by cyurtoz on 16/10/17.
  *
  */
object Utils {

  def splitTestAndTraining(movies: RDD[Movie]): (RDD[Movie], RDD[Movie]) = {
    val split = movies.randomSplit(Array(0.7, 0.3))
    (split(0), split(1))
  }

  def extractGenres(moviesRDD: List[Movie]) = {
    moviesRDD.flatMap(_.genres.split('|')).distinct.toArray
  }


  def extractMovieFeaturesVector(mv: Movie, genres: Array[String]): Array[Double] = {
    val mvGenres = genres.map(x => if (mv.genres.contains(x)) 1d else 0d)
    val arr = List(
      mv.imdb_score,
      mv.movie_facebook_likes,
      mv.num_critic_for_reviews,
      mv.num_user_for_reviews,
      mv.num_voted_users,
      mv.cast_total_facebook_likes.toDouble,
      mv.director_facebook_likes.toDouble,
      mv.gross
    ).toArray

    arr ++ mvGenres

  }
}
