package com.example.retrofitsamplewithcleanarchitecture.data.local.model

import com.example.retrofitsamplewithcleanarchitecture.domain.models.AuthToken
import com.example.retrofitsamplewithcleanarchitecture.domain.util.DomainMapper

class AuthTokenEntityMapper : DomainMapper<AuthTokenEntity, AuthToken> {

    override fun mapToDomainModel(model: AuthTokenEntity): AuthToken {
        return AuthToken(
            pk = model.account_pk,
            token = model.token
        )
    }

    override fun mapFromDomainModel(domainModel: AuthToken): AuthTokenEntity {
        return AuthTokenEntity(
            account_pk = domainModel.pk,
            token = domainModel.token
        )
    }
}