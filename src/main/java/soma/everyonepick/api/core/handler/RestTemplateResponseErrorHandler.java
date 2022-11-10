package soma.everyonepick.api.core.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.EveryonepickException;

import java.io.IOException;

import static soma.everyonepick.api.core.message.ErrorMessage.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String message = "Error";

        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            try {
                JsonNode data = objectMapper.readTree(response.getBody());
                log.info("BAD_REQUEST: " + data.asText());

                if (data.has("message")) {
                    message = data.get("message").asText();
                }
            }  catch (JsonProcessingException e) {
                throw new EveryonepickException("AI server JSON 응답 파싱 에러", e);
            }

            if (message.equals("No face")) {
                throw new BadRequestException(CAN_NOT_FIND_FACE);
            } else if (message.equals("Too many face")) {
                throw new BadRequestException(MANY_FACE);
            } else if (message.equals("The number of faces detected is less than the user_cnt")) {
                throw new BadRequestException(WRONG_FACE_NUM);
            } else {
                throw new BadRequestException(message);
            }
        } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error("INTERNAL_SERVER_ERROR: " + response.getBody());
        }
    }
}
