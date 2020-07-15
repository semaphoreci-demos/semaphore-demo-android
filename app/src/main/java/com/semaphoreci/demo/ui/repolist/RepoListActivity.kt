package com.semaphoreci.demo.ui.repolist

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.semaphoreci.demo.R
import com.semaphoreci.demo.databinding.ActivityRepoListBinding
import com.semaphoreci.demo.network.models.Repo
import com.semaphoreci.demo.ui.util.Resource

class RepoListActivity : AppCompatActivity() {
    private val viewModel: RepoListViewModel by viewModels()

    private lateinit var repoListBinding: ActivityRepoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoListBinding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(repoListBinding.root)

        getRepos()
    }

    private fun getRepos() {
        viewModel.repos.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> updateLoadingState(true)
                is Resource.Success -> {
                    updateRepoList(resource.data ?: emptyList())
                }
                is Resource.Error -> showError(resource.message)
            }
        })
    }

    private fun updateLoadingState(isLoading: Boolean) {
        repoListBinding.repoList.visibility = when {
            isLoading -> View.GONE
            else -> View.VISIBLE
        }

        repoListBinding.repoListLoading.visibility = when {
            isLoading -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun updateRepoList(repos: List<Repo>) {
        updateLoadingState(false)

        repoListBinding.repoList.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
            layoutManager = LinearLayoutManager(context)
            adapter = RepoListAdapter(repos)
        }
    }

    private fun showError(message: String?) {
        updateLoadingState(false)

        val errorMessage = message ?: getString(R.string.repo_list_error)
        val errorTextColor = TypedValue().let { value ->
            theme.resolveAttribute(R.attr.colorOnError, value, true)
            return@let value.data
        }
        val errorColor = TypedValue().let { value ->
            theme.resolveAttribute(R.attr.colorError, value, true)
            return@let value.data
        }

        Snackbar.make(repoListBinding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(errorColor)
            .setTextColor(errorTextColor)
            .show()
    }
}
