package coins.state;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private Coins state1 = new Coins(7, 3); // the original initial state

    private final Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }


    @Test
    void isGoalTest() {
        assertFalse(state1.isGoal());
        assertTrue(state2.isGoal());
    }

    @Test
    void canBeFlipedTest() {
        BitSet bitSet = new BitSet(8);
        assertFalse(state1.canFlip(bitSet));
        bitSet.set(0, 2);
        assertTrue(state1.canFlip(bitSet));
        assertTrue(state2.canFlip(bitSet));
        bitSet = new BitSet(6);
        assertFalse(state1.canFlip(bitSet));
        assertFalse(state1.canFlip(bitSet));
        bitSet = new BitSet(5);
        bitSet.set(0, 5);
        assertTrue(state1.canFlip(bitSet));
        assertTrue(state2.canFlip(bitSet));
        bitSet = new BitSet(4);
        bitSet.set(1, 4);
        assertFalse(state1.canFlip(bitSet));
        assertFalse(state2.canFlip(bitSet));
    }

    @Test
    void IfCanBeFlipedIsFalseAndIfLengthIsLessThanNOrCardinalityIsLessThanMTest() {
        BitSet bitSet = new BitSet(10);
        assertFalse(state1.canFlip(bitSet));
        bitSet = new BitSet(6);
        bitSet.set(1);
        assertFalse(state1.canFlip(bitSet));
    }

    @Test
    void flip() {
        BitSet bitSet = new BitSet(5);
        bitSet.set(0, 4);
        state1 = new Coins(7, 2);
        state1.flip(bitSet);
        assertEquals(bitSet, state1.getCoins());
        BitSet bitSet2 = new BitSet(3);
        bitSet2.set(0, 1);
        state1.flip(bitSet2);
        BitSet bitSet3 = new BitSet(4);
        bitSet3.set(3);
        assertEquals(bitSet3, state1.getCoins());
    }

    @Test
    void generateFlipsTest() {
        long pieces = CombinatoricsUtils.binomialCoefficient(13, 9);
        List<BitSet> flips = Coins.generateFlips(15, 8);
        assertEquals((int) pieces, flips.size());
        pieces = CombinatoricsUtils.binomialCoefficient(7, 2);
        flips = Coins.generateFlips(8, 3);
        assertEquals((int) pieces, flips.size());
    }

    @Test
    void generateFlipsThrowingExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0, 7));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(10, 0));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(4, 5));
    }

    @Test
    void getFlips() {
        List<BitSet> flips = Coins.generateFlips(7, 2);
        assertEquals(flips.size(), state1.getFlips().size());
    }
}