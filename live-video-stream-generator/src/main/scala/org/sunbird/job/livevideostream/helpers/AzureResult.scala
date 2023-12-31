package org.sunbird.job.livevideostream.helpers

import org.apache.commons.lang3.StringUtils

import scala.collection.immutable.HashMap

object AzureResult extends Result {

  override def getSubmitJobResult(response: MediaResponse): Map[String, AnyRef] = {
    val result = response.result
    val output: Map[String, AnyRef] = result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("outputs", List).asInstanceOf[List[Map[String, AnyRef]]].head
    HashMap[String, AnyRef](
      "job" -> HashMap[String, AnyRef](
        "id" -> result.getOrElse("name", "").toString,
        "status" -> output.getOrElse("state", "").toString.toUpperCase(),
        "submittedOn" -> result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("created", "").toString,
        "lastModifiedOn" -> result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("lastModified", "").toString
      )
    )

  }

  override def getJobResult(response: MediaResponse): Map[String, AnyRef] = {
    val result = response.result
    val output: Map[String, AnyRef] = result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("outputs", List).asInstanceOf[List[Map[String, AnyRef]]].head

    HashMap[String, AnyRef](
      "job" -> HashMap[String, AnyRef](
        "id" -> result.getOrElse("name", "").toString,
        "status" -> output.getOrElse("state", "").toString.toUpperCase(),
        "submittedOn" -> result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("created", "").toString,
        "lastModifiedOn" -> result.getOrElse("properties", Map).asInstanceOf[Map[String, AnyRef]].getOrElse("lastModified", "").toString,
        "error" -> {
          if (StringUtils.equalsIgnoreCase(output.getOrElse("state", "").toString.toUpperCase(),"ERROR")) {
            val errorMap: Map[String, AnyRef] = output.getOrElse("error", Map).asInstanceOf[Map[String, AnyRef]]
            Map[String, String](
              "errorCode" -> errorMap.getOrElse("code", "").toString,
              "errorMessage" -> errorMap.getOrElse("details", List).asInstanceOf[List[Map[String, AnyRef]]].head.getOrElse("message", "").toString
            )
          } else {
            null
          }
        }
      )
    )
  }

  override def getCancelJobResult(response: MediaResponse): Map[String, AnyRef] = {
    null
  }

  override def getListJobResult(response: MediaResponse): Map[String, AnyRef] = {
    null
  }

}
