package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import org.springframework.stereotype.Component;

@Component
public class AlbumExtendedDTOMapper extends AlbumDTOMapper {

    private final StockDTOMapper stockDTOMapper;

    public AlbumExtendedDTOMapper(GenreDTOMapper genreDTOMapper, PublisherDTOMapper publisherDTOMapper,
            ArtistDTOMapper artistDTOMapper, StockDTOMapper stockDTOMapper) {
        super(genreDTOMapper, publisherDTOMapper, artistDTOMapper);
        this.stockDTOMapper = stockDTOMapper;
    }

    @Override
    public AlbumExtendedResponseDTO mapToDto(AlbumEntity model) {
        AlbumExtendedResponseDTO dto = mapToDto(model, new AlbumExtendedResponseDTO());

        if (model.getStockItems() != null && !model.getStockItems().isEmpty()) {
            dto.setStockItems(stockDTOMapper.mapToDto(model.getStockItems()));
        }

        return dto;
    }
}
