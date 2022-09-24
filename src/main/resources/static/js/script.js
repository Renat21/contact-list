const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"contact_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"number\">" + data.number + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"surname\">" + data.surname + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < contacts.length; i++)
        table.appendChild(createLine(contacts[i]).firstChild);
    addListener()
})


// Добавить

function addContact(){
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const number = document.getElementById("number");
    const name = document.getElementById("name");
    const surname = document.getElementById("surname")

    console.log(number.value, name.value, surname.value)
    createAjaxQuery("/addContact", {number: number.value, name: name.value, surname: surname.value},
        addSuccessHandler)
    number.value = ""
    name.value = ""
    surname.value = ""
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить

function deleteContact(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteContact/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("contact_" + data))
}

// Изменить
function tryChangeContact(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getContact/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("number").value = data.number;
    document.getElementById("name").value = data.name;
    document.getElementById("surname").value = data.surname;
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

function updateChanges(){
    const changeButton = document.getElementById("changeButton")
    const number = document.getElementById("number");
    const name = document.getElementById("name");
    const surname = document.getElementById("surname")
    changeButton.style.display = "none"
    createAjaxQuery("/updateContact/" + changeButton.dataset.id, {number: number.value, name: name.value, surname: surname.value}, updateHandler)
    number.value = ""
    name.value = ""
    surname.value = ""
    changeButton.dataset.id = ""
}

function updateHandler(data){
    const tr = document.getElementById("contact_" + data.id)
    tr.querySelector(".number").innerHTML = data.number
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".surname").innerHTML = data.surname
}

function createAjaxQuery(path, contact, toFunction){
    $.ajax({
        url: currentLocation + path,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(contact),
        success: toFunction
    })
}


function addListener(){
    const contact = document.querySelectorAll(".fa-trash")
    const change = document.querySelectorAll(".fa-wrench")
    for (let i = 0; i < contact.length; i++){
        contact[i].addEventListener('click', deleteContact)
        change[i].addEventListener('click', tryChangeContact)
    }
}

document.getElementById("addButton").addEventListener("click", addContact);
