package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Rating

class RatingsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Rating
  override def resourcesName = "ratings"
  override def resourceName = "rating"

  override def resourcesBasePath = s"/resource/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("user_id") is required & numeric & longValue,
    paramKey("card_id") is required & numeric & longValue,
    paramKey("score") is required & numeric & intValue,
    paramKey("summary") is required & maxLength(512),
    paramKey("description") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "user_id" -> ParamType.Long,
    "card_id" -> ParamType.Long,
    "score" -> ParamType.Int,
    "summary" -> ParamType.String,
    "description" -> ParamType.String
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("user_id") is required & numeric & longValue,
    paramKey("card_id") is required & numeric & longValue,
    paramKey("score") is required & numeric & intValue,
    paramKey("summary") is required & maxLength(512),
    paramKey("description") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "user_id" -> ParamType.Long,
    "card_id" -> ParamType.Long,
    "score" -> ParamType.Int,
    "summary" -> ParamType.String,
    "description" -> ParamType.String
  )

}
