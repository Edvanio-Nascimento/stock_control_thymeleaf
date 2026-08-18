package com.desenedy.stockcontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String HomePage() {

        return "home";

    }

//    product

    @GetMapping("/products")
    public String HomeProduct() {

        return "product/product";
    }

//    category

    @GetMapping("/categories")
    public String HomeCategory() {

        return "category/category";

    }

//    employee

    @GetMapping("/employees")
    public String homeEmployee() {

        return "employee/employee";

    }

//    movement
    @GetMapping("/movements")
    public String homeMovement() {

        return "movement/movement";

    }

}
