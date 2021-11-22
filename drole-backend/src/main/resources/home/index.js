//document.getElementById("btn-card-profile").addEventListener("click", showProfile);

window.onload = () => {
  window.event.preventDefault();
  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
    $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
      "#showProfile"
    );
  }

  showProfiles();
};

function showProfiles() {
  window.event.preventDefault();

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/user/",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("user")
        .each(function () {
          var name = $(this).find("profileName").text();
          var id = $(this).find("id").text();
          var type = $(this).find("userType").text();
          var description = $(this).find("description").text();
          var photo_path = $(this).find("userPhoto").text();
          var likes = $(this).find("userLikes").text();

          if(type == 2) type = 'Estabelecimento';
          else if(type == 3) type = 'Músico';
          else if(type == 4) type = 'ADM';

          if (name != "null") {
            $(`
            <div class="card col-mb-4" style="width: 18rem; height:40rem; border-radius:2%">
                <img src="${photo_path}" class="card-img-top" alt="..." style="width: 100%;height: 15vw;object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title">${name}</h5>
                    <p class="card-text">${type}</p>
                    <p class="card-text">${description}</p>
                    <p class="card-text">Avaliação</li>
                </div>

                <div class="card-body text-center">
                    <button id="btn-card-profile" value="${id}" type="button" class="btn btn-warning">Visitar Perfil</button>
            </div>`
            ).appendTo("#profiles-grid");
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function showProfile(profile_id) {
  window.event.preventDefault();

  console.log(profile_id);
  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let current_id = obj.id;
  let user_type = obj.userType;

  $.ajax({
    type: "POST",
    url: "http://localhost:4568/user/" + profile_id,
    dataType: "xml",
    contentType: "application/json",
    data: {
      current_id: current_id,
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
          }
        } else {
          $('<h4 class="mt-0 mb-0">Nome</h4>').appendTo("#changeNameType");
          $('<p class="p-2">Escreva sobre vocês(s).</p>').appendTo("#changeAbout");
          $('<p class="p-2">Digite sua localização</p>').appendTo("#changeLocal");
          $('<p class="small mb-4 tipo">Tipo</p>').appendTo("#changeNameType");
        }

        window.location = "../profile/index.html";
      });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}
