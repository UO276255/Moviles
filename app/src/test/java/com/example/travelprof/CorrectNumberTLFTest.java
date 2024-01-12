package com.example.travelprof;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CorrectNumberTLFTest {

    public boolean validTlf(String number) {
        try{
            Integer.parseInt(number);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    @Test
    public void testValidUsername() {
        String numeroValido="656656656";
        String numeroInvalido="mal";
        assertTrue(validTlf(numeroValido));
        assertFalse(validTlf(numeroInvalido));
    }
}