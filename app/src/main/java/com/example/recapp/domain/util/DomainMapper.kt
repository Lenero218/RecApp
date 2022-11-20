package com.example.recapp.domain.util

interface DomainMapper<T,DomainModel> {
    fun mapToDomainModel(model: T):DomainModel  //Taking entity as input and outputting as domain model
    fun mapFromDomainModel (domainModel: DomainModel):T  //Taking Domain Model as input and outputting in T
}