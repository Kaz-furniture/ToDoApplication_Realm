package kaz_furniture.todoapplication.editInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kaz_furniture.todoapplication.ListObject
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.addInfo.AddViewModel
import timber.log.Timber

class EditActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "key"
        fun newIntent(context: Context, listObject: ListObject): Intent {
            return Intent(context,EditActivity::class.java).apply {
                putExtra(KEY, listObject)
            }
        }
    }

    private val viewModel: EditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val list = intent?.extras?.getParcelable<ListObject>(KEY) ?:return
        Timber.d("listObjectActivity:${list}")
        Timber.d("listObject.titleActivity:${list.title}")

        if (savedInstanceState == null) {
            val fragment = EditFragment.newInstance(list)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, EditFragment.TAG)
                .commit()
        }
    }

}