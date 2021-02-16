package lv.nixx.poc.features.java15.npe;

import org.junit.Test;

public class NpeSample {

    @Test(expected = NullPointerException.class)
    public void npeWithDescriptionSample() {

        Customer c = null;

        try {
            c.getName();
        } catch (NullPointerException npe) {
            System.err.println(npe);
            throw npe;
        }

    }


    class Customer {

        String getName() {
            return "name";
        }

    }

}


