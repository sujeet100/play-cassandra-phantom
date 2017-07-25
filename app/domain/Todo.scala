package domain

import com.outworkers.phantom.dsl.{DateTime, PartitionKey, RootConnector, UUID, _}
import play.api.libs.json._

import scala.concurrent.Future

/**
  * Created by sujit on 7/18/17.
  */
case class Todo(id: UUID, title: String, description: String, due: DateTime) {

}

object Todo {
  implicit val todoWrites: OWrites[Todo] = Json.writes[Todo]
  implicit object DefaultJodaDateWrites extends Writes[org.joda.time.DateTime] {
    def writes(d: org.joda.time.DateTime): JsValue = JsNumber(d.getMillis)
  }
}

abstract class Todos extends Table[Todos, Todo] with RootConnector{

  object id extends UUIDColumn with PartitionKey

  object title extends StringColumn

  object description extends StringColumn

  object due extends DateTimeColumn

  def getById(id: UUID): Future[Option[Todo]] = {
    select.where(_.id eqs id).one()
  }

  def getAll: Future[Seq[Todo]] = {
    select.fetch()
  }

}
