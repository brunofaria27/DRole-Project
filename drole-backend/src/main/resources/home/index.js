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

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let current_id = obj.id;

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/users/" + current_id,
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
                  <div id="like-img-div" class="col-6 text-left"><img data-bs-toggle="tooltip" data-bs-placement="top" title="Likes deste Perfil" id="like-img" src="../images/like.png" alt="Likes" width="30px" height="30px">${likes}</div>
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

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let current_id = obj.id;

  document.getElementById("profiles-grid").innerHTML = "";
  document.getElementById("show-profile-grid").innerHTML = `
  <div id="show-profile-card" class="card col-mb-4" style="width: 45rem;">
  <div class="px-4 pt-0 pb-4 cover">
      <div class="media align-items-end profile-head">

          <div id="profilePic" class="profile mr-3">
              <img id="imgPreview" src="../images/noimg.png" alt="..." width="130px"
                  class="rounded mb-2 img-thumbnail">
          </div>

          <div id="profileName" class="media-body mb-5">
              <h4>NOME</h4>
          </div>
      </div>

  </div>

  <div class="row" id="profile-info-row">
      <div id="type">
          <h6 class="font-weight-light d-block">Tipo de Perfil</h6>
      </div>

  </div> <!-- End porcentagem de avaliaÃ§Ã£o -->
  
  <div id="likes">

  </div>

  <div id="tituloAbout" class="px-4 py-3">
      <h5 class="mb-0">Sobre</h5>
  </div> <!-- End sobre -->

  <div class="px-4 py-3">
      <div id="about" class="p-4 rounded shadow-sm bg-light">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Nihil blanditiis, architecto
          laboriosam amet sit similique sequi excepturi quibusdam suscipit. Consequuntur.
      </div>

  </div> <!-- End sobre -->
  <hr color="black" style="margin: 10px 24px 10px 24px;">
  <div id="tituloLocal" class="px-4 py-3">
      <h5 class="mb-0">Localização</h5>
  </div>

  <div class="px-4 py-3">
      <div id="localization" class="p-4 rounded shadow-sm bg-light">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Illo commodi a quod rerum ea id!
          Ipsum architecto, animi obcaecati laboriosam neque doloribus vitae! Saepe totam molestias
          suscipit veritatis, voluptatum fugiat quis sunt dolore facilis consectetur iusto dolor, odit
          voluptatibus sequi quisquam unde nihil reiciendis exercitationem. Numquam aliquid incidunt
          beatae earum.
      </div>
  </div>
</div>
  `;

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
      var currentLike = $(this).find("currentLike").text();

      if (type == 2) type = "Estabelecimento";
      else if (type == 3) type = "Músico";
      else if (type == 4) type = "ADM";

      if (profile_id == id) {
        document.getElementById("profilePic").setAttribute("src", photo_path);
        document.getElementById("about").innerHTML = `${description}`;
        document.getElementById("localization").innerHTML = `${localization}`;
        document.getElementById("profileName").innerHTML = `<h4>${name}</h4>`;
        document.getElementById(
          "type"
        ).innerHTML = `<h6 class="font-weight-light d-block">${type}</h6>`;

        if (currentLike == "true") {
          document.getElementById("likes").innerHTML = `
          <button data-bs-toggle="tooltip" data-bs-placement="top" title="Remover Like" id="like-btn-${id}" value="${id}" type="button" class="like-btn">
          <img id="like-img" src="../images/filledLike.png" alt="Likes" style="margin-bottom: 2px;" width="25px" height="25px"></button><spam id="num-likes">${likes}</spam>`;
        } else {
          document.getElementById("likes").innerHTML = `
          <button data-bs-toggle="tooltip" data-bs-placement="top" title="Dar Like" id="like-btn-${id}" value="${id}" type="button" class="like-btn">
          <img id="like-img" src="../images/like.png" alt="Likes" style="margin-bottom: 2px;" width="25px" height="25px"></button><spam id="num-likes">${likes}</spam>`;
        }

        var btnId = "like-btn-" + id;
        document.getElementById(btnId).addEventListener("click", function (e) {
          if (
            document.getElementById("like-img").getAttribute("src") ==
            "../images/like.png"
          ) {
            document
              .getElementById("like-img")
              .setAttribute("src", "../images/filledLike.png");
            likes += 1;
            document.getElementById("num-likes").innerHTML = likes;

            addLike(current_id, id);
          } else {
            document
              .getElementById("like-img")
              .setAttribute("src", "../images/like.png");
            likes -= 1;
            document.getElementById("num-likes").innerHTML = likes;
            removeLike(current_id, id);
          }
        });
      }
    });
}

function addLike(current_id, profile_id) {
  window.event.preventDefault();

  $.ajax({
    url: "http://localhost:4568/score/",
    method: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: {
      user_id: current_id,
      profile_id: profile_id,
    },
    success: function () {
      console.log("add " + current_id + " " + profile_id);
    },
    error: function () {
      console.log("ERRO: add " + current_id + " " + profile_id);
    },
  });
}

function removeLike(current_id, profile_id) {
  window.event.preventDefault();

  $.ajax({
    url: "http://localhost:4568/score/remove",
    method: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: {
      user_id: current_id,
      profile_id: profile_id,
    },
    success: function () {
      console.log("remove " + current_id + " " + profile_id);
    },
    error: function () {
      console.log("ERRO: remove " + current_id + " " + profile_id);
    },
  });
}
