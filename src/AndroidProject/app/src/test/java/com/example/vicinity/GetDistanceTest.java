package com.example.vicinity;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetDistanceTest {

    @Test
    public void getDistance() {
        double lat1 = 53.3466;
        double lat2 = 53.3650;
        double lon1 = -6.2682;
        double lon2 = -6.3037;
        double delta = 0.1;
        double expected = 3.12;
        double output;

        GetDistance getDistance = new GetDistance();
        output = getDistance.getDistance(lat1, lon1, lat2, lon2);

        assertEquals(expected, output, delta);

    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}