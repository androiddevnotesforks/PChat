/*
 * Copyright 2023 PChat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.pchat.domain.repository

import com.peterchege.pchat.core.api.requests.AddUser
import com.peterchege.pchat.core.api.responses.AddUserResponse
import com.peterchege.pchat.core.api.responses.GetUserByIdResponse
import com.peterchege.pchat.core.api.responses.SearchUserResponse
import com.peterchege.pchat.domain.models.NetworkUser
import com.peterchege.pchat.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(addUser: AddUser): AddUserResponse

    suspend fun searchUser(query:String): SearchUserResponse

    suspend fun getUserById(id:String): GetUserByIdResponse

    fun getAuthUser(): Flow<NetworkUser?>

    suspend fun setAuthUser(user:NetworkUser)

    suspend fun signOutUser()

    suspend fun getChatUserById(id:String):NetworkUser?



}