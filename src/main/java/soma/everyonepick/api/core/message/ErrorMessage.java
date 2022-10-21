package soma.everyonepick.api.core.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    public static String UNAUTHORIZED = "접근 권한이 없습니다.";
    public static String NOT_EXIST_USER = "존재하지 않는 사용자입니다.";
    public static String NOT_EXIST_GROUP_ALBUM = "존재하지 않는 단체앨범입니다.";
    public static String NOT_EXIST_HOST = "멤버에 방장이 포함되어있지 않습니다.";
    public static String NOT_EXIST_PHOTO = "존재하지 않는 사진입니다.";
    public static String NOT_EXIST_PICK = "존재하지 않는 사진선택 작업입니다.";
    public static String NOT_EXIST_PICK_INFO = "존재하지 않는 사진선택 정보입니다.";
    public static String NOT_EXIST_RESULT_PHOTO = "존재하지 않는 합성완료사진입니다.";

    public static String NOT_HOST = "방장 권한이 없습니다.";
    public static String NOT_MEMBER = "해당 방의 멤버가 아닙니다.";

    @Deprecated
    public static String REDUNDANT_TITLE = "이미 존재하는 단체앨범 제목입니다.";
    public static String REDUNDANT_USER_GROUP_ALBUM = "이미 존재하는 멤버입니다.";
    public static String REDUNDANT_SELECT = "이미 선택한 멤버 입니다.";

    public static String HOST_MUST_STAY = "현재 정책상 방장 혼자 남았을 땐 단체앨범을 나갈 수 없습니다. (변경예정)";
    public static String WRONG_PHOTO = "현재 단체앨범에 속하지 않는 사진입니다.";
    public static String TIME_IS_UP = "타임아웃이 종료되어 더이상 선택 할 수 없습니다.";
    public static String CAN_NOT_FIND_FACE = "얼굴을 검출에 실패했습니다.";
    public static String MANY_FACE = "여러 얼굴들이 검출되었습니다.";
}
