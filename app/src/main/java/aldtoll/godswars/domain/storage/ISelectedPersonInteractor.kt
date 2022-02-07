package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Person
import io.reactivex.Observable

interface ISelectedPersonInteractor {

    fun update(person: Person?)

    fun get(): Observable<Person?>

    fun value(): Person?
}