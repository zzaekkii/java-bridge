package bridge.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

class BridgeGameTest {

    @Test
    void 이동하려는_다리_위치가_같아야_참() {
        BridgeNumberGenerator numberGenerator = new TestNumberGenerator(newArrayList(1, 0, 0));
        BridgeMaker bridgeMaker = new BridgeMaker(numberGenerator);
        List<String> bridge = bridgeMaker.makeBridge(3);

        BridgeGame testGame = new BridgeGame(bridge);
        assertThat(testGame.move("U")).isTrue();
    }

    @Test
    void 이동하려는_다리_위치가_다르면_거짓() {
        BridgeNumberGenerator numberGenerator = new TestNumberGenerator(newArrayList(1, 0, 0));
        BridgeMaker bridgeMaker = new BridgeMaker(numberGenerator);
        List<String> bridge = bridgeMaker.makeBridge(3);

        BridgeGame testGame = new BridgeGame(bridge);
        assertThat(testGame.move("D")).isFalse();
    }

    @Test
    void 재시도_시_라운드가_초기화되고_시도횟수가_증가한다() {
        BridgeGame testGame = new BridgeGame(new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(3));

        testGame.retry();

        // 라운드 초기화
        assertThat(testGame.getRound()).isEqualTo(0);

        // 시도횟수 증가
        assertThat(testGame.getTryCount()).isEqualTo(2);
    }

    @Test
    void 게임이_이기면_won필드를_true로_바꾼다() {
        BridgeGame testGame = new BridgeGame(new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(3));

        testGame.win();

        assertThat(testGame.hasWon()).isTrue();
    }

    @Test
    void 게임을_계속_진행하면_라운드가_증가한다() {
        BridgeGame testGame = new BridgeGame(new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(3));

        testGame.nextRound();

        assertThat(testGame.getRound()).isEqualTo(1);
    }

    @Test
    void 진행_라운드가_다리_길이와_같으면_게임_종료() {
        final int bridgeSize = 10;
        BridgeGame testGame = new BridgeGame(new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(bridgeSize));

        for (int round = 1; round < bridgeSize; round++) {
            testGame.nextRound();
        }

        assertThat(testGame.isEnd()).isTrue();
    }

    static class TestNumberGenerator implements BridgeNumberGenerator {

        private final List<Integer> numbers;

        TestNumberGenerator(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        public int generate() {
            return numbers.remove(0);
        }
    }
}