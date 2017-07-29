package com.tfr.scalaInAction.weKanban.application

import com.tfr.scalaInAction.weKanban.model.Story
import com.tfr.scalaInAction.weKanban.view._

import scalaz.Scalaz._
import scalaz.http.Slinky._
import scalaz.http._
import scalaz.http.request._
import scalaz.http.response._
import scalaz.http.servlet._

/**
  * Created by Erik on 7/27/2017.
  */
final class WeKanbanApplication extends StreamStreamServletApplication {

  import Request._
  import Response._
  implicit val charset = UTF8

  def param_!(name:String)(implicit request: Request[Stream]): String =
    (request | name).getOrElse(List[Char]()).mkString("")
  def param(name:String)(implicit request: Request[Stream]): String =
    (request ! name).getOrElse(List[Char]()).mkString("")

  def handle(implicit request: Request[Stream],
             servletRequest: HttpServletRequest): Option[Response[Stream]] = {
    request match {
      case MethodParts(GET, "card" :: "create" :: Nil) =>
        println("Matched GET /card/create")
        Some(OK(ContentType, "text/html") << strict << CreateStory(param("message")))

      case MethodParts(POST, "card" :: "save" :: Nil) =>
        println("Matched POST /card/create")
        Some(saveStory)

      case MethodParts(GET, "kanban" :: "board" :: Nil) =>
        println("Matched GET /kanban/board")
        Some(OK(ContentType, "text/html") << transitional << KanbanBoard())

      case MethodParts(POST, "card" :: "move" :: Nil) =>
        println("Matched POST /card/move")
        Some(moveCard)

      case _ =>
        None
    }
  }

  private def saveStory(implicit request: Request[Stream], servletRequest: HttpServletRequest) = {
    println("Saving story")
    val title  = param_!("title")
    val number = param_!("storyNumber")
    println("Title=" + title + ", Number=" + number)
    Story(number, title).save() match {
      case Right(message) =>
        println("Successful save: redirecting to /card/create")
        redirects[Stream, Stream]("/card/create", ("message", message))
      case Left(error) =>
        println("Error when saving story")
        OK(ContentType, "text/html") << strict << CreateStory(error.toString)
    }
  }

  private def moveCard(implicit request: Request[Stream],
                       servletRequest: HttpServletRequest) = {
    println("Moving story")
    val number = param_!("storyNumber")
    val toPhase = param_!("phase")
    println("Moving story #" + number + " to phase " + toPhase)
    val story = Story.findByNumber(number)
    story.moveTo(toPhase) match {
      case Right(message) => OK(ContentType, "text/html") << strict << message
      case Left(error) => OK(ContentType, "text/html") << strict << error.getMessage
    }
  }

  val application = new ServletApplication[Stream, Stream] {
    def application(implicit servlet: HttpServlet, servletRequest: HttpServletRequest, request: Request[Stream]): Response[Stream] = {
      println("New request: " + request.parts)
      def found(x: Iterator[Byte]) : Response[Stream] = OK << x.toStream
      handle | HttpServlet.resource(found, NotFound.xhtml)
    }
  }

}
