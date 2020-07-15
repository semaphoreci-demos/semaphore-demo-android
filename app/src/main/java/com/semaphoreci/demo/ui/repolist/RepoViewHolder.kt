package com.semaphoreci.demo.ui.repolist

import androidx.recyclerview.widget.RecyclerView
import com.semaphoreci.demo.databinding.ViewRepoBinding
import com.semaphoreci.demo.network.models.Repo

class RepoViewHolder(
    private val viewRepoBinding: ViewRepoBinding
) : RecyclerView.ViewHolder(viewRepoBinding.root) {

    fun bind(repo: Repo) = viewRepoBinding.apply {
        repoName.text = repo.name
        repoLanguage.text = repo.language
        repoForkCount.text = repo.forksCount.toString()
        repoStarsCount.text = repo.stargazersCount.toString()
        repoWatcherCount.text = repo.watchersCount.toString()
        repoIssueCount.text = repo.openIssuesCount.toString()

        repoCard.setOnClickListener { }
    }
}
