/*
 * Copyright (C) 2019 The Android Open Source Project
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
package com.mx.terryrockstar.rebalancedportfolioapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mx.terryrockstar.rebalancedportfolioapp.add.AddEditViewModel
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupsUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.SaveAssetUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.SaveGroupUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.groups.GroupsViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(AddEditViewModel::class.java) ->
                        AddEditViewModel(GetGroupUseCase(repository),
                            GetGroupsUseCase(repository),
                            SaveGroupUseCase(repository),
                            SaveAssetUseCase(repository)
                        )
                    isAssignableFrom(GroupsViewModel::class.java) ->
                        GroupsViewModel(GetGroupsUseCase(repository))
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}
