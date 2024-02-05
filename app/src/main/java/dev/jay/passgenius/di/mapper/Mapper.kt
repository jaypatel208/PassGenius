package dev.jay.passgenius.di.mapper

interface Mapper<F, T> {
    fun mapFrom(from: F): T
}