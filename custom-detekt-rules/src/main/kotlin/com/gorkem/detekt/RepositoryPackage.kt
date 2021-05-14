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
import org.jetbrains.kotlin.psi.KtPackageDirective
import java.util.Locale

class RepositoryPackage(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Maintainability,
        "The repository classes must be in the repository package",
        Debt.TWENTY_MINS
    )

    lateinit var packageName: String

    override fun visitPackageDirective(directive: KtPackageDirective) {
        packageName = directive.name
        super.visitPackageDirective(directive)
    }

    override fun visitClassOrObject(classOrObject: KtClassOrObject) {
        classOrObject.name?.let { nameOfClass ->
            if (nameOfClass.lowercase(Locale.ENGLISH).endsWith("repository") && packageName != "repository") {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(classOrObject),
                        "$nameOfClass must be inside of repository package"
                    )
                )
            }
        }
        super.visitClassOrObject(classOrObject)
    }
}
