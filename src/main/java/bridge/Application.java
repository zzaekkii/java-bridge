package bridge;

import bridge.controller.BridgeController;
import bridge.view.InputView;
import bridge.view.OutputView;

public class Application {

    public static void main(String[] args) {
        new BridgeController(
            new InputView(),
            new OutputView()
        ).run();
    }
}
