package es.org.ganchix.repository;

import es.org.ganchix.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    @Query(value = "{'personsToGo': { $elemMatch: { 'id': ?0 } } }")
    Restaurant findByPersonIsInRestaurant(String id);

}
