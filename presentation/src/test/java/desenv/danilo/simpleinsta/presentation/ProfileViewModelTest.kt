package desenv.danilo.simpleinsta.presentation

import com.nhaarman.mockitokotlin2.*
import desenv.danilo.simpleinsta.domain.interactor.user.ListUserPostsUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.LogoutUserUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.ViewUserDetailUseCase
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ProfileViewModelTest : Spek({


    describe("list post") {
        val listUserPostsUseCase = mock<ListUserPostsUseCase>()
        val logoutUserUseCase = mock<LogoutUserUseCase>()
        val viewUserDetailUseCase = mock<ViewUserDetailUseCase>()

        val profileViewModel = ProfileViewModel(listUserPostsUseCase, logoutUserUseCase, viewUserDetailUseCase)

        it("must execute the method") {
            profileViewModel.listUserPost()
            verify(listUserPostsUseCase, times(1))
                .execute(eq(""), any(), any(), eq(null), eq(null))
        }

    }
})