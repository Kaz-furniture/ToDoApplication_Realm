package kaz_furniture.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renderViewPager()
        renderTabLayer()
//        if (savedInstanceState == null) {
//            val fragment = ListFragment()
//            supportFragmentManager.beginTransaction()
//                .add(
//                    R.id.fragment_container,
//                    fragment,
//                    ListFragment::class.java.simpleName
//                )
//                .commit()
//        }

    }

    private fun renderViewPager() {
        viewpager.adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return ResourceStore.pagerFragments[position]
            }

            override fun getItemCount(): Int {
                return ResourceStore.tabList.size
            }
        }
    }

    private fun renderTabLayer() {
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = ResourceStore.tabList[position]
        }.attach()
    }



}

//<com.google.android.material.floatingactionbutton.FloatingActionButton
//android:id="@+id/fab"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginEnd="16dp"
//android:layout_marginRight="16dp"
//android:layout_marginBottom="16dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:backgroundTint="#e9967a"
//android:src="@drawable/ic_baseline_post_add_24" />