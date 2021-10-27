document.getElementById("submit-login").addEventListener("click", tryLogin);
document.getElementById("btn_salvar").addEventListener("click", createUser);

function tryLogin() {
  event.preventDefault();
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  $.ajax({
    url: "http://localhost:4568/login/",
    datatype: "json",
    contentType: "application/json",
    data: {
      email: username,
      password: password,
    },
  })
    .done(function (data) {
      window.location = "../home/index.html";
    })
    .fail(function () {
      alert("Nenhum usuário com essas credenciais foi encontrado na base!");
    });
}

function createUser() {
  event.preventDefault();

  if (passValidate()) {
    let username = document.getElementById("txt_login").value;
    let email = document.getElementById("txt_email").value;
    let password = document.getElementById("txt_senha").value;
    let userType = document.getElementById("user-type").value;

    $.ajax({
      url: "http://localhost:4568/user/create",
      contentType: "application/x-www-form-urlencoded",
      method: "POST",
      data: {
        "username": username,
        "email": email,
        "password": password,
        "user_type": userType,
      },
    })
      .done(function (data) {
        
      })
      .fail(function (data) {
        if(data.status == 409)
          alert("Já existe um usuário com esse e-mail cadastrado!");
        else
          alert("Houve um problema em seu cadastro: " + data.responseText)
      });
  } else {
    alert("As senhas informadas não são iguais!");
  }
}

function passValidate() {
  event.preventDefault();

  let senha = document.getElementById("txt_senha").value;
  let senha2 = document.getElementById("txt_senha2").value;
  if (senha != senha2) {
    return false;
  }

  return true;
}
