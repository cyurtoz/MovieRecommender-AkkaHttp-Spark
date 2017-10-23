package com.github.cyurtoz.model

/**
  * Created by cyurtoz on 22/10/17.
  *
  */
case class Recommendation(
                                 movieId: Long,
                                 recommendationIds: List[Long]
                               )
