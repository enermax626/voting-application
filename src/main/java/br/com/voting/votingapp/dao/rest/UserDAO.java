package br.com.voting.votingapp.dao.rest;

import static br.com.voting.votingapp.exception.ExceptionMessage.RESPONSE_DATA_NOT_FOUND;
import static br.com.voting.votingapp.exception.ExceptionMessage.USER_NOT_FOUND;

import br.com.voting.votingapp.dto.input.UserValidationInputDTO;
import br.com.voting.votingapp.exception.NotFoundException;
import br.com.voting.votingapp.exception.RestTemplateRequestException;
import br.com.voting.votingapp.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Component
@RequiredArgsConstructor
public class UserDAO {

    private final RestTemplateUtil restTemplateUtil;

    @Value("${rest.api.user.validate-user}")
    private String validateUserUrl;

    public UserValidationInputDTO validateUserById(String userId) {
        String formattedUrl = validateUserUrl.replace("{userId}", userId);
        ParameterizedTypeReference<UserValidationInputDTO> objectParameterizedTypeReference = ParameterizedTypeReference.forType(UserValidationInputDTO.class);

        try {
            return restTemplateUtil.doGetRes(formattedUrl, null, null, objectParameterizedTypeReference)
                    .orElseThrow(() -> new RestTemplateRequestException(RESPONSE_DATA_NOT_FOUND.getMessage()));
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(USER_NOT_FOUND.getMessage());
            }
            throw ex;
        }
    }

}
