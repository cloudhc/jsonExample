package controllers

/**
  * Created by bast on 2016-04-07.
  */

import models.Product
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class Products extends Controller {
  def list = Action {
    val productCodes = Product.findAll.map(_.ean)
    Ok(Json.toJson(productCodes))
  }
  
  def details(ean: Long) = Action {
    Product.findByEan(ean).map { product =>
      Ok(Json.toJson(product))
    }.getOrElse(NotFound)
  }
}

