package nl.novi.vinylshop.services;

import nl.novi.vinylshop.dtos.stock.StockRequestDTO;
import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import nl.novi.vinylshop.entities.StockEntity;
import nl.novi.vinylshop.mappers.StockDTOMapper;
import nl.novi.vinylshop.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockDTOMapper stockDTOMapper;

    public StockService(StockRepository stockRepository, StockDTOMapper stockDTOMapper) {
        this.stockRepository = stockRepository;
        this.stockDTOMapper = stockDTOMapper;
    }


    public List<StockResponseDTO> findAllStock() {
        //return the StockResponseDTO via the DTO mapper to the controller
        return stockDTOMapper.mapToDto(stockRepository.findAll());
    }

    public StockResponseDTO findStockById(Long id) {
        //check if records exists
        StockEntity result = getStockEntityById(id);  //this step can be skipped when using stored procedures

        //return the entity via the mapper
        if (result != null) {
            return stockDTOMapper.mapToDto(result);
        }
        return null;
    }


    public StockResponseDTO createStock(StockRequestDTO stockDTO) {
        //map the stockDTO to the stockEntity
        StockEntity result = stockDTOMapper.mapToEntity(stockDTO);

        //call the repo and save the result
        result = stockRepository.save(result);

        //return the stockEntity via the DTO mapper to the controller
        return stockDTOMapper.mapToDto(result);
    }


    public StockResponseDTO updateStock(Long id, StockRequestDTO requestDto) {
        //check if records exists
        StockEntity result = getStockEntityById(id);  //this step can be skipped when using stored procedures

        //update values
        result.setCondition(requestDto.getCondition());
        result.setPrice(requestDto.getPrice());

        //save to database
        result = stockRepository.save(result);

        //return entity to controller via de DTO
        return stockDTOMapper.mapToDto(result);
    }


    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }


    //private function to check if entity exists
    private StockEntity getStockEntityById(Long id) {
        Optional<StockEntity> stockEntity = stockRepository.findById(id);

        //check if Genre is present, if not return null
        return stockEntity.orElse(null);
    }
}
