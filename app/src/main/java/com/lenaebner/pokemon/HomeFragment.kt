package com.lenaebner.pokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lenaebner.pokemon.databinding.HomeFragmentBinding
import kotlinx.coroutines.*


class HomeFragment : Fragment(R.layout.home_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = HomeFragmentBinding.bind(view)

        lifecycleScope.launch {
            //run asysn code here
        }

        val myScope = CoroutineScope(Dispatchers.Main)

        //started coroutine in new thread and speichert sie in job
        val runner = myScope.launch {

        }

        //stop everything that is running in this scope
        runner.cancel()

        binding.button.setOnClickListener{
            Thread.sleep(200)
        }

        binding.button2.setOnClickListener {
            Thread.sleep(500)
        }

    }
}
