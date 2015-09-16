package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Card(
  id: Long,
  name: String,
  imageUrl: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Card extends SkinnyCRUDMapper[Card] with TimestampsFeature[Card] {
  override lazy val tableName = "cards"
  override lazy val defaultAlias = createAlias("c")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Card]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Card]): Card = new Card(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    imageUrl = rs.get(rn.imageUrl),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
