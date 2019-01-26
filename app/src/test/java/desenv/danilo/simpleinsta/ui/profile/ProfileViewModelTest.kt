package desenv.danilo.simpleinsta.ui.profile

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import desenv.danilo.simpleinsta.data.data.apiclient.ApiResponse
import desenv.danilo.simpleinsta.data.data.models.DataMedias
import desenv.danilo.simpleinsta.data.data.models.Location
import desenv.danilo.simpleinsta.data.data.models.Medias
import desenv.danilo.simpleinsta.data.data.models.User
import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepository
import desenv.danilo.simpleinsta.data.ui.profile.ProfileViewModel
import desenv.danilo.simpleinsta.data.ui.profile.TipoList
import desenv.danilo.simpleinsta.ui.SchedulerProviderTest
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.mockito.ArgumentMatchers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class ProfileViewModelTest: Spek({

    val testScheduler = SchedulerProviderTest()


    describe("The request User profile data"){
        val errorType = "Error"
        val errorMessage = "Error Message"
        val code = 200
        val userName = "Danilo is Testing"
        val fullName = "Danilo Little Test"
        val profilePicture = "htttp://themostbeautifulphoto.com"
        it("must return the identify the error and notify view"){
            val repository = mock<ProfileRepository>{
                on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.just(
                    ApiResponse(
                        ApiResponse.Meta(errorType = errorType, code = 404, errorMessage = errorMessage),
                        User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                        ApiResponse.Pagination()))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            profileViewModel.retrieveDataProfileUser(ArgumentMatchers.anyString())
            testScheduler.triggerActions()
            testObserver.assertValue(errorMessage)

        }

        it("must return the error and notify the view it"){
            val repository = mock<ProfileRepository>{
                on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.error(Exception("little erroe"))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            profileViewModel.retrieveDataProfileUser(ArgumentMatchers.anyString())
            testScheduler.triggerActions()
            testObserver.assertValue("Error retriveing User data")

        }

        it("must return the user name right"){
            val repository = mock<ProfileRepository>{
                on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.just(
                    ApiResponse(
                        ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                        User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                        ApiResponse.Pagination()))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("Danilo Little Test", profileViewModel.fullName.get())
        }

        it("must return the full name right"){

            val repository = mock<ProfileRepository>{
                on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.just(
                    ApiResponse(
                        ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                        User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                        ApiResponse.Pagination()))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("Danilo Little Test", profileViewModel.fullName.get())
        }

        it("must return the url profile right"){
            val repository = mock<ProfileRepository>{
                on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.just(
                    ApiResponse(
                        ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                        User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                        ApiResponse.Pagination()))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.retrieveDataProfileUser("123")
            testScheduler.triggerActions()
            assertEquals("htttp://themostbeautifulphoto.com", profileViewModel.profilePicture.get())
        }
    }


    describe("The request User profile data"){

        val errorType = "Error"
        val errorMessage = "Page not found"
        val code = 404
        val userName = "Danilo is Testing"
        val fullName = "Danilo Little Test"
        val profilePicture = "htttp://themostbeautifulphoto.com"

        val repository = mock<ProfileRepository>{
            on { getUserData(ArgumentMatchers.anyString()) } doReturn Single.just(
                ApiResponse(
                    ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                    User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                    ApiResponse.Pagination()))
        }

        val profileViewModel = ProfileViewModel(repository, testScheduler)

        it("must return error and message error"){
            profileViewModel.retrieveDataProfileUser("123")
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Page not found")
        }
    }


    describe("The request User posts"){

        val fullName = "Danilo good tester"
        val userName = "Danilo Test"
        val errorType = "Error"
        val errorMessage = ""
        val code = 200
        val media1 = DataMedias(
            Medias(Medias.StantardResolution("https:xxxxxteste.com", 120, 120)),
            User(userName = userName, fullName = fullName, profilePicture = "https:hhhh.com"),
            Location("Rio De Janeiro")
        )

        val media2 = DataMedias(
            Medias(Medias.StantardResolution("https:xxxxxteste.com", 120, 120)),
            User(userName = userName, fullName = fullName, profilePicture = "https:hhhh.com"),
            Location("Rio De Janeiro")
        )

        val arrayDataMedias = arrayOf(media1, media2)

        val repository = mock<ProfileRepository>{
            on { getUserPosts(ArgumentMatchers.anyString()) } doReturn Single.just(ApiResponse(
                ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                arrayDataMedias,
                ApiResponse.Pagination()))
        }

        val profileViewModel = ProfileViewModel(repository, testScheduler)

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
            assertEquals("https:hhhh.com", profileViewModel.publicationsAdapter.get()?.posts!![0].user.profilePicture)
        }
    }


    describe("The request User posts"){

        it("must catch error returned from servic and show message"){
            val repository = mock<ProfileRepository>{
                on{getUserPosts(ArgumentMatchers.anyString())} doReturn
                        Single.just(ApiResponse(
                            ApiResponse.Meta(errorType = "", code = 500, errorMessage = "Internal Server Error"),
                            emptyArray(),
                            ApiResponse.Pagination()))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.retrieveDataPotsUser()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Internal Server Error")
        }


        it("must catch error catched in app and show message"){
            val repository = mock<ProfileRepository>{
                on{getUserPosts(ArgumentMatchers.anyString())} doReturn Single.error(Exception("Error parse Json"))
            }

            val profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.retrieveDataPotsUser()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()

            testObserver.assertValue("Error parse Json")
        }

    }


    describe("Logout the app"){

        lateinit var profileViewModel: ProfileViewModel

        it("must return logout sucess, and call the metod for action the view"){
            val repository = mock<ProfileRepository>{
                on{logout()} doReturn Single.just(true)
            }
            profileViewModel = ProfileViewModel(repository, testScheduler)
            profileViewModel.logout()
            val testObserver = TestObserver<Boolean>()
            profileViewModel.logoutSucess.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue(true)
        }

        it("must return error logout, and call the metod for action the view"){
            val repository = mock<ProfileRepository>{
                on{logout()} doReturn Single.just(false)
            }
            profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.logout()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Error logout")
        }


        it("must return error logout, and call the metod for action the view"){
            val repository = mock<ProfileRepository>{
                on{logout()} doReturn Single.error(Exception("Error in logout"))
            }
            profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.logout()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Error in logout")
        }

        it("must return error logout, and call the metod for action the view, but wiht message default"){
            val repository = mock<ProfileRepository>{
                on{logout()} doReturn Single.error(Exception(""))
            }
            profileViewModel = ProfileViewModel(repository, testScheduler)

            profileViewModel.logout()
            val testObserver = TestObserver<String>()
            profileViewModel.actionError.subscribe(testObserver)
            testScheduler.triggerActions()
            testObserver.assertValue("Error logout")
        }
    }


    describe("User change type list of posts"){
        val repository = mock<ProfileRepository>()
        val profileViewModel = ProfileViewModel(repository, testScheduler)

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




