package es.iessaladillo.pedrojoya.pr04.ui.main

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr04.R
import es.iessaladillo.pedrojoya.pr04.base.observeEvent
import es.iessaladillo.pedrojoya.pr04.data.LocalRepository
import es.iessaladillo.pedrojoya.pr04.data.entity.Task
import es.iessaladillo.pedrojoya.pr04.utils.hideKeyboard
import es.iessaladillo.pedrojoya.pr04.utils.invisibleUnless
import es.iessaladillo.pedrojoya.pr04.utils.setOnSwipeListener
import kotlinx.android.synthetic.main.tasks_activity.*
import kotlinx.android.synthetic.main.tasks_activity_item.*


class TasksActivity : AppCompatActivity() {
    private val listAdapter: TasksActivityAdapter = TasksActivityAdapter().apply {
        setOnItemListener(object: OnItemClickListener{
            override fun onItemClick(position: Int) {
                viewModel.updateTaskCompletedState(getItem(position),getItem(position).completed)
            }
        })

    }
    private var mnuFilter: MenuItem? = null
    private val viewModel: TasksActivityViewModel by lazy {
        ViewModelProvider(this, TasksActivityViewModelFactory(LocalRepository, application))
            .get(TasksActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_activity)
        setupViews()
        observeTask()
    }

    private fun observeTask() {
        viewModel.tasks.observe(this) { showTasks(it) }
        viewModel.onShowMessage.observeEvent(this) { Snackbar.make(lstTasks, it, Snackbar.LENGTH_LONG).show() }
        viewModel.onStartActivity.observeEvent(this) {startActivity(it)}
        viewModel.currentFilterMenuItemId.observe(this) {
            checkMenuItem(it)
        }
    }


    private fun setupViews() {
        setupRecyclerView()

        imgAddTask.setOnClickListener {
            if (viewModel.isValidConcept(txtConcept.text.toString()))
                viewModel.addTask(txtConcept.text.toString())
            else
                Snackbar.make(
                    lstTasks, getString(R.string.concept_task_empty),
                    Snackbar.LENGTH_SHORT
                ).show()
            it.hideKeyboard()
            txtConcept.setText("")
        }
    }

    private fun setupRecyclerView() {
        lstTasks.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TasksActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(this@TasksActivity, RecyclerView.VERTICAL))
            adapter = listAdapter

            //ListenerItems
            setOnSwipeListener { viewHolder, _ ->
                deleteTaskk(listAdapter.getItem(viewHolder.adapterPosition))
            }
        }
    }

    private fun deleteTaskk(task: Task) {
        viewModel.deleteTask(task)
        Snackbar.make(
            lstTasks, getString(R.string.tasks_task_deleted, task.concep),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.tasks_recreate)) { viewModel.insertTask(task) }
            .show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        mnuFilter = menu.findItem(R.id.mnuFilter)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuShare -> viewModel.shareTasks()
            R.id.mnuDelete -> viewModel.deleteTasks()
            R.id.mnuComplete -> viewModel.markTasksAsCompleted()
            R.id.mnuPending -> viewModel.markTasksAsPending()
            R.id.mnuFilterAll -> viewModel.filterAll()
            R.id.mnuFilterPending -> viewModel.filterPending()
            R.id.mnuFilterCompleted -> viewModel.filterCompleted()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun checkMenuItem(@MenuRes menuItemId: Int) {
        lstTasks.post {
            val item = mnuFilter?.subMenu?.findItem(menuItemId)
            item?.let { menuItem: MenuItem ->
                menuItem.isChecked = true
            }
        }
    }

    private fun showTasks(tasks: List<Task>) {
        lstTasks.post {
            listAdapter.submitList(tasks)
            lblEmptyView.invisibleUnless(tasks.isEmpty())
        }
    }


}

