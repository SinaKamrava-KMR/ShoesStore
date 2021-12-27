package com.mainway.store.feature.product.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_ID
import com.mainway.store.common.NikeActivity
import com.mainway.store.data.Comment
import com.mainway.store.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comments_list_activty.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.ArrayList

class CommentsListActivity : NikeActivity() {

    private val viewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_list_activty)

        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
        viewModel.commentsLivData.observe(this){
            val adapter=CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            commentsRv.adapter=adapter
            adapter.comments = it as ArrayList<Comment>
        }

        commentListToolbar.onBackClickListener= View.OnClickListener {
            finish()
        }

    }
}