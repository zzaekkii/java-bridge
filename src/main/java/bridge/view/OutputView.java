package bridge.view;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    
    public void printGameStart() {
        System.out.println("다리 건너기 게임을 시작합니다.\n");
    }

    public void printBridgeSizeRequest() {
        System.out.println("다리의 길이를 입력해주세요.");
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap() {
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult() {
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
}
