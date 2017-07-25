package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import config.MyDatabase
import domain.Todo._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

/**
  * Created by sujit on 7/18/17.
  */
@Singleton
class ToDoController @Inject()(implicit ec: ExecutionContext, implicit val ex: ExecutionContextExecutor, controllerComponents: ControllerComponents) extends AbstractController(controllerComponents) {

  def getTodos = Action.async {
    MyDatabase.todos.getAll map {
      todo => Ok(Json.toJson(todo))
    }
  }

  def getTodo(id: String) = Action.async {
    MyDatabase.todos.getById(UUID.fromString(id)) map {
      todo => Ok(Json.toJson(todo.get))
    }
  }

}
