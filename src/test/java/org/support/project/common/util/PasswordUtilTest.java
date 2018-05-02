package org.support.project.common.util;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.util.PasswordUtil;

public class PasswordUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String pass = "password";
        String key = "key";

        String enc = PasswordUtil.encrypt(pass, key);
        System.out.println(enc);

        String dec = PasswordUtil.decrypt(enc, key);
        System.out.println(dec);

        assertEquals(pass, dec);
    }

    @Test
    public void testPasswordHash()
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String password = "testPassword2";
        String salt = "ZllwsbGifUHkh50zaY_Hm4hMVE54RYaoMnzNpZEtPDY=";

        String hash = PasswordUtil.getStretchedPassword(password, salt, 1000);

        System.out.println(hash);
    }

}
