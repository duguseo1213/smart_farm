package com.wcd.farm.presentation.intent

sealed class AppViewIntent {
    object ShowLoginView: AppViewIntent()
    object ShowMainView: AppViewIntent()
}