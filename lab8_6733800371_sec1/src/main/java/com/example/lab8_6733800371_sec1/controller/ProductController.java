package com.example.lab8_6733800371_sec1.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.lab8_6733800371_sec1.model.Product;
import com.example.lab8_6733800371_sec1.model.ProductDetail;
import com.example.lab8_6733800371_sec1.model.Review;
import com.example.lab8_6733800371_sec1.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Product product = new Product();
        product.setDetail(new ProductDetail());
        product.setDiscountType("NONE");
        product.getReviews().add(new Review());
        model.addAttribute("product", product);
        return "products/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "เพิ่มสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product.getDetail() == null) {
            product.setDetail(new ProductDetail());
        }
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product,
                          RedirectAttributes redirectAttributes) {
        Product existing = productService.getProductById(id);
        product.setReviews(existing.getReviews() != null ? existing.getReviews() : new ArrayList<>());

        if (product.getDetail() != null && existing.getDetail() != null) {
            product.getDetail().setId(existing.getDetail().getId());
        }
        productService.updateProduct(id, product);
        redirectAttributes.addFlashAttribute("message", "แก้ไขสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "ลบสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }
}