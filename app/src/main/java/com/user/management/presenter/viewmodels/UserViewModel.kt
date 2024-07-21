package com.user.management.presenter.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.management.domain.models.Result
import com.user.management.domain.models.UserEntity
import com.user.management.domain.models.asResult
import com.user.management.domain.use_case.GetUserLocalUseCase
import com.user.management.domain.use_case.GetUserNetworkUseCase
import com.user.management.presenter.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserNetworkUseCase: GetUserNetworkUseCase,
    private val getUserLocalUseCase: GetUserLocalUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userList = MutableStateFlow<UIState>(UIState.Loading)
    val userList: StateFlow<UIState> = _userList.asStateFlow()

//    private val _userDetail = MutableSharedFlow<UIState<UserEntity>>()
//    val userDetail: SharedFlow<UIState<UserEntity>> = _userDetail

    var perPage: String = "20"
    var page: Int = 0

    fun refresh() {

    }

    fun fetchUserList() {
        viewModelScope.launch {
            getUserNetworkUseCase.getUserList(perPage, page.toString())
                .asResult()
                .collect { result ->
                    _userList.update {
                        when (result) {
                            is Result.Loading -> UIState.Loading
                            is Result.Success -> UIState.Success(result.data.orEmpty())
                            is Result.Error -> UIState.Error(message = result.exception?.message)
                        }
                    }
                }
        }
    }
}
