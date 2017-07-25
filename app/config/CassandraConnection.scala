package config

import com.outworkers.phantom.builder.query.CreateQuery
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint}
import com.outworkers.phantom.dsl.{Database, KeySpace, default_time_to_live, gc_grace_seconds, read_repair_chance}
import domain.{Todo, Todos, Users}

/**
  * Created by sujit on 7/18/17.
  */
object Defaults {
  val connector: CassandraConnection = ContactPoint.local.keySpace("outworkers")
}

class MyDatabase(override val connector: CassandraConnection) extends Database[MyDatabase](connector) {

  object users extends Users with Connector

  object todos extends Todos with Connector {
    override def autocreate(space: KeySpace): CreateQuery.Default[Todos, Todo] = {
      create.ifNotExists()(space)
    }
  }

}

object MyDatabase extends MyDatabase(Defaults.connector)


