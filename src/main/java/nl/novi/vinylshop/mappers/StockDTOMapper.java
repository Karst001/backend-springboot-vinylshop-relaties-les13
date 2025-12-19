package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.stock.StockRequestDTO;
import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import nl.novi.vinylshop.entities.StockEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockDTOMapper implements DTOMapperInterface<StockResponseDTO, StockRequestDTO, StockEntity> {
    @Override
    public StockResponseDTO mapToDto(StockEntity model) {
        //instantiate
        StockResponseDTO response = new StockResponseDTO();

        //set values
        response.setId(model.getId());
        response.setCondition(model.getCondition());
        response.setPrice(model.getPrice());

        //return single object of model
        return response;
    }

    @Override
    public List<StockResponseDTO> mapToDto(List<StockEntity> models) {
        //instantiate
        List<StockResponseDTO> response = new ArrayList<StockResponseDTO>();

        //add values to List
        for (StockEntity model : models) {
            response.add(mapToDto(model));
        }

        //return List of response
        return response;
    }

    @Override
    public StockEntity mapToEntity(StockRequestDTO stockModel) {
        //instantiate
        StockEntity entity = new StockEntity();

        //set values
        entity.setCondition(stockModel.getCondition());
        entity.setPrice(stockModel.getPrice());

        //return entity
        return entity;
    }
}
