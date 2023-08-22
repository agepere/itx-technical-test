package com.itx.technicalTest.acceptance;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerAcceptanceTest {

    @Test()
    @Order(7)
    void getAllProductsOk() {
        when().get("/api/products")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("CONTRASTING LACE T-SHIRT"));
    }

    @Test()
    @Order(7)
    void getAllProductsOkOrderBySalesScoreAsc() {
        when().get("/api/products?salesScoreRatio=-1")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("PLEATED T-SHIRT"));
    }

    @Test()
    @Order(7)
    void getAllProductsPagination() {
        when().get("/api/products?page=0&size=3")
                .then()
                .statusCode(200)
                .body("", hasSize(3));
    }

    @Test()
    @Order(7)
    void getAllProductsPagination2() {
        when().get("/api/products?page=0&size=4")
                .then()
                .statusCode(200)
                .body("", hasSize(4));
    }


    @Test
    void getErrorUriNotFound() {
        when().get("/api/product1s")
                .then()
                .statusCode(404);


    }

    @Test
    @Order(1)
    void createProduct1() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                               {
                                 "name": " V-NECH BASIC SHIRT",
                                 "type": "TSHIRT",
                                 "sales": 100,
                                 "stockLines": [
                                   {
                                     "size": "S",
                                     "stock": 4
                                   },
                                   {
                                     "size": "L",
                                     "stock": 0
                                   },
                                   {
                                     "size": "M",
                                     "stock": 9
                                   }
                                 ]
                               }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201)
                .body("name", equalTo(" V-NECH BASIC SHIRT"))
                .body("sales", equalTo(100))
                .body("type", equalTo("TSHIRT"))
                .body("stockLines", hasSize(3))
                .body("stockLines.find { it.size == 'S' }.stock", equalTo(4))
                .body("stockLines.find { it.size == 'M' }.stock", equalTo(9))
                .body("stockLines.find { it.size == 'L' }.stock", equalTo(0));

    }

    @Test
    @Order(2)
    void createProduct2() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                               "name": " V-NECH BASIC SHIRT",
                               "type": "TSHIRT",
                               "sales": 50,
                               "stockLines": [
                                 {
                                   "size": "S",
                                   "stock": 35
                                 },
                                 {
                                   "size": "L",
                                   "stock": 9
                                 },
                                 {
                                   "size": "M",
                                   "stock": 9
                                 }
                               ]
                             }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(3)
    void createProduct3() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                               "name": "RAISED PRINT T-SHIRT",
                               "type": "TSHIRT",
                               "sales": 80,
                               "stockLines": [
                                 {
                                   "size": "S",
                                   "stock": 20
                                 },
                                 {
                                   "size": "L",
                                   "stock": 20
                                 },
                                 {
                                   "size": "M",
                                   "stock": 2
                                 }
                               ]
                             }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(4)
    void createProduct4() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                                "name": "PLEATED T-SHIRT",
                                "type": "TSHIRT",
                                "sales": 3,
                                "stockLines": [
                                  {
                                    "size": "S",
                                    "stock": 25
                                  },
                                  {
                                    "size": "L",
                                    "stock": 10
                                  },
                                  {
                                    "size": "M",
                                    "stock": 30
                                  }
                                ]
                              }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(5)
    void createProduct5() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                               "name": "CONTRASTING LACE T-SHIRT",
                               "type": "TSHIRT",
                               "sales": 650,
                               "stockLines": [
                                 {
                                   "size": "S",
                                   "stock": 0
                                 },
                                 {
                                   "size": "L",
                                   "stock": 0
                                 },
                                 {
                                   "size": "M",
                                   "stock": 1
                                 }
                               ]
                             }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(6)
    void createProduct6() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                               "name": "SLOGAN T-SHIRT",
                               "type": "TSHIRT",
                               "sales": 20,
                               "stockLines": [
                                 {
                                   "size": "S",
                                   "stock": 9
                                 },
                                 {
                                   "size": "L",
                                   "stock": 5
                                 },
                                 {
                                   "size": "M",
                                   "stock": 2
                                 }
                               ]
                             }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(1)
    void badRequestRepeteadSize() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                             {
                               "name": "SLOGAN T-SHIRT",
                               "type": "TSHIRT",
                               "sales": 20,
                               "stockLines": [
                                 {
                                   "size": "S",
                                   "stock": 9
                                 },
                                 {
                                   "size": "S",
                                   "stock": 5
                                 },
                                 {
                                   "size": "M",
                                   "stock": 2
                                 }
                               ]
                             }
                        """).
                when().post("/api/products")
                .then()
                .statusCode(400);

    }

}