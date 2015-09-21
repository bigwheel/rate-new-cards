package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Card

class CardsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Card
  override def resourcesName = "cards"
  override def resourceName = "card"

  override def resourcesBasePath = s"/resource/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("card_number") is required & numeric & intValue,
    paramKey("image_url") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "card_number" -> ParamType.Int,
    "image_url" -> ParamType.String
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("card_number") is required & numeric & intValue,
    paramKey("image_url") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "card_number" -> ParamType.Int,
    "image_url" -> ParamType.String
  )

}
