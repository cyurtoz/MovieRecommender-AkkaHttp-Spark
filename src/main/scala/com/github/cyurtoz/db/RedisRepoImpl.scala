package com.github.cyurtoz.db

import akka.util.ByteString
import com.github.cyurtoz.model.Recommendation
import redis.{ByteStringFormatter, RedisClient}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by cyurtoz on 22/10/17.
  *
  */
object RedisRepoImpl {

  implicit val akkaSystem = akka.actor.ActorSystem()

  val redis = RedisClient("localhost", 6379)

  implicit val byteStringFormatter = new ByteStringFormatter[Recommendation] {
    def serialize(data: Recommendation): ByteString = {
      ByteString(data.movieId + "|" + data.recommendationIds.mkString("#"))
    }

    def deserialize(bs: ByteString): Recommendation = {
      val split = bs.utf8String.split('|').toList
      val recomes = split(1).split("#").map(_.toLong)
      Recommendation(split.head.toLong, recomes.toList)
    }
  }

  def save(recom:Recommendation): Unit = {
    val asd = redis.lpush(String.valueOf(recom.movieId), recom)
  }

  def get(movieId:Long): Unit = {
    redis.lpop(String.valueOf(movieId))
  }

}
