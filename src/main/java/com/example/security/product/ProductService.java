package com.example.security.product;

import com.example.security.part.Part;
import com.example.security.part.PartRepository;
import com.example.security.user.Role;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.security.user.Role.USER;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;

    public List<Product> getAllProducts() {
        // Retrieve the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated and retrieve the email
        if (authentication != null && authentication.isAuthenticated()) {
            String userName = authentication.getName();
            Optional<User> userInfo = userRepository.findByEmail(userName);
            // Check if user information is present
            if (userInfo.isPresent()) {
                Role role = userInfo.get().getRole();
                if (role == USER) {
                    Long userId = Long.valueOf(userInfo.get().getId());
                    // Now you can use the userId to fetch user-specific parts
                    return productRepository.findAllByUserId(userId);
                }
                else {
                    return productRepository.findAll();
                }
            } else {
                // User information is not present
                throw new IllegalStateException("User is not present");
            }
        }
        else {
            // Handle unauthenticated access
            throw new IllegalStateException("User is not authenticated");
        }
    }

    public Optional<Product> getProductById(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("product with id " + id + " does not exist");
        }

        return productRepository.findById(id);
    }

    public void addNewProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated and retrieve the email
        if (authentication != null && authentication.isAuthenticated()) {
            String userName = authentication.getName();
            Optional<User> userInfo = userRepository.findByEmail(userName);
            // Check if user information is present
            if (userInfo.isPresent()) {
                product.setUser(userInfo.get());
            } else {
                // User information is not present
                throw new IllegalStateException("User is not present");
            }
        } else {
            // Handle unauthenticated access
            throw new IllegalStateException("User is not authenticated");
        }
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
