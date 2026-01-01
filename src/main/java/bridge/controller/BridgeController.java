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
        PlayGame(bridgeGame);
    }

    private void PlayGame(BridgeGame bridgeGame) {
        int round = 0;
        while (true) {
            // 이동할 칸 입력 받기
            String moveCommand = requestMoveCommand();

            // 이동하고 결과 출력, 실패하면 재시도/종료
            if (!crossBridge(bridgeGame, round, moveCommand)) {
                // 재시도 여부 입력 받기
                boolean wannaRetry = requestWannaRetry();

                // 종료
                if (!wannaRetry) {
                    break;
                }

                // 시도 횟수 증가
                bridgeGame.retry();

                // 라운드 초기화
                round = 0;
            }

            if (round == (bridgeGame.getBridge().size() + 1)) {
                bridgeGame.win();
                break;
            }

            // 라운드 증가
            round += 1;
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

    private boolean crossBridge(BridgeGame bridgeGame, int round, String command) {
        if (bridgeGame.move(round, command)) {
            // 계속 진행
            printBridge(bridgeGame.getBridge(), round, KEEP_GOING);
            return true;
        }

        // 게임 실패
        printBridge(bridgeGame.getBridge(), round, LOSE);
        return false;
    }

    private void printBridge(List<String> bridge, int round, boolean fail) {
        // 위쪽 출력
        outputView.printMap(UP, bridge, round, fail);

        // 아래쪽 출력
        outputView.printMap(DOWN, bridge, round, fail);
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
