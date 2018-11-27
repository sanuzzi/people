package com.dates

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Validated
@Controller("/people")
class PeopleController {

    // Parte 1, para entender estados y formatos
    @Get("/todoOK")
    fun todoBien(): HttpStatus {
        return HttpStatus.OK
    }

    @Get("/hola")
    fun hola(): String {
        return "Hola!!"
    }

    // Parte 2, para entender respuestas y que la vista automática convierte objeto a JSON
    @Get("/")
    fun index(): List<Person> {
        return peopleService.findAll()
    }

    @Get("/{id}")
    fun index(id: Long): Person {
        return peopleService.get(id)
    }

    @Delete("/{id}")
    fun save(id: Long) {
        peopleService.delete(id)
    }

    // Parte 3, recibiendo bodies (con header)
    @Post("/")
    fun save(@Body @Valid person: Person) {
        peopleService.save(person)
    }

    @Put("/{id}") // Acá nos empezamos a replantear cosas, ya que tiene sentido mandar el ID por un lado y en el cuerpo solo los datos
    fun update(id: Long, @Body @Valid person: Person) {
        peopleService.update(person)
    }
}

object peopleService {
    private val people =  mutableListOf(
            Person(id = 1, dni = 111, name = "Fer"),
            Person(id = 2, dni = 222, name = "Naty"),
            Person(id = 3, dni = 333, name = "Migue")
    )

    fun findAll(): List<Person>{
        return this.people
    }

    fun get(id: Long): Person {
        val possiblePerson = this.people.find { person -> person.id == id }
        return possiblePerson!!
    }

    fun delete(id: Long) {
        this.people.removeAll { person -> person.id == id }
    }

    fun save(person: Person) {
        this.people.add(person)
    }

    fun update(person: Person){
        this.delete(person.id!!)
        this.save(person)
    }
}

// Re feo, no se aprovecha la gracia de Kotlin aún
class Person{
    @NotNull @Positive var id: Long? = null
    @NotNull @Positive var dni: Long? = null
    @NotBlank var name: String? = null

    constructor() {} // Solo lo ponemos porque lo vamos a usar en un rato con la base de datos. El conversor de jsons usa lo de abajo

    constructor(id: Long, dni: Long, name: String) {
        this.id = id
        this.dni = dni
        this.name = name
    }
}