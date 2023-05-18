package com.akmalmf.storyapp

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

/**
 * Created by Akmal Muhamad Firdaus on 10/05/2023 22:18.
 * akmalmf007@gmail.com
 */
inline fun <reified T> mock() = Mockito.mock(T::class.java)
