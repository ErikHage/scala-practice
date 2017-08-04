package com.tfr.scalaInAction.ch12.example.remoteActor

import java.net.URL

import scala.io.Source

/**
  * Created by Erik on 8/3/2017.
  */
case class FileToCount(url: String) {

  def countWords = {
    Source.fromURL(new URL(url)).getLines.foldRight(0)(_.split(" ").length + _)
  }

}
