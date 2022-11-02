package com.nacro.compose.githubapp.di

import com.nacro.compose.githubapp.repository.GithubUserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        GithubUserRepository(get(), get())
    }
}