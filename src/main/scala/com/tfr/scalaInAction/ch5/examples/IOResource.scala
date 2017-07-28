package com.tfr.scalaInAction.ch5.examples

import java.io.File

import com.tfr.scalaInAction.ch5.examples.Pure.{Resource, ResourceLocator}

import scala.io.Source

/**
  * Created by Erik on 7/28/2017.
  */
case class IOResource(name:String) extends Resource {

  implicit val ioResourceLocator: ResourceLocator = name => IOResource(name)

  def exists = new File(name).exists
  def contents = Source.fromFile(name).getLines.toList
  def contentLength = Source.fromFile(name).count(x => true)
  def dispose() = {}

}
