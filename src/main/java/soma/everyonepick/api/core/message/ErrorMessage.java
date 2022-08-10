package soma.everyonepick.api.core.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    public static String UNAUTHORIZED = "접근 권한이 없습니다.";
    public static String NOT_EXIST_USER = "존재하지 않는 사용자입니다.";
    public static String NOT_EXIST_GROUP_ALBUM = "존재하지 않는 단체앨범입니다.";
    public static String NOT_EXIST_HOST = "멤버에 방장이 포함되어있지 않습니다.";
    public static String REDUNDANT_TITLE = "이미 존재하는 단체앨범 제목입니다.";
    public static String REDUNDANT_USER_GROUP_ALBUM = "이미 존재하는 멤버입니다.";
}
