/*
 * Copyright 2026 Rajnish Kumar
 * SPDX-License-Identifier: Apache-2.0
 */
package rajnishkmehta.sakshi.camera.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import rajnishkmehta.sakshi.camera.R
import rajnishkmehta.sakshi.camera.BuildConfig
import rajnishkmehta.sakshi.camera.databinding.ActivityAboutBinding
import rajnishkmehta.sakshi.camera.util.EmailHelper

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val cutouts = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            v.setPadding(cutouts.left, 0, cutouts.right, systemBars.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val cutouts = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            v.setPadding(cutouts.left, systemBars.top, cutouts.right, 0)
            insets
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.appVersion.text = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

        setupLink(binding.repoLink, R.string.link_repo)
        setupLink(binding.linkGithub, R.string.link_github)
        setupLink(binding.linkX, R.string.link_x)
        setupLink(binding.linkInstagram, R.string.link_instagram)
        setupLink(binding.linkLinkedin, R.string.link_linkedin)
        setupLink(binding.linkGravatar, R.string.link_gravatar)
        setupLink(binding.linkGitlab, R.string.link_gitlab)
        setupLink(binding.linkReddit, R.string.link_reddit)
        binding.linkEmail.setOnClickListener { EmailHelper.contactDeveloper(this) }
        setupLink(binding.linkKnowMore, R.string.link_know_more)

        setupLink(binding.linkGithubSponsors, R.string.link_github_sponsors)
    }

    private fun setupLink(textView: TextView, urlResId: Int) {
        if (textView.id != R.id.link_know_more && textView.id != R.id.link_email && textView.id != R.id.link_github_sponsors) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
        textView.setOnClickListener {
            val url = getString(urlResId)
            val uri = Uri.parse(url).normalizeScheme()
            val scheme = uri.scheme
            if (scheme != "http" && scheme != "https" && scheme != "mailto") {
                return@setOnClickListener
            }
            try {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "No app available to open this link", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                Toast.makeText(this, "No app available to open this link", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
