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

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtSuperTypeList

class ViewModelName(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Maintainability,
        "The name of ViewModels names must end with ViewModel",
        Debt.TWENTY_MINS
    )

    private var className: String? = null

    override fun visitSuperTypeList(list: KtSuperTypeList) {
        var hasViewModelParent = false
        list.entries.forEach { parent ->
            if (parent.text.contains("BaseViewModel")) {
                hasViewModelParent = true
                return@forEach
            }
        }
        className?.let { name ->
            if (hasViewModelParent && !name.replace(".kt", "").endsWith("ViewModel")) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(list),
                        "The name of $className must end with ViewModel"
                    )
                )
            }
        }
        super.visitSuperTypeList(list)
    }

    override fun visitClassOrObject(classOrObject: KtClassOrObject) {
        className = classOrObject.name
        super.visitClassOrObject(classOrObject)
    }
}
