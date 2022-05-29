package id.novian.challengechapter7.helper

import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class DataStoreManagerTest {
    private lateinit var dataStoreManager: DataStoreManager

    @Before
    fun setUp() {
        dataStoreManager = mockk()
    }

    @Test
    fun getEmail() {
        assertNull(dataStoreManager.getEmail())
    }

    @Test
    fun getStatusLogin() {
        assertNotNull(dataStoreManager.getStatusLogin())
    }
}