package com.user.management.presenter.screens.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.management.data.models.dao.toUserEntity
import com.user.management.domain.models.Result
import com.user.management.domain.models.UserDetailEntity
import com.user.management.domain.models.UserEntity
import com.user.management.domain.models.asResult
import com.user.management.domain.use_case.GetUserLocalUseCase
import com.user.management.domain.use_case.GetUserNetworkUseCase
import com.user.management.presenter.common.Constant.PARAM_USER_LOGIN_NAME
import com.user.management.presenter.common.Constant.PER_PAGE
import com.user.management.presenter.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserNetworkUseCase: GetUserNetworkUseCase,
    private val getUserLocalUseCase: GetUserLocalUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _userListState = MutableStateFlow<UIState<List<UserEntity>>>(UIState.Loading())
    val userListState: StateFlow<UIState<List<UserEntity>>> = _userListState.asStateFlow()
    var userDetailState: SharedFlow<UIState<UserDetailEntity?>> = MutableSharedFlow()
    private var userListTotal: MutableList<UserEntity> = arrayListOf()
    private var page: Int = 0
    private var isLoading = mutableStateOf(false)
    var toastMessage = mutableStateOf("")

    /**
     * update state user list
     */
    fun updateUserListState(state: UIState<List<UserEntity>>) {
        _userListState.update { state }
    }

    /**
     * refresh user list
     * clear user list current
     * then recall [getUserListData]
     */
    fun onRefreshUserList() {
        userListTotal.clear()
        updateUserListState(UIState.Loading())
        getUserListData()
    }

    /**
     * Get all the users
     * By default check database data, if exist display db data
     * If not, call [fetchUserListNetwork] to fetch data from network
     * return list [UserEntity] for success or message error
     */
    fun getUserListData() {
        page = 0
        viewModelScope.launch {
            getUserLocalUseCase.getUsers().flowOn(Dispatchers.IO).collect { userList ->
                if (userList.isNullOrEmpty()) {
                    fetchUserListNetwork(true)
                } else {
                    val users = userList.map { it.toUserEntity() }
                    userListTotal.addAll(users)
                    updateUserListState(UIState.Success(userListTotal))
                    page = userListTotal.size / PER_PAGE.toInt()
                    isLoading.value = false
                }
            }
        }
    }

    /**
     * Get all the users from the network
     * param [showLoading] to handle display loading UI or not
     * Load success display list [UserEntity] to UI collectAsStateWithLifecycle
     * If load error display error message
     */
    fun fetchUserListNetwork(showLoading: Boolean) {
        viewModelScope.launch {
            getUserNetworkUseCase.getUserList(PER_PAGE, page.toString())
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            if (showLoading) updateUserListState(UIState.Loading())
                        }

                        is Result.Success -> {
                            result.data?.toMutableList()?.let {
                                saveUserListToDB(it)
                            }
                        }

                        is Result.Error -> {
                            if (userListTotal.size == 0) {
                                updateUserListState(
                                    UIState.Error(
                                        message = result.exception?.message.orEmpty()
                                    )
                                )
                            } else {
                                toastMessage.value = result.exception?.message.orEmpty()
                                updateUserListState(UIState.Success(userListTotal))
                                isLoading.value = false
                            }
                        }
                    }
                }
        }
    }

    /**
     * Load more user data when scroll down to see more
     * [isLoading] to prevent call multi time
     * [page] will be increase
     */
    fun loadMoreUserList() {
        if (!isLoading.value) {
            isLoading.value = true
            page += 1
            fetchUserListNetwork(false)
        }
    }

    /**
     * Insert a list [UserEntity] data to database
     * validate data, if not null save to db
     */
    private fun saveUserListToDB(users: List<UserEntity>?) {
        users?.let {
            viewModelScope.launch {
                getUserLocalUseCase.insertUsers(users)
            }
        }
    }

    /**
     * Get user detail info data from api
     * with login name [PARAM_USER_LOGIN_NAME] param get from path navigation user list screen
     * return [UserDetailEntity] object or message error
     */
    fun fetchUserDetail() {
        savedStateHandle.get<String>(PARAM_USER_LOGIN_NAME)?.let { loginName ->
            userDetailState = getUserNetworkUseCase.getUserDetailInfo(loginName)
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Loading -> UIState.Loading()
                        is Result.Success -> UIState.Success(result.data)
                        is Result.Error -> UIState.Error(
                            message = result.exception?.message.orEmpty()
                        )
                    }
                }.shareIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L)
                )
        }
    }
}
