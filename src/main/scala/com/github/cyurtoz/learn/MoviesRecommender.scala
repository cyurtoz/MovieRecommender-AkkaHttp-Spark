package com.github.cyurtoz.learn

import com.github.cyurtoz.model.{Movie, MovieService, Recommendation}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by cyurtoz on 19/10/17.
  *
  */
object MoviesRecommender {

  val context = {

    val conf = new SparkConf().setAppName("cagatay").setMaster("local[7]")

    new SparkContext(conf)

  }

  val trainn = {
   trainInner(MovieService.movies).collect()
  }

  def trainInner(movies: List[Movie]) = {
    val genres = Utils.extractGenres(movies)
    val doubles = movies.map(Utils.extractMovieFeaturesVector(_, genres))

    val transposed = doubles.transpose

    val vectors = transposed.map(_.toArray).map(Vectors.dense)

    val parVectors = context.parallelize(vectors.take(20), 100)

    parVectors.cache()

    val mat = new RowMatrix(parVectors)

    val similar = mat.columnSimilarities(0.0000005d)

    similar.entries.cache()

    val grouped = similar.entries.groupBy(_.i)

    val gp = grouped.map(e => (e._1, e._2.toList.sortBy(_.value)))
    val gp2 = gp.map(e=> Recommendation(e._1, e._2.reverse.take(5).map(me => me.j)))

    gp2
  }

  def transpose[A](xs: List[Vector[A]]): List[List[A]] = xs.filter(_.nonEmpty) match {
    case Nil => Nil
    case ys: List[Vector[A]] => ys.map {
      _.head
    } :: transpose(ys.map {
      _.tail
    })
  }

}
