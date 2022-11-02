package com.nacro.compose.githubapp.di

import com.nacro.compose.githubapp.mapper.DefaultDataMapper
import org.koin.dsl.module

val mapperModule = module {
    single {
        DefaultDataMapper()
    }
}