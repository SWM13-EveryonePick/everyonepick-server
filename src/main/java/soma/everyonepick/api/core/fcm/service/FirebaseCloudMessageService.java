package soma.everyonepick.api.core.fcm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.core.fcm.dto.PushMessage;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.DeviceTokenService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    @Value(value = "${fcm.google_application_credentials_path}")
    private String path;
    @Value(value = "${fcm.type}")
    private String type;
    @Value(value = "${fcm.project_id}")
    private String project_id;
    @Value(value = "${fcm.private_key_id}")
    private String private_key_id;
    @Value(value = "${fcm.private_key}")
    private String private_key;
    @Value(value = "${fcm.client_email}")
    private String client_email;
    @Value(value = "${fcm.client_id}")
    private String client_id;
    @Value(value = "${fcm.auth_uri}")
    private String auth_uri;
    @Value(value = "${fcm.token_uri}")
    private String token_uri;
    @Value(value = "${fcm.auth_provider_x509_cert_url}")
    private String auth_provider_x509_cert_url;
    @Value(value = "${fcm.client_x509_cert_url}")
    private String client_x509_cert_url;
    private final DeviceTokenService deviceTokenService;

    @PostConstruct
    public void initFCM() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();

        private_key = private_key.replace(" ", "\n");
        private_key = "-----BEGIN PRIVATE KEY-----\n" + private_key + "\n-----END PRIVATE KEY-----\n";

        map.put("type", type);
        map.put("project_id", project_id);
        map.put("private_key_id", private_key_id);
        map.put("private_key", private_key);
        map.put("client_email", client_email);
        map.put("client_id", client_id);
        map.put("auth_uri", auth_uri);
        map.put("token_uri", token_uri);
        map.put("auth_provider_x509_cert_url", auth_provider_x509_cert_url);
        map.put("client_x509_cert_url", client_x509_cert_url);

        try {
            File file = new File(path);
            objectMapper.writeValue(file, map);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(file)))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }

        } catch (FileNotFoundException e) {
            log.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());
            throw new EveryonepickException(e);
        } catch (IOException e) {
            log.error("FirebaseOptions IOException" + e.getMessage());
            throw new EveryonepickException(e);
        }
    }

    /**
     * 초대된 사용자들에게 푸시 메시지를 보낸다.
     * @param pushMessage
     */
    public void sendPushToGroup(PushMessage pushMessage) {
        List<User> users = pushMessage.getUsers();
        GroupAlbum groupAlbum = pushMessage.getGroupAlbum();

        List<String> registrationTokens = users.stream()
                .map(s-> deviceTokenService.getDeviceToken(s).getFcmDeviceToken())
                .collect(Collectors.toList());

        MulticastMessage message = MulticastMessage.builder()
                .putData("groupAlbumId", groupAlbum.getId().toString())
                .setNotification(
                        Notification.builder()
                                .setTitle(pushMessage.getMessageData().getTitle())
                                .setBody(pushMessage.getMessageData().getContent())
                                .build()
                )
                .addAllTokens(registrationTokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            log.info(response.getSuccessCount() + " messages were sent successfully");

            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        // The order of responses corresponds to the order of the registration tokens.
                        failedTokens.add(registrationTokens.get(i));
                    }
                }
                log.error("List of tokens that caused failures: " + failedTokens);
            }
        }catch (FirebaseMessagingException e) {
            throw new EveryonepickException("Firebase Message Send Error",e);
        }

    }
}
