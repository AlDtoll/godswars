package aldtoll.godswars.domain.logic

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable

interface ISelectedPersonInteractor {

    fun get(): Observable<Person>
}