function userRoles(array) {
    let string = "";
    const length = array.length;
    for (let index = 0; index < length; index++) {
        string = string + " " + array[index].name;
    }
    return string;
}

$(document).ready(function () {
    loadTable();
})

function loadTable() {

    $.ajax("/api/users", {
        method: "GET",
        dataType: "json",
        success: function (msg) {
            $("#allUsers").children().remove();
            msg.forEach(function (user) {
                $("#allUsers").append(
                    '<tr id=' + user.id + '>' +
                    '<td>' + user.id + '</td>' +
                    '<td>' + user.name + '</td>' +
                    '<td>' + user.surname + '</td>' +
                    '<td>' + user.age + '</td>' +
                    '<td>' + user.email + '</td>' +
                    '<td>' + userRoles(user.roles) + '</td>' +
                    '<td>' + '<button type="button" name="buttonEdit" id="edit" onclick="userForEdit(this)" data-target="#editModal" value=' + user.id + ' ' +
                    'class="btn btn-info" data-toggle="modal">' + 'Edit' + '</button>' + '</td>' +
                    '<td>' + '<button type="button" name="buttonDelete" onclick="userForDelete(this)" data-target="#deleteModal" value=' + user.id + ' ' +
                    ' class="btn btn-danger" data-toggle="modal">' +
                    'Delete' + '</button>' + '</td>' +
                    '</tr>');
            });
        }
    })
}


function editUser() {
    var user = new Object();
    user.name = $("#name").val();
    user.surname = $("#surname").val();
    user.age = $("#age").val();
    user.email = $("#email").val();
    user.password = $("#password").val();
    user.id = $("#editId").val();
    user.roles = $("#roles1").val(),
            $.ajax("/api/users", {
                method: "PUT",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(user),

                success: function (data) {
                    console.log("SUCCESS: ", data);
                    loadTable();
                    $(".modal").modal('hide');
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                },
                done: function (e) {
                    console.log("DONE");
                }
            })
}

function userForEdit(obj) {
    let user_id = obj.value
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/users/" + user_id,
        data: obj.value,
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            var user = JSON.parse(JSON.stringify(data));
            $("#name").val(user.name);
            $("#surname").val(user.surname);
            $("#age").val(user.age);
            $("#email").val(user.email);
            $("#password").val(user.password);
            $("#roles1:selected").val(user.roles);
            $("#editId").val(user.id);
            $("#editButton").val(user.id);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

function userForDelete(obj) {
    let user_id = obj.value
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/users/" + user_id,
        data: obj.value,
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            var user = JSON.parse(JSON.stringify(data));
            $("#name2").val(user.name);
            $("#surname2").val(user.surname);
            $("#age2").val(user.age);
            $("#email2").val(user.email);
            $("#password1").val(user.password);
            $("#roles2").val(user.role);
            $("#deleteId").val(user.id);
            $("#deleteButton").val(user.id);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}


function addUser() {
    $.ajax("/api/users", {
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(
            {
                name: $("#inputName").val(),
                surname: $("#inputSurname").val(),
                age: $("#inputAge").val(),
                email: $("#inputEmail").val(),
                password: $("#inputPassword").val(),
                roles: $("#roles").val()
            }),


        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            loadTable();
            document.getElementById('addForm').reset();
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $(".modal").modal('hide');
        },
        done: function (e) {
            console.log("DONE");
            $(".modal").modal('hide');
        }
    })
}

function deleteUser() {

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/api/users/" + $("#deleteId").val(),
        data: $("#deleteId").val(),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            $(".modal").modal('hide');
            loadTable();
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}





