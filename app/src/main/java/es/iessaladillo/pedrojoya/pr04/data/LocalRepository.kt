package es.iessaladillo.pedrojoya.pr04.data

import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr04.data.entity.Task



object LocalRepository: Repository {

    private val taskList:  MutableList<Task> = mutableListOf()

    override fun queryAllTasks(): List<Task> = taskList

    override fun queryCompletedTasks(): List<Task> {
        val taskCompletedList: MutableList<Task> = mutableListOf()

        taskList.forEach {
            if ( it.completed)
                taskCompletedList.add(it)
        }
        return taskCompletedList
    }

    override fun queryPendingTasks(): List<Task> {
        val taskPendingList: MutableList<Task> =  mutableListOf()
        taskList.forEach {
            if (!it.completed)
                taskPendingList.add(it)
        }
        return taskPendingList
    }

    fun createNewIdAllList(): Long{
        var id: Long = 1L
        val idList: MutableList<Long> = mutableListOf()
        if (taskList.isNotEmpty()){
            taskList.forEach {
                idList.add(it.id)
            }
            idList.forEach {
                if (id == it){
                    id++
                }else{
                    return id
                }

            }
        }
        return id

    }

    override fun addTask(concept: String) {
        taskList.add(Task(createNewIdAllList(),concept,false))
    }

    override fun insertTask(task: Task) {
        task.id = createNewIdAllList()
         taskList.add(task)
    }

    override fun deleteTask(taskId: Long) {
       var taskRemove: Task? = null

        taskList.forEach {
            if ( it.id == taskId)
                taskRemove = it
        }

        taskList.remove(taskRemove)
    }

    override fun deleteTasks(taskIdList: List<Long>) {
            taskIdList.forEach{
                    deleteTask(it)
        }
    }

    override fun markTaskAsCompleted(taskId: Long) {
        taskList.forEach {
            if ( it.id == taskId && it.completed)
                it.cambiarEstado()
        }
    }

    override fun markTasksAsCompleted(taskIdList: List<Long>) {
        taskIdList.forEach{
            markTaskAsCompleted(it)
        }    }

    override fun markTaskAsPending(taskId: Long) {
        taskList.forEach {
            if ( it.id == taskId && !it.completed)
                it.cambiarEstado()
        }
    }

    override fun markTasksAsPending(taskIdList: List<Long>) {
        taskIdList.forEach{
            markTaskAsPending(it)
        }
    }

}