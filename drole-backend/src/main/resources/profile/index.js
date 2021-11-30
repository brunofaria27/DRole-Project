document.getElementById("saveProfile").addEventListener("click", updateProfile);

window.onload = () => {
  window.event.preventDefault();
  showProfile();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
    $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
      "#showProfile"
    );
    // $(
    //   '<a id="editprofile" class="btn btn-outline-dark btn-sm btn-block" data-toggle="modal" data-target="#mudarModal">Editar Perfil</a>'
    // ).appendTo("#buttonEditLike");
  } else {
    // $(
    //   '<a class="btn btn-outline-dark btn-sm btn-block">Curtir Perfil</a>'
    // ).appendTo("#buttonEditLike");
  }
};

function showProfile() {
  window.event.preventDefault();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let current_id = obj.id;

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
    <button data-toggle="modal" data-target="#mudarModal" data-bs-toggle="tooltip" data-bs-placement="top" id="edit-btn" title="Editar Perfil" type="button" class="font-weight-light d-block">
      <img id="edit-img" src="../images/edit.png" alt="Editar Perfil" style="margin-bottom: 2px;" width="25px" height="25px"></button>
    </button>
    <img data-bs-toggle="tooltip" data-bs-placement="top" title="Likes deste Perfil" id="like-img" src="../images/like.png" alt="Likes" style="margin-bottom: 2px;" width="25px" height="25px"></button><spam id="num-likes"></spam>
  </div>

  <div id="tituloAbout" class="px-4 py-3">
      <h5 class="mb-0">Sobre</h5>
  </div> <!-- End sobre -->

  <div class="px-4 py-3">
      <div id="about" class="p-4 rounded shadow-sm bg-light">
      </div>

  </div> <!-- End sobre -->
  <hr color="black" style="margin: 10px 24px 10px 24px;">
  <div id="tituloLocal" class="px-4 py-3">
      <h5 class="mb-0">Localização</h5>
  </div>

  <div class="px-4 py-3">
      <div id="localization" class="p-4 rounded shadow-sm bg-light">
      </div>
  </div>

  <div class="px-4 py-3">
  </div>

</div>
  `;
  $.ajax({
    type: "GET",
    url: "http://localhost:4568/user/" + current_id,
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
          var localization = $(this).find("localization").text();

          if (type == 2) type = "Estabelecimento";
          else if (type == 3) type = "Músico";
          else if (type == 4) type = "ADM";

          // document.getElementById("edit-btn").addEventListener("click", function(e){
          //   document.getElementById("mudarModal").classList.add("show");
          // });

          document.getElementById("profilePic").setAttribute("src", photo_path);
          document.getElementById("about").innerHTML = `${description}`;
          document.getElementById("localization").innerHTML = `${localization}`;
          document.getElementById("profileName").innerHTML = `<h4>${name}</h4>`;
          document.getElementById("type").innerHTML = `
          <h6 class="font-weight-light d-block">${type}</h6>
          `;

          document.getElementById("num-likes").innerHTML = likes;
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function updateProfile() {
  window.event.preventDefault();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_id = obj.id;

  let value_description = document.getElementById("aboutDescription").value;
  let value_profileName = document.getElementById("nameProfile").value;
  let value_photo = document.getElementById("minhafoto").value;
  let value_localization = document.getElementById("localizationInput").value;

  if (value_photo == "") {
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
