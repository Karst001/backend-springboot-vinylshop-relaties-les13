package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.artist.ArtistRequestDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.ArtistEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistDTOMapper implements DTOMapperInterface<ArtistResponseDTO, ArtistRequestDTO, ArtistEntity> {
    @Override
    public ArtistResponseDTO mapToDto(ArtistEntity model) {
        //instantiate
        ArtistResponseDTO response = new ArtistResponseDTO();

        //set values
        response.setId(model.getId());
        response.setName(model.getName());
        response.setBiography(model.getBiography());

        if (model.getAlbums() != null && !model.getAlbums().isEmpty()) {
                response.setAlbumIds(model.getAlbums().stream().map(AlbumEntity::getId).toList()
            );
        }

        //return single object of model
        return response;
    }

    @Override
    public List<ArtistResponseDTO> mapToDto(List<ArtistEntity> models) {
        //instantiate
        List<ArtistResponseDTO> response = new ArrayList<ArtistResponseDTO>();

        //add values to List
        for (ArtistEntity model : models) {
            response.add(mapToDto(model));
        }

        //return List of response
        return response;
    }

    @Override
    public ArtistEntity mapToEntity(ArtistRequestDTO artistModel) {
        //instantiate
        ArtistEntity entity = new ArtistEntity();

        //set values
        entity.setName(artistModel.getName());
        entity.setBiography(artistModel.getBiography());

        //return entity
        return entity;
    }
}
