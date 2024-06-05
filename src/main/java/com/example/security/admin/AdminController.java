package com.example.security.admin;

import com.example.security.part.InHouse;
import com.example.security.part.Outsourced;
import com.example.security.part.Part;
import com.example.security.part.PartService;
import com.example.security.product.Product;
import com.example.security.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final PartService partService;
    private final ProductService productService;

    @GetMapping(path = "/part")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping(path = "/part{partId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<Part> getPartById(@PathVariable("partId") Long id) {
        return partService.getPartById(id);
    }

    @PostMapping(path = "/outsourced")
    @PreAuthorize("hasAuthority('admin:create')")
    public void registerNewPart(@RequestBody Outsourced outsourced) {
        partService.addNewPart(outsourced);
    }
    @PostMapping(path = "/inHouse")
    @PreAuthorize("hasAuthority('admin:create')")
    public void registerNewPart(@RequestBody InHouse inHouse) {
        partService.addNewPart(inHouse);
    }


    @PutMapping(path = "/part/{partId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public void updatePart(@PathVariable("partId") Long id,
                           @RequestParam(required=false) String name,
                           @RequestParam(required=false) Double price,
                           @RequestParam(required=false) Integer stock,
                           @RequestParam(required=false) Integer min,
                           @RequestParam(required=false) Integer max,
                           @RequestParam(required=false) Long machineId,
                           @RequestParam(required=false) String companyName) {
        partService.updatePart(id, name, price, stock, min, max, machineId, companyName);
    }

    @DeleteMapping(path = "/part/{partId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deletePart(@PathVariable("partId") Long id) {
        partService.deletePart(id);
    }

    @GetMapping(path = "/product")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/product/{productId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<Product> getProductById(@PathVariable("productId") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(path = "/product")
    @PreAuthorize("hasAuthority('admin:create')")
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @DeleteMapping(path = "/product/{productId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping(path = "/product/{productId}/{partId}")
    @PreAuthorize("hasAuthority('admin:update')")
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
