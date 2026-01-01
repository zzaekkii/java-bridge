package bridge.controller;

import bridge.domain.BridgeGame;
import bridge.domain.BridgeMaker;
import bridge.domain.BridgeNumberGenerator;
import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.List;

import static bridge.domain.Position.*;

public class BridgeController {

    private static final boolean KEEP_GOING = false;
    private static final boolean LOSE = true;

    private final InputView inputView;
    private final OutputView outputView;
    private final BridgeNumberGenerator bridgeNumberGenerator;

    public BridgeController(InputView inputView, OutputView outputView, BridgeNumberGenerator bridgeNumberGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.bridgeNumberGenerator = bridgeNumberGenerator;
    }

    public void run() {
        /// 게임 시작
        BridgeGame bridgeGame = gameStart();

        /// 게임 진행
        int round = 0;
        while (!bridgeGame.hasWon()) {
            // 이동할 칸 입력 받기
            String command = requestCommand();

            // 이동하고 결과 출력
            crossBridge(bridgeGame, round, command);
        }
    }

    private void crossBridge(BridgeGame bridgeGame, int round, String command) {
        if (bridgeGame.move(round, command)) {
            // 계속 진행
            printBridge(bridgeGame.getBridge(), round, KEEP_GOING);
            return;
        }
        printBridge(bridgeGame.getBridge(), round, LOSE);
    }

    private BridgeGame gameStart() {
        // 게임 시작 문구 출력
        outputView.printGameStart();

        // 다리 길이 입력 받기
        int bridgeSize = requestBridgeSize();

        // 다리 생성
        return new BridgeGame(
            new BridgeMaker(bridgeNumberGenerator).makeBridge(bridgeSize)
        );
    }

    private int requestBridgeSize() {
        int bridgeSize;
        while (true) {
            // 다리 길이 입력 요청 문구 출력
            outputView.printBridgeSizeRequest();

            try {
                // 다리 길이 입력 받기 (내부에서 검증됨)
                bridgeSize = inputView.readBridgeSize();

                // 빈 줄 한 줄 출력
                outputView.printGap();

                return bridgeSize;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String requestCommand() {
        while (true) {
            try {
                return inputView.readGameCommand();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 현재 라운드 수만큼 진행 상태 출력
     * 위부터 출력한 뒤, 아래 출력
     * 시작은 '[ '로 출력
     * round 전까지 다리 상태에 따라 ' ' 또는 'O' 출력
     * 마지막 원소가 아니면 뒤에 ' | ' 출력
     * 끝은 ' ]'로 출력
     * <p>
     * X인지 여부는 결국 마지막에서 판단하면 됨
     * 실패가 아니면 초기 다리 상태랑 같은 위치에 'O'만 출력하면 됨
     * 실패 했으면 마지막 상태는 반대 위치에 'X' 출력
     */
    private void printBridge(List<String> bridge, int round, boolean fail) {
        // 위쪽 출력
        outputView.printMap(UP, bridge, round, fail);

        // 아래쪽 출력
        outputView.printMap(DOWN, bridge, round, fail);
    }
}
