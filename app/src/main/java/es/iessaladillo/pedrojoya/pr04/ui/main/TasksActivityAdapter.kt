package es.iessaladillo.pedrojoya.pr04.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr04.R
import es.iessaladillo.pedrojoya.pr04.data.entity.Task
import es.iessaladillo.pedrojoya.pr04.utils.invisibleUnless
import es.iessaladillo.pedrojoya.pr04.utils.strikeThrough
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tasks_activity_item.*


class TasksActivityAdapter: RecyclerView.Adapter<TasksActivityAdapter.ViewHolder>() {

    private var data: List<Task> = emptyList()

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.tasks_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemId(position: Int): Long = data[position].id


    fun getItem(position: Int): Task = data[position]

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(newData: List<Task>){
        data = newData
        notifyDataSetChanged()
    }


    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {


        init {
            containerView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(position)
                }
            }
            chkCompleted.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(position)
                }
            }
        }

        fun bind(item: Task){
            lblConcept.text = item.concep
            if (item.completed){
                lblConcept.strikeThrough(true)
                viewBar.setBackgroundColor( Color.parseColor("#00574B"))
                lblCompleted.text = item.completedAt
                chkCompleted.isChecked = true
            }else{
                viewBar.setBackgroundColor( Color.parseColor("#008577"))
                lblConcept.strikeThrough(false)
                lblCompleted.text = item.createdAt
                chkCompleted.isChecked = false
            }
        }

    }
}