package bridge.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {

    private static final String[] MOVE_COMMANDS = {"D", "U"};
    private static final String[] RETRY_COMMANDS = {"R", "Q"};
    private static final String RETRY = "R";


    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        String input = Console.readLine();

        nullCheck(input);

        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("숫자를 입력해야 합니다.");
        }

        try {
            int bridgeSize = Integer.parseInt(input);

            validateBounds(bridgeSize);

            return bridgeSize;
        } catch (NumberFormatException e) { // 끝나고 이부분 위 matches()랑 중복인지 확인하기. parseInt()에서 오류 터질 가능성.
            throw new IllegalArgumentException("숫자를 입력해야 합니다.");
        }
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public String readMoving() {
        String input = Console.readLine();

        nullCheck(input);

        if (!input.matches("[A-Z]")) {
            throw new IllegalArgumentException("형식이 맞지 않습니다.");
        }

        if (checkCommand(input, MOVE_COMMANDS)) {
            throw new IllegalArgumentException("U 또는 D만 입력 가능합니다.");
        }

        return input;
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public boolean readGameCommand() {
        String input = Console.readLine();

        nullCheck(input);

        if (!input.matches("[A-Z]")) {
            throw new IllegalArgumentException("형식이 맞지 않습니다.");
        }

        if (checkCommand(input, RETRY_COMMANDS)) {
            throw new IllegalArgumentException("R 또는 Q만 입력 가능합니다.");
        }

        return RETRY.equals(input);
    }

    private static void nullCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력값이 존재하지 않습니다.");
        }
    }

    private static void validateBounds(int bridgeSize) {
        if (20 < bridgeSize || bridgeSize < 3) {
            throw new IllegalArgumentException("3 이상 20 이하의 숫자를 입력해야 합니다.");
        }
    }

    private boolean checkCommand(String input, String[] commands) {
        for (String command: commands) {
            if (command.equals(input)) {
                return true;
            }
        }
        return false;
    }
}
