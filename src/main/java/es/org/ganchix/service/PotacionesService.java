package es.org.ganchix.service;

import es.org.ganchix.dto.request.SlackRequest;
import es.org.ganchix.dto.response.SlackResponse;

/**
 * Created by Rafael Ríos on 18/02/17.
 */
public interface PotacionesService {

    SlackResponse processPotacion(SlackRequest slackRequest);
}
