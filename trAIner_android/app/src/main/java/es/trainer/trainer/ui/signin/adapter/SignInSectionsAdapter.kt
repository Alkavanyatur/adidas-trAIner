package es.trainer.trainer.ui.signin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import es.trainer.trainer.ui.signin.view.LoginFragment
import es.trainer.trainer.ui.signin.view.RegisterFragment

class SignInSectionsAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> null
        }
    }


    override fun getCount(): Int {
     return 2
    }
}