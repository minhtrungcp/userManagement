package com.user.management.presenter.common

import com.user.management.domain.models.UserEntity

sealed interface UIState {
    data object Loading : UIState

    data class Success(
        val data: List<UserEntity>
    ) : UIState

    data class Error(
        val message: String? = null
    ) : UIState
}