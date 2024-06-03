package com.example.security.part;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final InHouseRepository inHouseRepository;
    private final OutsourcedRepository outsourcedRepository;

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Optional<Part> getPartById(Long id) {
        boolean exists = partRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("part with id " + id + " does not exist");
        }

        return partRepository.findById(id);
    }

    public void addNewPart(Part part) {
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
