package desenv.danilo.simpleinsta

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import desenv.danilo.simpleinsta.ui.auth.AuthenticationRepository

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.mockito.Mock

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Mock
    lateinit var authRepository: AuthenticationRepository

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("desenv.danilo.simpleinsta", appContext.packageName)
    }
}
