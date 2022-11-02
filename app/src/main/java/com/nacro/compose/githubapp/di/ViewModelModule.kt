package com.nacro.compose.githubapp.di

import com.nacro.compose.githubapp.ui.page.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        MainViewModel(get(), get())
    }
}