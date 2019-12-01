package es.iessaladillo.pedrojoya.pr04.data

import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr04.data.entity.Task

// TODO: Crea una clase llamada LocalRepository que implemente la interfaz Repository
//  usando una lista mutable para almacenar las tareas.
//  Los id de las tareas se irán generando secuencialmente a partir del valor 1 conforme
//  se van agregando tareas (add).

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

    fun createNewIdAllList(){
        var count = 1L
        taskList.forEach {
            it.id = count
            count++
        }
    }

    override fun addTask(concept: String) {
        taskList.add(Task(0,concept,false))
        createNewIdAllList()
    }

    override fun insertTask(task: Task) {
         taskList.add(task)
        createNewIdAllList()
    }

    override fun deleteTask(taskId: Long) {
       var taskRemove: Task? = null

        taskList.forEach {
            if ( it.id == taskId)
                taskRemove = it
        }

        taskList.remove(taskRemove)
        createNewIdAllList()
    }

    override fun deleteTasks(taskIdList: List<Long>) {
            taskIdList.forEach{
                    deleteTask(it)
        }
        createNewIdAllList()
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