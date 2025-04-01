package com.example.demo.controller;

import com.example.demo.dto.ProductPatchRequest;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://kaleidoscopic-pavlova-fd05ac.netlify.app"})
@Tag(name = "Produkti", description = "Produktu pārvaldība")
@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(
            summary = "Atgriež visus produktus",
            description = "Atgriež sarakstu ar visiem produktiem, kas šobrīd reģistrēti datubāzē."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saraksts ar produktiem veiksmīgi iegūts")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @Operation(
            summary = "Atgriež konkrētu produktu pēc ID",
            description = "Atgriež visu informāciju par vienu produktu, identificējot to pēc ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produkts atrasts"),
            @ApiResponse(responseCode = "404", description = "Produkts nav atrasts")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "Produkta ID")
            @PathVariable
            @Min(value = 1, message = "Produkta ID ir jābūt lielākam vai vienādam ar 1")
            Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @Operation(
            summary = "Atgriež produkta atlikumu",
            description = "Atgriež tikai konkrētā produkta atlikuma daudzumu identificējot to pēc ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atlikums veiksmīgi iegūts"),
            @ApiResponse(responseCode = "404", description = "Produkts nav atrasts")
    })
    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> getProductStock(
            @Parameter(description = "Produkta ID")
            @PathVariable
            @Min(value = 1, message = "Produkta ID ir jābūt lielākam vai vienādam ar 1")
            Long id) {
        return ResponseEntity.ok(service.getStock(id));
    }

    @Operation(
            summary = "Izveido jaunu produktu",
            description = "Izveido jaunu produktu, izmantojot nosaukumu, aprakstu, cenu un sākotnējo atlikumu."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produkts veiksmīgi izveidots"),
            @ApiResponse(responseCode = "400", description = "Nepareizi ievadīti dati")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return ResponseEntity.ok(service.saveProduct(product));
    }

    @Operation(
            summary = "Izmaina vienu vai visas produkta detaļas",
            description = "Atļauj mainīt vienu vai vairākus produkta laukus (piemēram, cenu, nosaukumu vai atlikumu), saglabājot pārējo nemainītu."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produkts veiksmīgi atjaunināts"),
            @ApiResponse(responseCode = "404", description = "Produkts nav atrasts")
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> patchProduct(
            @Parameter(description = "Produkta ID")
            @PathVariable
            @Min(value = 1, message = "Produkta ID ir jābūt lielākam vai vienādam ar 1")
            Long id,
            @RequestBody ProductPatchRequest patch) {
        return ResponseEntity.ok(service.patchProduct(id, patch));
    }

    @Operation(
            summary = "Dzēš visus produktus",
            description = "Dzēš visus produktus no datubāzes."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Visi produkti veiksmīgi dzēsti")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteAllProducts() {
        service.deleteAllProducts();
        return ResponseEntity.ok("Visi produkti ir izdzēsti");
    }

    @Operation(
            summary = "Dzēš konkrētu produktu pēc ID",
            description = "Dzēš vienu produktu, identificējot to pēc tā ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produkts veiksmīgi dzēsts"),
            @ApiResponse(responseCode = "404", description = "Produkts nav atrasts")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Produkta ID")
            @PathVariable
            @Min(value = 1, message = "Produkta ID ir jābūt lielākam vai vienādam ar 1")
            Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
