package com.semaphoreci.demo.ui.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.semaphoreci.demo.databinding.ActivityRepoDetailsBinding
import com.semaphoreci.demo.network.models.Repo
import java.text.SimpleDateFormat
import java.util.*

class RepoDetailsActivity : AppCompatActivity() {
    private lateinit var repoDetailsBinding: ActivityRepoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoDetailsBinding = ActivityRepoDetailsBinding.inflate(layoutInflater)
        setContentView(repoDetailsBinding.root)

        setupViews()
    }

    private fun setupViews() {
        val repo = intent.getSerializableExtra("REPO") as Repo

        repoDetailsBinding.apply {
            repoDetailsToolbar.title = repo.name
            repo.description?.let { repoDescription.text = it }
            repo.language?.let { repoLanguage.text = it }
            repoForkCount.text = repo.forksCount.toString()
            repoStarsCount.text = repo.stargazersCount.toString()
            repoWatcherCount.text = repo.watchersCount.toString()
            repoIssueCount.text = repo.openIssuesCount.toString()

            val dateFormat = SimpleDateFormat(
                "yyyy/MM/dd HH:mm",
                Locale.getDefault()
            )

            repoCreationDate.text = dateFormat.format(repo.createdAt)
            repoUpdateDate.text = dateFormat.format(repo.updatedAt)

            repo.homepage?.let {
                val homePageIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(it)
                )
                buttonHome.setOnClickListener { startActivity(homePageIntent) }
            }


            val gitHubIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(repo.htmlUrl)
            )
            buttonGit.setOnClickListener { startActivity(gitHubIntent) }

            repoDetailsToolbar.setNavigationOnClickListener { finish() }
        }
    }
}
