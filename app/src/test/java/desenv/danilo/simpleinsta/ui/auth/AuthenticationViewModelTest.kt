package desenv.danilo.simpleinsta.ui.auth

import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationViewModel
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.lang.Exception
import kotlin.test.assertEquals


class AuthenticationViewModelTest: Spek({

    val testScheduler = TestScheduler()

    describe("Start Authentication"){
        val repositoryMock = AuthenticationRepositoryMock()
        val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler, testScheduler)
        val testObserver = TestObserver<String>()
        val urlLogin = "https://api.instagram.com/oauth/authorize/?client_id=724a4916ffab473f995b91826b5c3f53&redirect_uri=https://instagram.com/&response_type=token&display=touch&scope=public_content"
        it("Must hide the button login and send a url to view") {
            authViewModel.loadInstaPage.subscribe(testObserver)
            authViewModel.startAuthenticate()
            testScheduler.triggerActions()
            testObserver.assertValue(urlLogin)
            assertEquals(false, authViewModel.showButtonLogin.get())
        }
    }


    describe("After authentication"){
        val repositoryMock = AuthenticationRepositoryMock()
        val authViewModel = AuthenticationViewModel(repositoryMock, testScheduler, testScheduler)
        it("When has been success Must hide progress and notify view of success") {
            val testObserver = TestObserver<Boolean>()
            authViewModel.authenticatedSucess.subscribe(testObserver)
            authViewModel.registerToken("")
            testScheduler.triggerActions()
            testObserver.assertValue(true)
            assertEquals(false, authViewModel.showProgress.get())
        }

        it("When happen a error, must notify the view hide the progress and show login button") {
            val testObserver = TestObserver<String>()
            repositoryMock.exception = Exception("Error requesting token")
            authViewModel.publishError.subscribe(testObserver)
            authViewModel.registerToken("")
            testScheduler.triggerActions()
            testObserver.assertValue("Error requesting token")
            assertEquals(false, authViewModel.showProgress.get())
            assertEquals(true, authViewModel.showButtonLogin.get())
        }

        it("When happen a error because return false, must notify the view hide the progress and show login button") {
            val testObserver = TestObserver<String>()
            repositoryMock.responseLogout = false
            repositoryMock.exception = null
            authViewModel.publishError.subscribe(testObserver)
            authViewModel.registerToken("")
            testScheduler.triggerActions()
            testObserver.assertValue("Error fetching the token")
            assertEquals(false, authViewModel.showProgress.get())
            assertEquals(true, authViewModel.showButtonLogin.get())
        }
    }
})
