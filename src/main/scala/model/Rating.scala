package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Rating(
  id: Long,
  userId: Long,
  cardId: Long,
  score: Int,
  summary: String,
  description: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Rating extends SkinnyCRUDMapper[Rating] with TimestampsFeature[Rating] {
  override lazy val tableName = "ratings"
  override lazy val defaultAlias = createAlias("r")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Rating]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Rating]): Rating = new Rating(
    id = rs.get(rn.id),
    userId = rs.get(rn.userId),
    cardId = rs.get(rn.cardId),
    score = rs.get(rn.score),
    summary = rs.get(rn.summary),
    description = rs.get(rn.description),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
