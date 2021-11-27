document.getElementById("submit-login").addEventListener("click", tryLogin);
document.getElementById("btn_salvar").addEventListener("click", createUser);

function tryLogin() {
  window.event.preventDefault();
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
    success: function (data) {
      $(data)
        .find("user")
        .each(function () {
          var obj = new Object();

          obj.id = $(this).find("id").text();
          obj.username = $(this).find("username").text();
          obj.userType = $(this).find("userType").text();
          obj.userPhoto = $(this).find("userPhoto").text();
          obj.profileName = $(this).find("profileName").text();
          obj.localization = $(this).find("localization").text();
          obj.description = $(this).find("description").text();

          var jsonString = JSON.stringify(obj);
          localStorage.setItem('currentUser', jsonString);

        });

      window.location = "../home/index.html";
    },
    error: function () {
      $(
        '<div class="alert alert-danger" role="alert">Nenhum usuário com essas credenciais foi encontrado, tente novamente!</div>'
      ).appendTo("#sucessLogin").fadeIn(300).delay(3000).fadeOut(400);
    },
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
        username: username,
        email: email,
        password: password,
        user_type: userType,
      },
    })
      .done(function (data) {
        $(
          '<div class="alert alert-success" role="alert">Parabéns! Agora você está cadastrado, siga para o login e aproveite <strong>' +username + "</strong></div>"
        ).appendTo("#sucessCadas").fadeIn(300).delay(10000).fadeOut(400);
      })
      .fail(function (data) {
        if (data.status == 409) {
          $(
            '<div class="alert alert-danger" role="alert">CUIDADO! Já existe um usuário com o email <strong>' + email + "</strong> cadastrado, tente novamente!</div>"
          ).appendTo("#sucessCadas").fadeIn(300).delay(3000).fadeOut(400);
        } else {
          $(
            '<div class="alert alert-danger" role="alert">Houve um problema em seu cadastro:' + data.responseText + '</div>'
          ).appendTo("#sucessCadas").fadeIn(300).delay(3000).fadeOut(400);
        }
      });
  } else {
    $(
      '<div class="alert alert-danger" role="alert">As senhas informadas devem ser iguais para concluir o cadastro!</div>'
    ).appendTo("#sucessCadas").fadeIn(300).delay(3000).fadeOut(400);
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
