package com.user.management.presenter.screens.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.user.management.data.models.dao.UserModel
import com.user.management.data.models.dao.toUserEntity
import com.user.management.data.models.dto.UserDTO
import com.user.management.data.models.dto.toUserEntity
import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.LocalUserRepository
import com.user.management.domain.repositories.NetworkUserRepository
import com.user.management.domain.use_case.GetUserLocalUseCase
import com.user.management.domain.use_case.GetUserNetworkUseCase
import com.user.management.presenter.common.UIState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {
	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()
	private val dispatcher = StandardTestDispatcher()
	private lateinit var networkRepository: NetworkUserRepository
	private lateinit var localRepository: LocalUserRepository
	private lateinit var savedStateHandle: SavedStateHandle
	private lateinit var getUserLocalUseCase: GetUserLocalUseCase
	private lateinit var getUserNetworkUseCase: GetUserNetworkUseCase
	private lateinit var viewModel: UserViewModel
	private val userDTOList = listOf(
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
			6, "ivey", "https://avatars.githubusercontent.com/u/6?v=4",
			"https://github.com/ivey"
		), UserDTO(
			7, "evanphx", "https://avatars.githubusercontent.com/u/7?v=4",
			"https://github.com/evanphx"
		)
	)
	private val userModelList = listOf(
		UserModel(
			1, "mojombo", "https://avatars.githubusercontent.com/u/1?v=4",
			"https://github.com/mojombo"
		), UserModel(
			2, "defunkt", "https://avatars.githubusercontent.com/u/2?v=4",
			"https://github.com/defunkt"
		), UserModel(
			3, "pjhyett", "https://avatars.githubusercontent.com/u/3?v=4",
			"https://github.com/pjhyett"
		), UserModel(
			4, "wycats", "https://avatars.githubusercontent.com/u/4?v=4",
			"https://github.com/wycats"
		), UserModel(
			5, "ezmobius", "https://avatars.githubusercontent.com/u/5?v=4",
			"https://github.com/ezmobius"
		)
	)
	private val userEntityList = userDTOList.map { it.toUserEntity() }
	private val userEntityListFromLocal = userModelList.map { it.toUserEntity() }
	private val page = "0"

	@Before
	fun setUp() {
		MockKAnnotations.init(this, relaxUnitFun = true)
		Dispatchers.setMain(dispatcher)
		networkRepository = mockkClass(NetworkUserRepository::class)
		localRepository = mockkClass(LocalUserRepository::class)
		savedStateHandle = mockkClass(SavedStateHandle::class)
		getUserLocalUseCase = mockkClass(GetUserLocalUseCase::class)
		getUserNetworkUseCase = mockkClass(GetUserNetworkUseCase::class)
		viewModel = spyk(UserViewModel(getUserNetworkUseCase, getUserLocalUseCase, savedStateHandle), recordPrivateCalls = true)
	}

	@Test
	fun testGetUserList_network_success() {
		dispatcher.run {
			val flowTest = flowOf(userEntityList)
			val successState = UIState.Success(userEntityList)

			assertEquals(true, viewModel.userListState.value is UIState.Loading)

			every { getUserNetworkUseCase.getUserList(userDTOList.size.toString(), page) } returns flowTest

			every { viewModel.fetchUserListNetwork(true) } returns viewModel.updateUserListState(successState)

			assertEquals(userDTOList.size, viewModel.userListState.value.data?.size)
		}
	}

	@Test
	fun testGetUserList_network_error() {
		dispatcher.run {
			val flowTest = flowOf(null)
			val errorMsg = "403 HTTP"
			val errorState = UIState.Error<List<UserEntity>>(errorMsg)

			assertEquals(true, viewModel.userListState.value is UIState.Loading)

			every { getUserNetworkUseCase.getUserList(userDTOList.size.toString(), page) } returns flowTest

			every { viewModel.fetchUserListNetwork(true) } returns viewModel.updateUserListState(errorState)

			assertEquals(errorMsg, viewModel.userListState.value.message)
		}
	}

	@Test
	fun testGetUserData_case_exist_local_data() {
		dispatcher.run {
			val flowTest = flowOf(userModelList)
			val successState = UIState.Success(userEntityListFromLocal)

			assertEquals(true, viewModel.userListState.value is UIState.Loading)

			every { getUserLocalUseCase.getUsers() } returns flowTest

			every { viewModel.getUserListData() } returns viewModel.updateUserListState(successState)

			assertEquals(userModelList.size, viewModel.userListState.value.data?.size)
		}
	}

	@Test
	fun testGetUserData_case_non_exist_local_data() {
		dispatcher.run {
			val flowTest = flowOf(null)
			val successState = UIState.Success<List<UserEntity>>(arrayListOf())

			assertEquals(true, viewModel.userListState.value is UIState.Loading)

			every { getUserLocalUseCase.getUsers() } returns flowTest

			every { viewModel.getUserListData() } returns viewModel.updateUserListState(successState)

			assertEquals(0, viewModel.userListState.value.data?.size)
		}
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
}