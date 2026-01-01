package bridge.view;

import bridge.domain.BridgeGame;
import bridge.domain.Position;

import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    private static final String BEGIN_BRIDGE = "[ ";
    private static final String IN_PROGRESS = " | ";
    private static final String END_BRIDGE = " ]";

    private static final String CORRECT = "O";
    private static final String INCORRECT = "X";
    private static final String EMPTY = " ";

    private static final String GAME_CLEAR = "성공";
    private static final String GAME_OVER = "실패";

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    public void printGameStart() {
        System.out.println("다리 건너기 게임을 시작합니다.\n");
    }

    public void printBridgeSizeRequest() {
        System.out.println("다리의 길이를 입력해주세요.");
    }

    /**
     * X인지 여부는 결국 마지막에서 판단하면 됨
     * 실패가 아니면 초기 다리 상태랑 같은 위치에 'O'만 출력하면 됨
     * 실패 했으면 마지막 상태는 반대 위치에 'X' 출력
     */
    public void printMap(Position position, List<String> bridge, int round, boolean success) {
        // 다리 시작 표시
        System.out.print(BEGIN_BRIDGE);

        // 중간 다리 표시
        int len = round - 1;
        for (int r = 0; r < len; r++) {
            System.out.print(isChosen(position, bridge.get(r)) + IN_PROGRESS);
        }

        // 마지막 상태 표시 (성공인지 아닌지)
        System.out.print(lastStatus(bridge.get(round).equals(position.getValue()), success));

        // 다리 끝 표시
        System.out.println(END_BRIDGE);
    }

    // 중간 상태 - `O` 아니면 ` `
    private static String isChosen(Position position, String bridgePosition) {
        if (bridgePosition.equals(position.getValue())) {
            return CORRECT;
        }
        return EMPTY;
    }

    /**
     * 같은 포지션이면 성공인지 실패인지에 따라 `O`, `X`가 나뉨
     * 같은 포지션이 아니라면 ` `
     */
    private String lastStatus(boolean samePosition, boolean success) {
        if (!samePosition) {
            return EMPTY;
        }

        if (success) {
            return CORRECT;
        }

        return INCORRECT;
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printFinalMap() {
        System.out.println("최종 게임 결과");
    }

    public void printErrorMessage(String message) {
        System.out.println(ERROR_MESSAGE_PREFIX + message);
    }

    /**
     * 필요치 않을 수도 있는데
     * 요구사항에서 예시 출력을 보면,
     * 다리 길이를 입력받고 나서 빈 줄 하나를 출력함
     */
    public void printGap() {
        System.out.println();
    }

    public void printCommandRequest() {
        System.out.println("이동할 칸을 선택해주세요. (위: U, 아래: D)");
    }

    public void printRetryRequest() {
        System.out.println("게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)");
    }

    public void printResult(BridgeGame bridgeGame) {
        System.out.println("게임 성공 여부: " + gameResult(bridgeGame.hasWon()));
        System.out.println("총 시도한 횟수:" + bridgeGame.getTryCount());
    }

    private String gameResult(boolean gameClear) {
        if (gameClear) {
            return GAME_CLEAR;
        }
        return GAME_OVER;
    }
}
