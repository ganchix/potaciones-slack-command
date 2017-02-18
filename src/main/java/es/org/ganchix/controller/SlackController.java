package es.org.ganchix.controller;

import es.org.ganchix.domain.Person;
import es.org.ganchix.domain.Restaurant;
import es.org.ganchix.dto.SlackAttachments;
import es.org.ganchix.dto.request.SlackRequest;
import es.org.ganchix.dto.response.SlackResponse;
import es.org.ganchix.repository.PersonRepository;
import es.org.ganchix.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/slack")
public class SlackController {

    @Value("${slack.id}")
    String slackToken;

    @Value("#{'${restaurant.list}'.split(',')}")
    List<String> restaurantList;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @RequestMapping(value = "/command/potaciones", consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
    public SlackResponse executeCommandPotaciones(@ModelAttribute SlackRequest slackRequest) {
        // Validate token
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
                        .map(rest -> {
                            String attachement = StringUtils.capitalize(rest.getName()) + " (" + rest.getPersonsToGo().size() + ")" + " : "
                                    + rest.getPersonsToGo().parallelStream().map(prsn ->
                                    (!StringUtils.isEmpty(prsn.getIcon()) ? prsn.getIcon() : prsn.getName())).collect(Collectors.joining(", "));
                            return new SlackAttachments(attachement);

                        }).collect(Collectors.toList()));
                slackResponse.setResponse_type("in_channel");
            } else {
                slackResponse.setText("Pero que tipo de restaurante es " + slackRequest.getText() + ".Sólo puedes elegir entre los siguientes restaurantes: "
                        + restaurantList.stream().collect(Collectors.joining(", ")) + " Si quieres alguno mas notifica a @rafita ");
                slackResponse.setResponse_type("ephemeral");

            }

        }
        return slackResponse;
    }

}
