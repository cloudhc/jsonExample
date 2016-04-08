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
  
  def save(ean: Long) = Action(parse.json) { request =>
    val productJson = request.body
    val product = productJson.as[Product]
    try {
      Product.save(product)
      Ok("Saved")
    }
    catch {
      case e:IllegalArgumentException =>
        BadRequest("Product not found")
    }
  }
}

