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

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class ViewModelNameTest {
  @Test
  fun notValidViewModelName() {
    val findings = ViewModelName().lint("class SubViewModelGK : ViewModel()")

    assertThat(findings).hasSize(1)
    assertThat(findings[0].message)
      .isEqualTo("The name of SubViewModelGK must end with ViewModel")
  }
}
