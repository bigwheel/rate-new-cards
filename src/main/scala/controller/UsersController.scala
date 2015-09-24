package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.User

class UsersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = User
  override def resourcesName = "users"
  override def resourceName = "user"

  override def resourcesBasePath = s"resource/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("user_name") is required & maxLength(512),
    paramKey("o_auth_type") is required & maxLength(512),
    paramKey("o_auth_id") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "user_name" -> ParamType.String,
    "o_auth_type" -> ParamType.String,
    "o_auth_id" -> ParamType.String,
    "is_admin" -> ParamType.Boolean
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("user_name") is required & maxLength(512),
    paramKey("o_auth_type") is required & maxLength(512),
    paramKey("o_auth_id") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "user_name" -> ParamType.String,
    "o_auth_type" -> ParamType.String,
    "o_auth_id" -> ParamType.String,
    "is_admin" -> ParamType.Boolean
  )

}
