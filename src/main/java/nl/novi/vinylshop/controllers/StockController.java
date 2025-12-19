package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.stock.StockRequestDTO;
import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;
    private final UrlHelper urlHelper;

    //constructor
    public StockController(StockService stockService, UrlHelper urlHelper) {
        this.stockService = stockService;
        this.urlHelper = urlHelper;
    }

    //mappings
    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> findAllStock() {
        //load the DTO with all genres via the service
        List<StockResponseDTO> stock = stockService.findAllStock();

        //return the List
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> findStockById(@PathVariable Long id) {
        //load the DTO with one stock via the service
        StockResponseDTO stock = stockService.findStockById(id);

        //return the found object or null
        if (stock != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(stock, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<StockResponseDTO> createStock(@Valid @RequestBody StockRequestDTO stockInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //create a genre via the service and store result in ResponseDTO
        StockResponseDTO newStock = stockService.createStock(stockInput);

        //return the new response and add Id to header
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newStock.getId())).body(newStock);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody StockRequestDTO stockInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //update stock via the service and store result in ResponseDTO
        StockResponseDTO updated = stockService.updateStock(id, stockInput);

        //return the updated object or null
        if (updated != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
