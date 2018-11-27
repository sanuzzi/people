package com.dates

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller("/people")
class PeopleController {

    // Parte 1, para entender estados y formatos
    @Get("/todoOK")
    fun todoBien(): HttpStatus {
        return HttpStatus.OK
    }

    @Get("/hola") // TODO: Se asume que es JSON a pesar del texto?
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
    fun save(id: Long) { // TODO: Se asume status OK?
        peopleService.delete(id)
    }

    // Parte 3, recibiendo bodies (con header)
    @Post("/")
    fun save(@Body person: Person) { // TODO: Se asume status OK?
        peopleService.save(person)
    }

    @Put("/{id}") // Acá nos empezamos a replantear cosas, ya que tiene sentido mandar el ID por un lado y en el cuerpo solo los datos
    fun update(id: Long, @Body person: Person) { // TODO: Se asume status OK?
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
        this.delete(person.id)
        this.save(person)
    }
}

class Person(val id: Long, val dni: Long, val name: String)