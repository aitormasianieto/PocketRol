package org.ieselcaminas.aitor.pocketrol.lobby


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import org.ieselcaminas.aitor.pocketrol.LoginActivity
import org.ieselcaminas.aitor.pocketrol.MainActivity

import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentLobbyBinding

/**
 * A simple [Fragment] subclass.
 */
class LobbyFragment : Fragment() {

    private lateinit var binding: FragmentLobbyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lobby, container, false)

        //Options Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lobby_settings_menu, menu)
        /**ADoNou*/
        //super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut_menuItem -> {
                AuthUI.getInstance().signOut(context!!)
                startActivity(Intent(context, LoginActivity::class.java))
                true
            }
            else -> {
               super.onOptionsItemSelected(item)
            }
        }
    }
}
