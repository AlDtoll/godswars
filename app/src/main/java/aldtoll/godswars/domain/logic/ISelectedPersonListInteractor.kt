package aldtoll.godswars.domain.logic

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable

interface ISelectedPersonListInteractor {

    fun data(): Observable<List<Person>>
}