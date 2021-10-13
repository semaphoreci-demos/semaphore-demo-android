package com.semaphoreci.demo.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.semaphoreci.demo.databinding.ViewRepoBinding
import com.semaphoreci.demo.network.models.Repo

class RepoListAdapter(
    private val repos: List<Repo>
) : RecyclerView.Adapter<RepoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewRepoBinding = ViewRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(viewRepoBinding)
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.bind(repo)
    }
}
