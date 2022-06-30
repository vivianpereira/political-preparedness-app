package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class VoterInfoFragment : Fragment() {

    private val args: VoterInfoFragmentArgs by navArgs()

    private val _viewModel: VoterInfoViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        _viewModel.initialise(args.election, args.followed)

        _viewModel.hideVoterInfo.observe(viewLifecycleOwner, Observer {
            hideVoterInfo(binding)
            Snackbar.make(requireView(), R.string.error_message, Snackbar.LENGTH_LONG).show()
        })

        _viewModel.showProgress.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.mainContentVoterInfo.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.mainContentVoterInfo.visibility = View.VISIBLE
            }
        })

        _viewModel.url.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                startUrl(it)
            }
        })

        return binding.root
    }

    private fun startUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun hideVoterInfo(binding: FragmentVoterInfoBinding) {
        binding.stateHeader.visibility = View.GONE
        binding.votingLocation.visibility = View.GONE
        binding.ballotInformation.visibility = View.GONE
    }
}
