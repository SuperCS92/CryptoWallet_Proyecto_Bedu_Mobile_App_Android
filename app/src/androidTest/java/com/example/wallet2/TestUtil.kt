package com.example.wallet2
import com.example.wallet2.data.models.ReceivedTran

object TestUtil {

    fun createReceivedTran(id: Int) = ReceivedTran(
        id=id,
        100f,
        3,
        "ETH",
        "21/12/2020",
        "qr.jpg",
        "success"
    )

//    fun createRepos(count: Int, owner: String, name: String, description: String): List<Repo> {
//        return (0 until count).map {
//            createRepo(
//                owner = owner + it,
//                name = name + it,
//                description = description + it
//            )
//        }
//    }
//
//    fun createRepo(owner: String, name: String, description: String) = createRepo(
//        id = Repo.UNKNOWN_ID,
//        owner = owner,
//        name = name,
//        description = description
//    )
//
//    fun createRepo(id: Int, owner: String, name: String, description: String) = Repo(
//        id = id,
//        name = name,
//        fullName = "$owner/$name",
//        description = description,
//        owner = Repo.Owner(owner, null),
//        stars = 3
//    )
//
//    fun createContributor(repo: Repo, login: String, contributions: Int) = Contributor(
//        login = login,
//        contributions = contributions,
//        avatarUrl = null
//    ).apply {
//        repoName = repo.name
//        repoOwner = repo.owner.login
//    }
}