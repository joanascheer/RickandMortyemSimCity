package br.com.zup.rickandmortyemsimcity.ui.login.view

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
import br.com.zup.rickandmortyemsimcity.databinding.FragmentLoginBinding
import br.com.zup.rickandmortyemsimcity.domain.model.User
import br.com.zup.rickandmortyemsimcity.ui.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        val actualUser = viewModel.getUser()
        actualUser?.reload()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initObservers()

        binding.tvRegisterUser.setOnClickListener {
            navigateRegisterUser()
        }

        binding.btnEnter.setOnClickListener {
            val user = getData()
            viewModel.validateData(user)
        }

        return binding.root
    }

    private fun getData(): User {
        return User(
            email = binding.etEmailLogin.text.toString(),
            password = binding.etPassLogin.text.toString()
        )
    }

    private fun initObservers() {
        viewModel.loginState.observe(this.viewLifecycleOwner) {
            navigateHome(it)
        }

        viewModel.errorState.observe(this.viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun navigateHome(user: User) {
        val bundle = bundleOf(USER_KEY to user)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_loginFragment_to_characterListFragment, bundle)
    }

    private fun navigateRegisterUser() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }


}