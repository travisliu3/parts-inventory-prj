package com.example.security.part;

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
public class PartService {

    private final PartRepository partRepository;
    private final InHouseRepository inHouseRepository;
    private final OutsourcedRepository outsourcedRepository;
    private final UserRepository userRepository;

    public List<Part> getAllParts() {
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
                    return partRepository.findAllByUserId(userId);
                }
                else {
                    return partRepository.findAll();
                }
            } else {
                // User information is not present
                throw new IllegalStateException("User is not present");
            }
        } else {
            // Handle unauthenticated access
            throw new IllegalStateException("User is not authenticated");
        }
    }

    public Optional<Part> getPartById(Long id) {
        boolean exists = partRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("part with id " + id + " does not exist");
        }

        return partRepository.findById(id);
    }

    public void addNewPart(Part part) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated and retrieve the email
        if (authentication != null && authentication.isAuthenticated()) {
            String userName = authentication.getName();
            Optional<User> userInfo = userRepository.findByEmail(userName);
            // Check if user information is present
            if (userInfo.isPresent()) {
                part.setUser(userInfo.get());
            } else {
                // User information is not present
                throw new IllegalStateException("User is not present");
            }
        } else {
            // Handle unauthenticated access
            throw new IllegalStateException("User is not authenticated");
        }
        partRepository.save(part);
    }

    public void deletePart(Long id) {
        boolean exists = partRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("part with id " + id + " does not exist");
        }

        partRepository.deleteById(id);
    }

    @Transactional
    public void updatePart(Long id, String name, Double price, Integer stock, Integer min, Integer max, Long machineId, String companyName) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "part with id " + id + " does not exist"
                ));

        if (name != null && !name.isEmpty() && !Objects.equals(part.getName(), name)) {
            part.setName(name);
        }
        if (stock != null && stock > 0) {
            part.setStock(stock);
        }
        if (price != null && price > 0) {
            part.setPrice(price);
        }
        if (min != null && min > 0) {
            part.setMin(min);
        }
        if (max != null && max > 0) {
            part.setMax(max);
        }

        boolean exists = inHouseRepository.existsById(id);
        if (exists) {
            InHouse inHouse = inHouseRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "inHouse part with id " + id + " does not exist"
                    ));
            if (machineId != null) {
                inHouse.setMachineId(machineId);
            }
        }

        exists = outsourcedRepository.existsById(id);
        if (exists) {
            Outsourced outsourced = outsourcedRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "outsourced part with id " + id + " does not exist"
                    ));
            if (companyName != null && !companyName.isEmpty() && !Objects.equals(outsourced.getCompanyName(), companyName)) {
                outsourced.setCompanyName(companyName);
            }
        }
    }
}
