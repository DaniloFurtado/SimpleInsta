package desenv.danilo.simpleinsta.ui.auth

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationRepository
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationViewModel
import desenv.danilo.simpleinsta.ui.SchedulerProviderTest
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.mockito.ArgumentMatchers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals


class AuthenticationViewModelTest: Spek({

    val testScheduler = SchedulerProviderTest()


    describe("Start Authentication"){
        val urlLogin = "https://api.instagram.com/oauth/authorize/?client_id=724a4916ffab473f995b91826b5c3f53&redirect_uri=https://instagram.com/&response_type=token&display=touch&scope=public_content"
        val repositoryMock = mock<AuthenticationRepository>{
            on{extractToken(ArgumentMatchers.anyString())} doReturn Single.just(urlLogin)
        }
        val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler)
        val testObserver = TestObserver<String>()
        it("Must hide the button login and send a url to view") {
            authViewModel.loadInstaPage.subscribe(testObserver)
            authViewModel.startAuthenticate()
            testScheduler.triggerActions()
            testObserver.assertValue(urlLogin)
            assertEquals(false, authViewModel.showButtonLogin.get())
            testObserver.dispose()
        }
    }


    describe("After authentication"){

        it("When has been success Must hide progress and notify view of success") {

            val repositoryMock = mock<AuthenticationRepository>{
                on { registerToken(ArgumentMatchers.anyString()) } doReturn Single.just(true)
                on { extractToken(ArgumentMatchers.anyString()) } doReturn Single.just("")
            }
            val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler)
            val testObserver = TestObserver<Boolean>()
            authViewModel.authenticatedSucess.subscribe(testObserver)
            authViewModel.registerToken(ArgumentMatchers.anyString())
            testScheduler.triggerActions()
            testObserver.assertValue(true)
            assertEquals(false, authViewModel.showProgress.get())
            testObserver.dispose()
        }

        it("When happen a error, must notify the view hide the progress and show login button") {
            val testObserver = TestObserver<String>()
            val repositoryMock = mock<AuthenticationRepository>{
                on { registerToken(ArgumentMatchers.anyString()) } doReturn Single.error(Exception("Error requesting token"))
                on { extractToken(ArgumentMatchers.anyString()) } doReturn Single.just("")
            }

            val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler)

            authViewModel.publishError.subscribe(testObserver)
            authViewModel.registerToken(ArgumentMatchers.anyString())
            testScheduler.triggerActions()
            testObserver.assertValue("Error requesting token")
            assertEquals(false, authViewModel.showProgress.get())
            assertEquals(true, authViewModel.showButtonLogin.get())
            testObserver.dispose()
        }

        it("When happen a error because return false, must notify the view hide the progress and show login button") {
            val testObserver = TestObserver<String>()
            val repositoryMock = mock<AuthenticationRepository>{
                on{registerToken(ArgumentMatchers.anyString())} doReturn Single.just(false)
                on { registerToken("isso vai ") } doReturn  Single.just(false)
                on{extractToken(ArgumentMatchers.anyString())} doReturn Single.just("")
            }
            val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler)
            authViewModel.publishError.subscribe(testObserver)
            authViewModel.registerToken(ArgumentMatchers.anyString())
            testScheduler.triggerActions()
            testObserver.assertValue("Error fetching the token")
            assertEquals(false, authViewModel.showProgress.get())
            assertEquals(true, authViewModel.showButtonLogin.get())
            testObserver.dispose()
        }
    }
})
