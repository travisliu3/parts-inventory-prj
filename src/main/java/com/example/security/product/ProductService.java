package com.example.security.product;

import com.example.security.part.Part;
import com.example.security.part.PartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PartRepository partRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("product with id " + id + " does not exist");
        }

        return productRepository.findById(id);
    }

    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("part with id " + id + " does not exist");
        }

        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(Long id, String name, Double price, Integer stock, Integer min, Integer max, Long partId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "product with id " + id + " does not exist"
                ));

        if (name != null && !name.isEmpty() && !Objects.equals(product.getName(), name)) {
            product.setName(name);
        }
        if (stock != null && stock > 0) {
            product.setStock(stock);
        }
        if (price != null && price > 0) {
            product.setPrice(price);
        }
        if (min != null && min > 0) {
            product.setMin(min);
        }
        if (max != null && max > 0) {
            product.setMax(max);
        }

        if(partId != null) {
            Part part = partRepository.findById(partId)
                    .orElseThrow(() -> new IllegalStateException(
                            "part with id " + id + " does not exist"
                    ));
            part.setProduct(product);
        }
    }
}
