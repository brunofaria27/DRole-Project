document.getElementById("saveProfile").addEventListener("click", updateProfile);

window.onload = () => {
  event.preventDefault();
  showProfile();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
    $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
      "#showProfile"
    );
    $(
      '<a id="editprofile" class="btn btn-outline-dark btn-sm btn-block" data-toggle="modal" data-target="#mudarModal">Editar Perfil</a>'
    ).appendTo("#buttonEditLike");
  } else {
    $(
      '<a class="btn btn-outline-dark btn-sm btn-block">Curtir Perfil</a>'
    ).appendTo("#buttonEditLike");
  }
};

function showProfile() {
  window.event.preventDefault();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_id = obj.id;
  let user_type = obj.userType;

  $.ajax({
    type: "POST",
    url: "http://localhost:4568/user/" + user_id,
    dataType: "xml",
    data: {
      current_id: user_id,
    },
    success: function (xml) {
      $(xml)
        .find("user")
        .each(function () {
          var profileName = $(this).find("profileName").text();
          var profileDescription = $(this).find("description").text();
          var profilePhoto = $(this).find("userPhoto").text();
          var profileLocalization = $(this).find("localization").text();

          if(profileName != "null" && profileLocalization != null && profileDescription != null) {
            $('<h4 class="mt-0 mb-0">' + profileName + '</h4>').appendTo("#changeNameType");
            $('<p class="p-2">' + profileDescription + '</p>').appendTo("#changeAbout");
            $('<p class="p-2">' + profileLocalization + '</p>').appendTo("#changeLocal");

            if(user_type == 2) {
              $('<p class="small mb-4 tipo">Estabelecimento</p>').appendTo("#changeNameType");
            } else if(user_type == 3) {
              $('<p class="small mb-4 tipo">Músico</p>').appendTo("#changeNameType");
            } else if(user_type == 4) {
              $('<p class="small mb-4 tipo">Administrador</p>').appendTo("#changeNameType");
            }
          } else {
            $('<h4 class="mt-0 mb-0">Nome</h4>').appendTo("#changeNameType");
            $('<p class="p-2">Escreva sobre vocês(s).</p>').appendTo("#changeAbout");
            $('<p class="p-2">Digite sua localização</p>').appendTo("#changeLocal");
            $('<p class="small mb-4 tipo">Tipo</p>').appendTo("#changeNameType");
          }

        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function updateProfile() {
  event.preventDefault();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_id = obj.id;

  let value_description = document.getElementById("aboutDescription").value;
  let value_profileName = document.getElementById("nameProfile").value;
  let value_photo = document.getElementById("minhafoto").value;
  let value_localization = document.getElementById("localizationInput").value;

  if(value_photo == "") {
    value_photo = null;
  }

  $.ajax({
    url: "http://localhost:4568/user/update/" + user_id,
    method: "PUT",
    data: {
      profile_name: value_profileName,
      profile_localization: value_localization,
      profile_description: value_description,
      photo_path: value_photo,
    },
    success: function (data) {
      window.location = "../profile/index.html";
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}
