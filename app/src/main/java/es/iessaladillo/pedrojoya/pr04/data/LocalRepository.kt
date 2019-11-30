package es.iessaladillo.pedrojoya.pr04.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr04.data.entity.Task

// TODO: Crea una clase llamada LocalRepository que implemente la interfaz Repository
//  usando una lista mutable para almacenar las tareas.
//  Los id de las tareas se irán generando secuencialmente a partir del valor 1 conforme
//  se van agregando tareas (add).

object LocalRepository: Repository {

    private var task:  MutableList<Task> = mutableListOf(
        Task(1, "Prueba", false),
        Task(2,"prueba 2 hermano", false),
        Task(2,"prueba 3 brá", true)
    )


    override fun queryAllTasks(): List<Task> = task

    override fun queryCompletedTasks(): List<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun queryPendingTasks(): List<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTask(concept: String) {
        task.add(Task((task.size+1).toLong(),concept,false))
    }

    override fun insertTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(taskId: Long) {
        task.
    }

    override fun deleteTasks(taskIdList: List<Long>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun markTaskAsCompleted(taskId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun markTasksAsCompleted(taskIdList: List<Long>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun markTaskAsPending(taskId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun markTasksAsPending(taskIdList: List<Long>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}