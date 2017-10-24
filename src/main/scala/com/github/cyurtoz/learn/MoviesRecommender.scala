package com.github.cyurtoz.learn

import com.github.cyurtoz.model.{Movie, MovieService, Recommendation}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by cyurtoz on 19/10/17.
  *
  */
object MoviesRecommender {

  val context = {
    val conf = new SparkConf().setAppName("cagatay").setMaster("local[7]")
      .set("spark.driver.memory", "8g")
    new SparkContext(conf)
  }

  val model = {
    trainInner(MovieService.movies).collect()
  }

  def trainInner(movies: List[Movie]): RDD[Recommendation] = {
    val genres = Utils.extractGenres(movies)
    val countries = Utils.extractCountries(movies)
    val languages = Utils.extractLanguages(movies)

    val doubles = movies.map(Utils.extractMovieFeaturesVector(_, genres, countries, languages))

    val transposed = doubles.transpose

    val vectors = transposed.map(_.toArray).map(Vectors.dense)

    val parVectors = context.parallelize(vectors, 10)

    parVectors.cache()

    val mat = new RowMatrix(parVectors)

    val similar = mat.columnSimilarities(0.000001d)

    similar.entries.cache()

    val grouped = similar.entries.groupBy(_.i)

    val gp = grouped.map(e => (e._1, e._2.toList.sortBy(_.value)))
    val asdr = gp.collect()
    val gp2 = gp.map(e => Recommendation(e._1, e._2.reverse.take(5).map(me => me.j)))

    gp2
  }
}
