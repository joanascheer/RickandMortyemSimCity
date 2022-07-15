package br.com.zup.rickandmortyemsimcity.ui.home.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.rickandmortyemsimcity.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customAppBar()
        //clickFloatActionBtn()

//        binding.floatingActionButton.setOnClickListener {
//            startActivity(Intent(this, CharacterFavoriteListFragment::class.java))
//
//        }

        supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
    }
//    private fun clickFloatActionBtn() {
//        binding.floatingActionButton.setOnClickListener {
//            goToCharacterFavoriteList()
//        }
//    }
//
//    private fun goToCharacterFavoriteList() {
//
//        NavHostFragment.findNavController(this)
//            .navigate(R.id.action_characterListFragment_to_characterFavoriteListFragment)
//    }

    private fun customAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}