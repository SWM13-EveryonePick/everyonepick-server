package soma.everyonepick.api.core.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    // Authorization
    public static String UNAUTHORIZED = "접근 권한이 없습니다.";

    // User
    public static String NOT_EXIST_USER = "존재하지 않는 사용자입니다.";

    // Coach
    public static String NOT_EXIST_COACH = "존재하지 않는 코치입니다.";
    public static String USER_NOT_COACH = "사용자가 코치가 아닙니다.";

    // Lecture
    public static String NOT_EXIST_LECTURE = "존재하지 않는 클래스 일정 입니다.";

    // LectureInfo
    public static String NOT_EXIST_LECTURE_INFO = "존재하지 않는 클래스-info 입니다.";

    // Form
    public static String NOT_EXIST_FORM = "존재하지 않는 신청서입니다.";
    public static String FORM_STATE_NOT_VALID = "유효하지 않은 state 값입니다.";
    public static String FORM_IS_NOT_BELONG_TO_THIS_COACH = "이 신청서는 로그인한 사용자(코치) 앞으로 작성되지 않았습니다.";
}
