package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.repo.common.IRepo
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool

//TODO change to abstract NONE not school
data class ContextConfig(
    val repoTest: IRepoSchool = IRepoSchool.NONE,
    val repoProd: IRepoSchool = IRepoSchool.NONE
)
