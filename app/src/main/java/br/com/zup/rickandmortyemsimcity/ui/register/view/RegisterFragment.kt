package br.com.zup.rickandmortyemsimcity.ui.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.USER_KEY
import br.com.zup.rickandmortyemsimcity.databinding.FragmentRegisterBinding
import br.com.zup.rickandmortyemsimcity.domain.model.User
import br.com.zup.rickandmortyemsimcity.ui.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnRegister?.setOnClickListener {
            val user = getData()
            viewModel.validateData(user)
        }
        initObservers()
        return binding.root
    }


    private fun getData(): User {
        return User(
            name = binding.etRegisterName.text.toString(),
            email = binding.etRegisterEmail.text.toString(),
            password = binding.etRegisterPassword.text.toString()
        )
    }

    private fun initObservers() {
        viewModel.registerState.observe(this.viewLifecycleOwner) {
            goToLogin(it)
        }

        viewModel.errorState.observe(this.viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun goToLogin(user: User) {
        val bundle = bundleOf(USER_KEY to user)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_registerFragment_to_loginFragment, bundle)
    }

}