package nl.novi.vinylshop.repositories;

import nl.novi.vinylshop.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    //no methods defined because Jpa contains already methods like
    //findAll()
    //findById()
    //save()
    //deleteById()
    //count()
    //existsById()

    //if a custom method is needed like 'findByName' then we need to add like below
    //GenreEntity findByName(String name)

    //custom method that will return the data sorted by Id as Jpa doesn't sort this by default
    List<GenreEntity> findAllByOrderByIdAsc();
}
