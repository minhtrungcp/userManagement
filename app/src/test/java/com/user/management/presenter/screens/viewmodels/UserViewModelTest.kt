package com.user.management.presenter.screens.viewmodels

import com.user.management.domain.repositories.NetworkUserRepository
import com.user.management.domain.use_case.GetUserNetworkUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class UserViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getUserNetworkUseCase: GetUserNetworkUseCase

}