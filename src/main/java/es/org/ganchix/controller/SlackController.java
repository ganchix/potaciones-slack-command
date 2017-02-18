package es.org.ganchix.controller;

import es.org.ganchix.dto.request.SlackRequest;
import es.org.ganchix.dto.response.SlackResponse;
import es.org.ganchix.service.PotacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/slack")
public class SlackController {

    @Autowired
    PotacionesService potacionesService;

    @RequestMapping(value = "/command/potaciones", consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
    public SlackResponse executeCommandPotaciones(@ModelAttribute SlackRequest slackRequest) {
        return potacionesService.processPotacion(slackRequest);
    }

}
