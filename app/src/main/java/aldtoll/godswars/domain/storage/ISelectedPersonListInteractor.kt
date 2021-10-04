package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable

interface ISelectedPersonListInteractor {

    fun update(persons: MutableList<Person>)

    fun get(): Observable<MutableList<Person>>

    fun value(): MutableList<Person>
}