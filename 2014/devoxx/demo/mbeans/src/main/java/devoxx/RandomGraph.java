package devoxx;

import java.util.*;

/**
 * @author roland
 * @since 26.06.13
 */
public class RandomGraph implements RandomGraphMBean {

    int spreads[] = {
            5,
            30,
            10,
            4,
            3
    };


    Map<Integer,Integer> lastVals = new HashMap<Integer, Integer>();
    Map<Integer,Random> randomGens = new HashMap<Integer, Random>();

    public int nextValue(int series) {
        Integer lastVal = lastVals.get(series);
        int val;
        if (lastVal == null) {
            val = getRandom(series);
        } else {
            val = getRandom(series,lastVal);
        }
        lastVals.put(series,val);
        return val;
    }

    public int getSample1() {
        return nextValue(0);
    }

    public int getSample2() {
        return nextValue(1);
    }

    public int getSample3() {
        return nextValue(2);
    }

    public int getSample4() {
        return nextValue(3);
    }

    public int getSample5() {
        return nextValue(4);
    }

    private int getRandom(int pSeries) {
        return getRandom(pSeries,0);
    }

    private int getRandom(int pSeries,int pLastVal) {
        Random random = randomGens.get(pSeries);
        if (random == null) {
            random = new Random();
            randomGens.put(pSeries,random);
        }
        int spread = spreads[pSeries % spreads.length];
        int delta = random.nextInt(spread * 2 + 1) - spread;
        return pLastVal + delta;
    }
}
