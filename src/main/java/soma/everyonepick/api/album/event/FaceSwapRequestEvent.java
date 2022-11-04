package soma.everyonepick.api.album.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soma.everyonepick.api.album.dto.FaceSwapRequestDto;

@AllArgsConstructor
@Getter
public class FaceSwapRequestEvent {
    private FaceSwapRequestDto faceSwapRequestDto;
}
