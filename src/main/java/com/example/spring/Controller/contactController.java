package com.example.spring.Controller;


import com.example.spring.Entity.Contact;
import com.example.spring.Repository.ContactRepository;
import com.example.spring.Service.contactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class contactController {

    final private Contact f = new Contact();
    @Autowired
    contactService contactService;

    @PostMapping("/addContact")
    @Operation(
            tags = "Добавить контакт",
            summary = "контакт добавится",
            operationId = "ID контакта",
            description = "Добавление контакта в телефонную книжку. Нажмите на кнопку " +
                    "'Try it out'. И заполните параметры запроса",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Это пример контакта, который можно добавить в телефонную книжку. Имеется 3 параметра, которые нужно указать. 'number' - телефон контакта, 'name' - имя контакта, 'surname' - фамилия конакта",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "number",schema = @Schema(name = "contact")),
                                    @SchemaProperty(name = "name",schema = @Schema(name = "contact")),
                                    @SchemaProperty(name = "surname",schema = @Schema(name = "contact"))})),
            responses = {
                    @ApiResponse( responseCode = "200",content = @Content( schema = @Schema(implementation = Contact.class),mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name="Пример",
                                                    value = "Объект контакта",
                                                    description = "Возвращает объект контакта со всей информацией"
                                            )
                                    }
                            ),
                            description = "Успешный запрос"),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(),
                            description = "Ошибка сервера")
            }
    )
    @ResponseBody
    public Contact saveContact(@RequestBody Contact contact){
        contactService.save(contact);
        return contact;
    }

    @PostMapping("/deleteContact/{id}")
    @Operation(
            tags = "Удалить контакт",
            summary = "контакт удалится",
            operationId = "ID контакта",
            description = "Удаление контактов из телефонной книжки. Нажмите на кнопку " +
                    "'Try it out'. И заполните параметры запроса",
            parameters = {@Parameter(name = "id",
                    description = "ID Контакта в телефонной книжке",
                    example = "21",
                    in = ParameterIn.PATH
            )},
            responses = { @ApiResponse(responseCode = "200",content = @Content(schema = @Schema(implementation = Contact.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject( name="Пример", value = "id контакта",description = "Возвращает id удаленного контакта")}
                            ),
                            description = "Успешный запрос"),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(),
                            description = "Ошибка сервера")
                    }
    )
    @ResponseBody
    public Long deleteContact(@PathVariable Long id){
        contactService.deleteContactById(id);
        return id;
    }

    @PostMapping("/updateContact/{id}")
    @Operation(
            tags = "Обновление контакт",
            summary = "контакт обновится",
            operationId = "ID контакта",
            description = "Обновление контактов в телефонной книжке. Нажмите на кнопку " +
                    "'Try it out'. И заполните параметры запроса",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Это пример контакта, который можно обновить в телефонной книжку. Имеется 3 параметра, которые нужно указать. 'number' - телефон контакта, 'name' - имя контакта, 'surname' - фамилия конакта",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "number",schema = @Schema(name = "contact")),
                                    @SchemaProperty(name = "name",schema = @Schema(name = "contact")),
                                    @SchemaProperty(name = "surname",schema = @Schema(name = "contact"))
                            })
            ),
            parameters = {@Parameter(name = "id",
                    description = "ID Контакта в телефонной книжке",
                    example = "21",
                    in = ParameterIn.PATH
            )},
            responses = {@ApiResponse( responseCode = "200",content = @Content(schema = @Schema(implementation = Contact.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name="Пример", value = "Объект контакта", description = "Возвращает объект контакта со всей информацией")}),
                            description = "Успешный запрос"),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(),
                            description = "Ошибка сервера")
            }
    )
    @ResponseBody
    public Contact updateContact(@RequestBody Contact contact, @PathVariable long id){
        contactService.changeContact(contact, id);
        return contactService.getContactById(id);
    }

    @PostMapping("/getContact/{id}")
    @Operation(
            tags = "Получение контакта",
            summary = "контакт вернется",
            operationId = "ID контакта",
            description = "Получение контактов из телефонной книжки. Нажмите на кнопку " +
                    "'Try it out'. И заполните параметры запроса",
            parameters = {@Parameter(name = "id",
                    description = "ID Контакта в телефонной книжке",
                    example = "21",
                    in = ParameterIn.PATH
            )},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Contact.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(  name="Пример", value = "Объект контакта", description = "Возвращает объект контакта со всей информацией")}),
                            description = "Успешный запрос"),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(),
                            description = "Ошибка сервера")
            }
    )
    @ResponseBody
    public Contact getContact(@PathVariable Long id){
        return contactService.getContactById(id);
    }
}
