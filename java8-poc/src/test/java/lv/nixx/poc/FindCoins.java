package lv.nixx.poc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FindCoins {

    @Test
    public void findCoinsCombinationTest() {
        assertThat(findCoins(10.51), containsInAnyOrder("200:5", "50:1", "1:1"));
        assertThat(findCoins(0.1), containsInAnyOrder("10:1"));
        assertThat(findCoins(10.00), containsInAnyOrder("200:5"));
        assertTrue(findCoins(0).isEmpty());
    }

    private Collection<String> findCoins(double sum) {

        int[] possibleCoins = new int[]{
                200, 100, 50, 20, 10, 5, 2, 1
        };

        double s = sum * 100;
        int pos = 0;

        Collection<String> response = new ArrayList<>();

        while (s > 0) {
            int d = possibleCoins[pos];
            int d1 = (int) s / d;

            if (d1 > 0) {
                s = s - (d * d1);
//                System.out.println(d + ":" + d1 + "s:" + s );
                response.add( d  + ":" + d1);
            }
            pos++;
        }

        return response;
    }



}
