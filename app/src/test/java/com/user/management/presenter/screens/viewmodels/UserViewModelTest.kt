package com.user.management.presenter.screens.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.user.management.data.models.dto.UserDTO
import com.user.management.data.models.dto.toUserEntity
import com.user.management.domain.repositories.NetworkUserRepository
import com.user.management.domain.use_case.GetUserLocalUseCase
import com.user.management.domain.use_case.GetUserNetworkUseCase
import com.user.management.presenter.common.UIState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {
	@ExperimentalCoroutinesApi
	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Mock
	private lateinit var networkRepository: NetworkUserRepository

	@Mock
	private lateinit var getUserNetworkUseCase: GetUserNetworkUseCase

	@Mock
	private lateinit var getUserLocalUseCase: GetUserLocalUseCase

	@Mock
	private lateinit var savedStateHandle: SavedStateHandle
	lateinit var viewModel: UserViewModel
	val users = listOf(
		UserDTO(
			1, "mojombo", "https://avatars.githubusercontent.com/u/1?v=4",
			"https://github.com/mojombo"
		), UserDTO(
			2, "defunkt", "https://avatars.githubusercontent.com/u/2?v=4",
			"https://github.com/defunkt"
		), UserDTO(
			3, "pjhyett", "https://avatars.githubusercontent.com/u/3?v=4",
			"https://github.com/pjhyett"
		), UserDTO(
			4, "wycats", "https://avatars.githubusercontent.com/u/4?v=4",
			"https://github.com/wycats"
		), UserDTO(
			5, "ezmobius", "https://avatars.githubusercontent.com/u/5?v=4",
			"https://github.com/ezmobius"
		), UserDTO(
			6, "ivey", "https://avatars.githubusercontent.com/u/6?v=4", "https://github.com/ivey"
		), UserDTO(
			7, "evanphx", "https://avatars.githubusercontent.com/u/7?v=4",
			"https://github.com/evanphx"
		)
	)

	@Before
	fun setUp() {
		viewModel = UserViewModel(getUserNetworkUseCase, getUserLocalUseCase, savedStateHandle)
	}

	@Test
	fun fetchUserListNetwork_success() = runTest {
		assertEquals(true, viewModel.userListState.value is UIState.Loading)

		val flow = flow {
			emit(users.map { it.toUserEntity() })
		}
//		`when`(viewModel.fetchUserListNetwork(true)).then {
//			networkRepository
//				.getUserList(users.size.toString(), "0")
//		}
		doReturn(flow)
			.`when`(getUserNetworkUseCase)
			.getUserList(users.size.toString(), "0")

		viewModel.fetchUserListNetwork(true)

		assertEquals(UIState.Success(null), viewModel.userListState.value)
	}

	@After
	fun tearDown() {
	}
}