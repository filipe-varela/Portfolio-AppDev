package com.vilp.bin2decapp

import org.junit.Test
import org.junit.Assert.assertEquals

internal class MainActivityTest {

    @Test fun convert_two_from_binary_to_decimal(): Unit = assertEquals(
        "Asserting 10 in binary to 2 in decimal wasn't possible",
        "2",
        convertBin2DecUInt("10")
    )

    @Test fun convert_four_from_binary_to_decimal(): Unit = assertEquals(
        "Asserting 11 in binary to 4 in decimal wasn't possible",
        "4",
        convertBin2DecUInt("100")
    )

    @Test fun convert_ten_from_binary_to_decimal(): Unit = assertEquals(
        "Asserting 1010 in binary to 10 in decimal wasn't possible",
        "10",
        convertBin2DecUInt("1010")
    )

    @Test fun convert_two_hundred_and_forty_seven_from_binary_to_decimal(): Unit = assertEquals(
        "Asserting 11110111 in binary to 247 in decimal wasn't possible",
        "247",
        convertBin2DecUInt("11110111")
    )
}