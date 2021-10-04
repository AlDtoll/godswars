package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SelectedPersonListInteractor : ISelectedPersonListInteractor {

    private val list = BehaviorSubject.create<MutableList<Person>>()

    override fun update(persons: MutableList<Person>) {
        list.onNext(persons)
    }

    override fun get(): Observable<MutableList<Person>> = list

    override fun value(): MutableList<Person> = list.value ?: emptyList<Person>().toMutableList()
}