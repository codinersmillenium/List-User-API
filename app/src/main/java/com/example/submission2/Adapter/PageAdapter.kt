package com.example.submission2.Adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission2.FragmentFollowers
import com.example.submission2.FragmentFollowing
import com.example.submission2.R

class PageAdapter(private val context: Context, fragment: FragmentManager) :
FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    @StringRes
    private val title = intArrayOf(
        R.string.followers,
        R.string.following
    )

    private val page = listOf(
        FragmentFollowers(),
        FragmentFollowing()
    )

    override fun getCount(): Int {
        return page.size
    }

    override fun getItem(position: Int): Fragment {
        return page[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(title[position])
    }

}