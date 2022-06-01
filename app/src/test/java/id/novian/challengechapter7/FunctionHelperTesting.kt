package id.novian.challengechapter7

import id.novian.challengechapter7.helper.toDate
import id.novian.challengechapter7.helper.toDateDetail
import org.junit.Assert.assertEquals
import org.junit.Test

class FunctionHelperTesting {
    @Test
    fun testDate() {
        val valueTest = "1999-11-15"
        assertEquals("Nov 15, 1999", valueTest.toDate())
    }

    @Test
    fun testDateDetail() {
        val valueTest = "1999-11-15"
        assertEquals("11/15/1999", valueTest.toDateDetail())
    }
}