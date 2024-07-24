package com.example.security.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "{productId}")
    public Optional<Product> getProductById(@PathVariable("productId") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping(path = "{productId}/{partId}")
    public void updateProduct(@PathVariable("productId") Long id,
                              @PathVariable("partId") Long partId,
                           @RequestParam(required=false) String name,
                           @RequestParam(required=false) Double price,
                           @RequestParam(required=false) Integer stock,
                           @RequestParam(required=false) Integer min,
                           @RequestParam(required=false) Integer max) {
        productService.updateProduct(id, name, price, stock, min, max, partId);
    }

}
