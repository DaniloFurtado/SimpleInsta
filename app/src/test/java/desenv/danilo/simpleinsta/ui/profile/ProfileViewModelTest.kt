package desenv.danilo.simpleinsta.ui.profile

import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepository
import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepositoryImp
import desenv.danilo.simpleinsta.data.ui.profile.ProfileViewModel
import desenv.danilo.simpleinsta.data.ui.profile.TipoList
import desenv.danilo.simpleinsta.ui.profile.api.ApiProfileTest
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class ProfileViewModelTest: Spek({

    val testScheduler = TestScheduler()


    describe("The request User profile data"){
        val repository: ProfileRepository = ProfileRepositoryImp(ApiProfileTest())
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)

        it("must return the user name right"){
            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("Danilo Little Test", profileViewModel.fullName.get())
        }

        it("must return the url profile right"){
            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("htttp://themostbeautifulphoto.com", profileViewModel.profilePicture.get())
        }
    }


    describe("The request User profile data"){
        val apiProfile = ApiProfileTest()
        apiProfile.code = 404
        apiProfile.errorMessage = "Page not found"
        val repository: ProfileRepository = ProfileRepositoryImp(apiProfile)
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)

        it("must return error and message error"){
            profileViewModel.retrieveDataProfileUser("123")
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Page not found")
        }
    }


    describe("The request User posts"){
        val api = ApiProfileTest()
        api.fullName = "Danilo good tester"
        api.userName = "Danilo Test"
        val repository: ProfileRepository = ProfileRepositoryImp(api)
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)

        it("must create a Adapter"){
            profileViewModel.retrieveDataPotsUser()
            testScheduler.triggerActions()
            assertNotNull(profileViewModel.publicationsAdapter)
        }

        it("must return array with 2 posts"){
            profileViewModel.retrieveDataPotsUser()
            testScheduler.triggerActions()
            assertEquals(2, profileViewModel.publicationsAdapter.get()?.itemCount)
        }

        it("must return the user post right"){
            profileViewModel.retrieveDataPotsUser()
            testScheduler.triggerActions()
            assertEquals("Danilo good tester", profileViewModel.publicationsAdapter.get()?.posts!![0].user.fullName)
            assertEquals("Danilo Test", profileViewModel.publicationsAdapter.get()?.posts!![0].user.userName)
        }
    }


    describe("The request User posts"){
        val api = ApiProfileTest()
        api.code = 500
        api.errorMessage = "Internal Server Error"
        val repository: ProfileRepository = ProfileRepositoryImp(api)
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)

        it("must catch error and show message"){
            profileViewModel.retrieveDataPotsUser()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Internal Server Error")
        }

    }

    describe("User change type list of posts"){
        val repository: ProfileRepository = ProfileRepositoryImp(ApiProfileTest())
        val profileViewModel = ProfileViewModel(repository, testScheduler, testScheduler)

        it("Selected type List"){
            profileViewModel.changeTypeList(TipoList.LIST)
            assertEquals(TipoList.LIST, profileViewModel.publicationsAdapter.get()?.typeList)
        }

        it("Selected type Grid"){
            profileViewModel.changeTypeList(TipoList.GRID)
            assertEquals(TipoList.GRID, profileViewModel.publicationsAdapter.get()?.typeList)
        }

    }
})




