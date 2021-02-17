/*
 * Copyright 2020 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gorkem.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.ConfigValidator
import io.gitlab.arturbosch.detekt.api.Notification

class CustomRulesConfigValidator : ConfigValidator {

  override fun validate(config: Config): Collection<Notification> {
    val result = mutableListOf<Notification>()
    runCatching {
      config.subConfig("detekt-custom-rules")
        .subConfig("RepositoryPackage")
        .valueOrNull<Boolean>("active")

      config.subConfig("detekt-custom-rules")
        .subConfig("ViewModelName")
        .valueOrNull<Boolean>("active")
    }.onFailure {
      result.add(SampleMessage("'active' property must be of type boolean."))
    }
    return result
  }
}

class SampleMessage(
  override val message: String,
  override val level: Notification.Level = Notification.Level.Error
) : Notification
