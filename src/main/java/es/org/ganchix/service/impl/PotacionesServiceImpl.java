package es.org.ganchix.service.impl;

import es.org.ganchix.domain.Person;
import es.org.ganchix.domain.Restaurant;
import es.org.ganchix.dto.SlackAttachments;
import es.org.ganchix.dto.request.SlackRequest;
import es.org.ganchix.dto.response.SlackResponse;
import es.org.ganchix.repository.PersonRepository;
import es.org.ganchix.repository.RestaurantRepository;
import es.org.ganchix.service.PotacionesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rafael Ríos on 18/02/17.
 */
@Slf4j
@Service
public class PotacionesServiceImpl implements PotacionesService {

    @Value("${slack.id}")
    String slackToken;

    @Value("#{'${restaurant.list}'.split(',')}")
    List<String> restaurantList;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public SlackResponse processPotacion(SlackRequest slackRequest) {

        SlackResponse slackResponse = new SlackResponse();
        if (slackRequest.getToken().equals(slackToken)) {
            slackRequest.setText(slackRequest.getText().toLowerCase());
            if (restaurantList.contains(slackRequest.getText())) {
                Person person = personRepository.findOne(slackRequest.getUser_id());
                if (person == null) {
                    person = personRepository.save(new Person(slackRequest.getUser_id(), slackRequest.getUser_name()));
                }

                Restaurant restaurantSelectedByPersonPreviously = restaurantRepository.findByPersonIsInRestaurant(person.getId());
                if (restaurantSelectedByPersonPreviously != null) {
                    if (!restaurantSelectedByPersonPreviously.getName().equals(slackRequest.getText())) {
                        restaurantSelectedByPersonPreviously.getPersonsToGo().remove(person);
                        restaurantRepository.save(restaurantSelectedByPersonPreviously);
                        Restaurant restaurantSelected = restaurantRepository.findOne(slackRequest.getText());
                        if (restaurantSelected == null) {
                            restaurantSelected = new Restaurant();
                            restaurantSelected.setName(slackRequest.getText());
                        }
                        restaurantSelected.getPersonsToGo().add(person);
                        restaurantRepository.save(restaurantSelected);
                    }
                } else {
                    Restaurant restaurantSelected = restaurantRepository.findOne(slackRequest.getText());
                    if (restaurantSelected == null) {
                        restaurantSelected = new Restaurant();
                        restaurantSelected.setName(slackRequest.getText());
                        restaurantSelected = restaurantRepository.save(restaurantSelected);
                    }
                    restaurantSelected.getPersonsToGo().add(person);
                    restaurantRepository.save(restaurantSelected);

                }
                slackResponse.setText(person.getName() + " ha seleccionado " + StringUtils.capitalize(slackRequest.getText()) + " asi van las potaciones: ");
                List<Restaurant> findAll = restaurantRepository.findAll();
                slackResponse.setAttachments(findAll
                        .parallelStream()
                        .map(restaurant -> {
                            String attachement = StringUtils.capitalize(restaurant.getName()) + " (" + restaurant.getPersonsToGo().size() + ")" + " : "
                                    + restaurant.getPersonsToGo()
                                    .parallelStream()
                                    .map(personOfRestaurant ->
                                            (!StringUtils.isEmpty(personOfRestaurant.getIcon()) ? personOfRestaurant.getIcon() : personOfRestaurant.getName()))
                                    .collect(Collectors.joining(", "));
                            return new SlackAttachments(attachement);

                        }).collect(Collectors.toList()));
                slackResponse.setResponse_type("in_channel");
            } else {
                slackResponse.setText("Pero que tipo de restaurante es " + slackRequest.getText() + ".Sólo puedes elegir entre los siguientes restaurantes: "
                        + restaurantList.stream().collect(Collectors.joining(", ")) + " Si quieres alguno mas notifica a @rafita ");
                slackResponse.setResponse_type("ephemeral");

            }

        } else {
            log.error("Slack request is not correct {}", slackRequest);
            throw new RuntimeException("Token is not correct");
        }
        return slackResponse;
    }

}
