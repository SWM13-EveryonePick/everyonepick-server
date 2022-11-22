package soma.everyonepick.api.album.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.service.PickInfoService;
import soma.everyonepick.api.album.service.PickService;

@Component
@RequiredArgsConstructor
public class PickValidator {
    private final PickService pickService;
    private final PickInfoService pickInfoService;

    /**
     * Pick을 검증해서 PinkInfoUser 데이터가 없으면 비활성화.
     * @param pick
     * @return Boolean
     */
    @Deprecated
    public Boolean pickValidator(Pick pick) {
        PickInfoUser pickInfoUser = pickInfoService.getPickInfo(pick).orElse(null);

        if (pickInfoUser == null) {
            pickService.deletePick(pick);
            return false;
        }
        return true;
    }
}
