package kaz_furniture.todoapplication

import androidx.fragment.app.Fragment

interface ResourceStore {
    companion object {
        val tabList = listOf(
            "all", "todo", "finished"
        )
        val pagerFragments:List<Fragment> = listOf(
            ListFragment.create(), ListFragmentSecond.create(), ListFragmentThird.create())
    }
}