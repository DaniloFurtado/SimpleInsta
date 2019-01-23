package desenv.danilo.simpleinsta

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import desenv.danilo.simpleinsta.data.data.apiclient.ApiResponse
import desenv.danilo.simpleinsta.data.data.models.User
import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepository
import desenv.danilo.simpleinsta.data.ui.profile.ProfileViewModel
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.mockito.MockitoAnnotations
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

class Teste2: Spek({

    beforeGroup {
        MockitoAnnotations.initMocks(this)
    }

    describe("Have the toke and request the Profile Data"){


        val repository: ProfileRepository = mock<ProfileRepository>{
            on {
                this.getUserData("123456")
            } doReturn Single.just(
                ApiResponse<User>(
                    ApiResponse.Meta(errorType = "", code = 2),
                    User(fullName = "Danilo Teste", profilePicture = "xxxxx"),
                    ApiResponse.Pagination())
            )
        }
        val testScheduler = TestScheduler()
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)


        it("Verify the data has been returned on ViewModel"){
            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("Danilo Teste", profileViewModel.fullName.get())

        }
    }
})