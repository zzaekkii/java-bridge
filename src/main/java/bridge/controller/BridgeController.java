package bridge.controller;

import bridge.domain.BridgeGame;
import bridge.domain.BridgeMaker;
import bridge.domain.BridgeNumberGenerator;
import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.List;

import static bridge.domain.Position.*;

public class BridgeController {

    private static final boolean SUCCESS = true;
    private static final boolean LOSE = false;

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
        PlayGame(bridgeGame);

        // 게임 결과 출력
        outputView.printFinalMapIntro();
        printBridge(bridgeGame.getBridge(), bridgeGame.getRound(), bridgeGame.hasWon());

        outputView.printResult(bridgeGame);
    }

    private void PlayGame(BridgeGame bridgeGame) {
        while (true) {
            String moveCommand = requestMoveCommand();

            // 이동하고 결과 출력, 실패하면 재시도/종료
            if (!crossBridge(bridgeGame, moveCommand)) {
                if (!requestWannaRetry()) {
                    break;
                }
                bridgeGame.retry();
            }

            if (bridgeGame.isEnd()) {
                bridgeGame.win();
                break;
            }

            bridgeGame.nextRound();
        }
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
        while (true) {
            outputView.printBridgeSizeRequest();

            try {
                int bridgeSize = inputView.readBridgeSize();
                outputView.printGap();
                return bridgeSize;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String requestMoveCommand() {
        while (true) {
            // 이동할 칸 입력 요청 문구 출력
            outputView.printCommandRequest();

            try {
                return inputView.readMoving();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean crossBridge(BridgeGame bridgeGame, String command) {
        if (bridgeGame.move(command)) {
            // 계속 진행
            printBridge(bridgeGame.getBridge(), bridgeGame.getRound(), SUCCESS);
            return true;
        }

        // 게임 실패
        printBridge(bridgeGame.getBridge(), bridgeGame.getRound(), LOSE);
        return false;
    }

    private void printBridge(List<String> bridge, int round, boolean success) {
        // 위쪽 출력
        outputView.printMap(UP, bridge, round, success);

        // 아래쪽 출력
        outputView.printMap(DOWN, bridge, round, success);

        // 아래 빈 줄 추가
        outputView.printGap();
    }

    private boolean requestWannaRetry() {
        while (true) {
            // 재시도/종료 의사 물어보는 문구 출력
            outputView.printRetryRequest();

            try {
                // 재시도/종료 의사 입력 받기 (내부에서 검증됨)
                return inputView.readGameCommand();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
