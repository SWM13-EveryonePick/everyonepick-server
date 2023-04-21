package soma.everyonepick.api.core.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.core.fcm.dto.PushMessage;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.DeviceTokenService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final DeviceTokenService deviceTokenService;

    @PostConstruct
    public void initFCM() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
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
                .filter(User::getIsPushActive)
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
