package com.desenedy.stockcontrol.controller.category;

import com.desenedy.stockcontrol.domain.dto.category.CategoryRequest;
import com.desenedy.stockcontrol.domain.dto.category.CategoryResponse;
import com.desenedy.stockcontrol.domain.dto.category.CategoryUpdate;
import com.desenedy.stockcontrol.domain.entity.Category;
import com.desenedy.stockcontrol.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }


//    search by catgory name
    @GetMapping
    public String GetByName(@RequestParam(required = false) String name,
                            @RequestParam(required = false, defaultValue = "true") Boolean active, Model model) {

        boolean activeFilter = (active == null) ? true : active;

        List<CategoryResponse> responses = service.getAll(name, activeFilter);

        model.addAttribute("categories", responses);

        if (name != null && !name.isBlank() && responses.isEmpty()) {
            model.addAttribute("message", "Category with this name: " + name + ", does not exists.");
        }

        return "category/categories";

    }

//    new category
    @GetMapping("/new")
    public String formCategory(Model model) {

        model.addAttribute("categoryRequest", new CategoryRequest(null, null));

        return "category/form-category";

    }

    @PostMapping("/newCategory")
    public String newCategory(@Valid @ModelAttribute("categoryRequest") CategoryRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return "category/form-category";
        }

        service.create(request);

        return "redirect:/categories";

    }

//    edit category
    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable Long id, Model model) {

        CategoryResponse category = service.getById(id);

        CategoryUpdate update = new CategoryUpdate(
                category.name(), category.description()
        );

        model.addAttribute("edit", update);
        model.addAttribute("id", id);

        return "category/form-edit-category";

    }

    @PostMapping("/editCategory")
    public String editCategory(@RequestParam Long id, @Valid @ModelAttribute("edit") CategoryUpdate update, BindingResult result) {

        if (result.hasErrors()) {
            return "category/form-edit-category";
        }

        service.update(id, update);

        return "redirect:/categories";
    }

//    delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        service.delete(id);

        return "redirect:/categories";

    }

//    details category
    @GetMapping("/details/{id}")
    public String detailsCategory(@PathVariable Long id, Model model) {

        CategoryResponse response = service.getById(id);

        model.addAttribute("details", new Category(response.id(), response.name(), response.description(), response.active()));

        return "category/details";

    }

}
