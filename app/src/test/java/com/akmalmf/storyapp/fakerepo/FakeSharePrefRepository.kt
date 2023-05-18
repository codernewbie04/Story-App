package com.akmalmf.storyapp.fakerepo

import com.akmalmf.storyapp.domain.repository.SharePrefRepository

/**
 * Created by Akmal Muhamad Firdaus on 10/05/2023 18:54.
 * akmalmf007@gmail.com
 */
class FakeSharePrefRepository: SharePrefRepository {
    override fun getAccessToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXNzcnp0ek9BeWphVGZWU3oiLCJpYXQiOjE2ODM1NTg3NzV9.KTQCWx6a33dSvywa8L5ABItVqZV6FtweCiLrBXRv-uA"
    }

    override fun setAccessToken(value: String) {}
}