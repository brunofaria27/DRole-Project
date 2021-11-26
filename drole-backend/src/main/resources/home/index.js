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

          if (type == 2) type = "Estabelecimento";
          else if (type == 3) type = "Músico";
          else if (type == 4) type = "ADM";

          if (name != "null") {
            $(`
            <div id="profile-cards" class="card col-mb-4" style="width: 18rem;">
                <img id="profile-photo" src="${photo_path}" class="card-img-top" alt="..." style="width: 100%; height: 12vw; object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title">${name}</h5>
                    <p class="card-text">${type}</p>
                    <p class="card-text">${description}</p>
                </div>
                <div class="row">
                  <div class="col-6 text-left"><img data-bs-toggle="tooltip" data-bs-placement="top" title="Likes deste Perfil" class="like-img" src="../images/like.png" alt="Likes" width="35px" height="35px">${likes}</div>
                  <div class="col-6 text-right"><button here data-bs-toggle="tooltip" data-bs-placement="top" title="Visitar Perfil" id="visit-profile-${id}" value="${id}" type="button" class="btn-event" ><img id="btn-visit-img" src="../images/VisitarPerfil.png" alt="Visitar Perfil" width="100%" height="50px"></button></div>
                </div>
            </div>`).appendTo("#profiles-grid");

            var btnId = "visit-profile-" + id;
            document
              .getElementById(btnId)
              .addEventListener("click", function (e) {
                showProfile(xml, id);
              });
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function showProfile(xml, profile_id) {
  window.event.preventDefault();

  document.getElementById("profiles-grid").innerHTML = "";

  $(xml)
    .find("user")
    .each(function () {
      var name = $(this).find("profileName").text();
      var id = $(this).find("id").text();
      var type = $(this).find("userType").text();
      var description = $(this).find("description").text();
      var photo_path = $(this).find("userPhoto").text();
      var likes = $(this).find("userLikes").text();
      var localization = $(this).find("localization").text();

      console.log(name);

      if (type == 2) type = "Estabelecimento";
      else if (type == 3) type = "Músico";
      else if (type == 4) type = "ADM";

      if(profile_id == id){
      document.getElementById("profilePic").setAttribute("src", photo_path);
      document.getElementById("profileName").innerHTML = `<h4>${name}</h4>`;
      }
    });
}
