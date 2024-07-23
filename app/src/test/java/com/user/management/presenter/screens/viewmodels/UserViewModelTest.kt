package com.user.management.presenter.screens.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.user.management.data.models.dao.UserModel
import com.user.management.data.models.dto.UserDTO
import com.user.management.data.models.dto.toUserEntity
import com.user.management.domain.repositories.LocalUserRepository
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val networkRepository = NetworkUserRepositoryTest()
    private lateinit var getUserNetworkUseCase: GetUserNetworkUseCase

    @Mock
    private lateinit var localRepository: LocalUserRepository

    private lateinit var getUserLocalUseCase: GetUserLocalUseCase

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: UserViewModel

    private val users = listOf(
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

    @Before
    fun setUp() {
        getUserNetworkUseCase = GetUserNetworkUseCase(networkRepository)
        getUserLocalUseCase = GetUserLocalUseCase(localRepository)
        viewModel = UserViewModel(getUserNetworkUseCase, getUserLocalUseCase, savedStateHandle)
    }


    @Test
    fun fetchUserListNetwork_success() = runTest {
        val userEntityList = users.map { it.toUserEntity() }

        assertEquals(true, viewModel.userListState.value is UIState.Loading)

        networkRepository.sendUsers(userEntityList)

        viewModel.fetchUserListNetwork(true)

        viewModel.updateUserListState(UIState.Success(userEntityList))

        assertEquals(UIState.Success(userEntityList.toMutableList()).data?.size, viewModel.userListState.value.data?.size)
    }

    @After
    fun tearDown() {
    }
}