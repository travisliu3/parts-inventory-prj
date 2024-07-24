package com.example.security.part;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/part")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class PartController {

    private final PartService partService;

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping(path = "{partId}")
    public Optional<Part> getPartById(@PathVariable("partId") Long id) {
        return partService.getPartById(id);
    }

    @PostMapping(path = "/outsourced")
    public void registerNewPart(@RequestBody Outsourced outsourced) {
        partService.addNewPart(outsourced);
    }
    @PostMapping(path = "/inHouse")
    public void registerNewPart(@RequestBody InHouse inHouse) {
        partService.addNewPart(inHouse);
    }

    @DeleteMapping(path = "{partId}")
    public void deletePart(@PathVariable("partId") Long id) {
        partService.deletePart(id);
    }

    @PutMapping(path = "{partId}")
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

}
