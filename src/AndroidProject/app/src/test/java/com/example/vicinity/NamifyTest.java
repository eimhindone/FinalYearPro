package com.example.vicinity;

import org.junit.Test;

import static org.junit.Assert.*;

public class NamifyTest {

    @Test
    public void namify() {
        String address = "Place Name, Street Name, More Address Info, More Text";
        String name;

        Namify namify = new Namify();
        name = namify.namify(address);

        assertEquals("Place Name", name);
    }
}