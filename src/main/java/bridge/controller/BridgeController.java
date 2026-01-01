package bridge.controller;

import bridge.view.InputView;
import bridge.view.OutputView;

public class BridgeController {

    private final InputView inputView;
    private final OutputView outputView;

    public BridgeController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // 게임 시작
        // 게임 시작 문구 출력
        outputView.printGameStart();

        // 다리 길이 입력 받기
        int bridgeSize = requestBridgeSize();
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
}
