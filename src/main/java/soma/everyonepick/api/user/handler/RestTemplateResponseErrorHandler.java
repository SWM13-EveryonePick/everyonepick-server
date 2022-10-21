package soma.everyonepick.api.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import soma.everyonepick.api.core.exception.BadRequestException;

import java.io.IOException;

import static soma.everyonepick.api.core.message.ErrorMessage.CAN_NOT_FIND_FACE;
import static soma.everyonepick.api.core.message.ErrorMessage.MANY_FACE;

@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String message = objectMapper.readTree(response.getBody()).get("message").asText();
            if (message.equals("No face")) {
                throw new BadRequestException(CAN_NOT_FIND_FACE);
            } else if (message.equals("Too many face")) {
                throw new BadRequestException(MANY_FACE);
            }
        }
    }
}
