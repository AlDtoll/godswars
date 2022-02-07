package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SelectedPersonInteractor : ISelectedPersonInteractor {

    private val item = BehaviorSubject.create<Person?>()

    override fun update(person: Person?) {
        if (person != null) {
            item.onNext(person)
        } else {
            item.onNext(Person.nobody())
        }
    }

    override fun get(): Observable<Person?> = item

    override fun value(): Person? = item.value
}